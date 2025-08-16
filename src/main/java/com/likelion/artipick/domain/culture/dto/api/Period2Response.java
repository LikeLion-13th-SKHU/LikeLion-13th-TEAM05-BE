package com.likelion.artipick.domain.culture.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Period2Response {

    // 예: { "response": { "body": { "items": [...], "pageNo": 1, "numOfRows": 10, "totalCount": 123 } } }
    @JsonProperty("items")
    private List<Period2Item> items;

    @JsonProperty("pageNo")
    private Integer pageNo;

    @JsonProperty("numOfRows")
    private Integer numOfRows;

    @JsonProperty("totalCount")
    private Integer totalCount;

    /** 안전 장치 */
    public boolean hasNoItems() {
        return items == null || items.isEmpty();
    }
}
