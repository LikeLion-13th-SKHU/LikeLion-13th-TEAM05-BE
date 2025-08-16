package com.likelion.artipick.domain.culture;

import com.likelion.artipick.domain.culture.dto.CultureDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/cultures")
@RequiredArgsConstructor
public class CultureController {

    private final CultureService cultureService;

    /** 목록 조회 */
    @GetMapping
    public ResponseEntity<Page<CultureDto>> list(Pageable pageable) {
        return ResponseEntity.ok(cultureService.getList(pageable));
    }

    /** 단건 조회 */
    @GetMapping("/{id}")
    public ResponseEntity<CultureDto> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(cultureService.getOne(id));
    }

    /** 공공API → DB 적재 (배치/관리자용 엔드포인트) */
    @PostMapping("/import")
    public ResponseEntity<Integer> importByPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        int affected = cultureService.importByPeriod(from, to);
        return ResponseEntity.ok(affected);
    }
}
