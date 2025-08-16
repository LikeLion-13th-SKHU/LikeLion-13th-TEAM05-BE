package com.likelion.artipick.domain.culture.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.likelion.artipick.domain.culture.Culture;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Period2Item {

    // 실제 필드명은 데이터포털 응답에 맞춰 @JsonProperty로 매핑
    @JsonProperty("title")      private String title;
    @JsonProperty("place")      private String place;
    @JsonProperty("startDate")  private String startDate;
    @JsonProperty("endDate")    private String endDate;

    @JsonProperty("area")       private String area;
    @JsonProperty("sigungu")    private String sigungu;
    @JsonProperty("price")      private String price;
    @JsonProperty("phone")      private String phone;

    @JsonProperty("img")        private String imgUrl;
    @JsonProperty("placeUrl")   private String placeUrl;

    @JsonProperty("seq")        private String seq;      // 고유키
    @JsonProperty("url")        private String url;

    @JsonProperty("placeAddr")  private String placeAddr;
    @JsonProperty("gpsX")       private String gpsX;     // 경도
    @JsonProperty("gpsY")       private String gpsY;     // 위도

    public Culture toEntity() {
        return Culture.of(
                title, place, startDate, endDate,
                area, sigungu, price, phone,
                imgUrl, placeUrl, seq, url,
                placeAddr, gpsX, gpsY
        );
    }
}
