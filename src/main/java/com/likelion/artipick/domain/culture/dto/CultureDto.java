package com.likelion.artipick.domain.culture.dto;

import com.likelion.artipick.domain.culture.Culture;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CultureDto {
    private Long id;
    private String title;
    private String place;
    private String startDate;
    private String endDate;
    private String area;
    private String sigungu;
    private String price;
    private String phone;
    private String imgUrl;
    private String placeUrl;
    private String seq;       // 고유 식별자(공공API)
    private String url;
    private String placeAddr;
    private String gpsX;      // 경도
    private String gpsY;      // 위도

    public static CultureDto from(Culture c) {
        return CultureDto.builder()
                .id(c.getId())
                .title(c.getTitle())
                .place(c.getPlace())
                .startDate(c.getStartDate())
                .endDate(c.getEndDate())
                .area(c.getArea())
                .sigungu(c.getSigungu())
                .price(c.getPrice())
                .phone(c.getPhone())
                .imgUrl(c.getImgUrl())
                .placeUrl(c.getPlaceUrl())
                .seq(c.getSeq())
                .url(c.getUrl())
                .placeAddr(c.getPlaceAddr())
                .gpsX(c.getGpsX())
                .gpsY(c.getGpsY())
                .build();
    }
}
