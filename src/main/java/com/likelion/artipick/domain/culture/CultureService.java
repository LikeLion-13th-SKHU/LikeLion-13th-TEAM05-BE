package com.likelion.artipick.domain.culture;

import com.likelion.artipick.domain.culture.dto.CultureDto;
import com.likelion.artipick.domain.culture.dto.api.Period2Item;
import com.likelion.artipick.domain.culture.dto.api.Period2Response;
import com.likelion.artipick.domain.culture.infra.CultureOpenApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class CultureService {

    private final CultureRepository repository;
    private final CultureOpenApiClient client;

    /** 목록 조회 */
    @Transactional(readOnly = true)
    public Page<CultureDto> getList(Pageable pageable) {
        return repository.findAll(pageable).map(CultureDto::from);
    }

    /** 단건 조회 */
    @Transactional(readOnly = true)
    public CultureDto getOne(Long id) {
        Culture c = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("문화행사를 찾을 수 없습니다. id=" + id));
        return CultureDto.from(c);
    }

    /**
     * 공공 API에서 기간 단위로 조회해 DB에 upsert
     * @return 신규/갱신 반영 건수
     */
    public int importByPeriod(LocalDate from, LocalDate to) {
        int page = 1;
        final int size = 100; // API 허용 범위 내 최대 권장
        int affected = 0;

        while (true) {
            Period2Response resp = client.fetchPeriod(from, to, page, size);
            if (resp == null || resp.hasNoItems()) break;

            for (Period2Item item : resp.getItems()) {
                // upsert by seq
                Culture incoming = item.toEntity();
                repository.findBySeq(incoming.getSeq())
                        .ifPresentOrElse(existing -> {
                            existing.overwriteWith(incoming);
                            // JPA dirty checking
                        }, () -> {
                            repository.save(incoming);
                        });
                affected++;
            }

            // 더 이상 페이지 없으면 종료
            Integer total = resp.getTotalCount();
            Integer numOfRows = resp.getNumOfRows() != null ? resp.getNumOfRows() : size;
            if (total == null) {
                // totalCount가 없다면, 아이템이 꽉 차지 않았을 때 종료
                if (resp.getItems().size() < numOfRows) break;
            } else {
                int lastPage = (int) Math.ceil(total / (double) numOfRows);
                if (page >= lastPage) break;
            }
            page++;
        }
        return affected;
    }
}
