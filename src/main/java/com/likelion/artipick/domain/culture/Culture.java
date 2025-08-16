package com.likelion.artipick.domain.culture;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cultures", indexes = {
        @Index(name = "idx_culture_seq", columnList = "seq", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Culture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 기본 정보 */
    @Column(nullable = false, length = 300)
    private String title;

    @Column(length = 200)
    private String place;

    @Column(length = 50)
    private String startDate;

    @Column(length = 50)
    private String endDate;

    /** 부가 정보 */
    @Column(length = 100)
    private String area;

    @Column(length = 100)
    private String sigungu;

    @Column(length = 100)
    private String price;

    @Column(length = 100)
    private String phone;

    @Column(length = 600)
    private String imgUrl;

    @Column(length = 600)
    private String placeUrl;

    /** 공공API 고유키 */
    @Column(nullable = false, length = 120, unique = true)
    private String seq;

    @Column(length = 600)
    private String url;

    @Column(length = 600)
    private String placeAddr;

    @Column(length = 100)
    private String gpsX; // 경도

    @Column(length = 100)
    private String gpsY; // 위도

    private Culture(String title, String place, String startDate, String endDate,
                    String area, String sigungu, String price, String phone,
                    String imgUrl, String placeUrl, String seq, String url,
                    String placeAddr, String gpsX, String gpsY) {
        this.title = title;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
        this.area = area;
        this.sigungu = sigungu;
        this.price = price;
        this.phone = phone;
        this.imgUrl = imgUrl;
        this.placeUrl = placeUrl;
        this.seq = seq;
        this.url = url;
        this.placeAddr = placeAddr;
        this.gpsX = gpsX;
        this.gpsY = gpsY;
    }

    public static Culture of(String title, String place, String startDate, String endDate,
                             String area, String sigungu, String price, String phone,
                             String imgUrl, String placeUrl, String seq, String url,
                             String placeAddr, String gpsX, String gpsY) {
        return new Culture(title, place, startDate, endDate, area, sigungu, price, phone,
                imgUrl, placeUrl, seq, url, placeAddr, gpsX, gpsY);
    }

    /** upsert용 반영 */
    public void overwriteWith(Culture src) {
        this.title = src.title;
        this.place = src.place;
        this.startDate = src.startDate;
        this.endDate = src.endDate;
        this.area = src.area;
        this.sigungu = src.sigungu;
        this.price = src.price;
        this.phone = src.phone;
        this.imgUrl = src.imgUrl;
        this.placeUrl = src.placeUrl;
        this.url = src.url;
        this.placeAddr = src.placeAddr;
        this.gpsX = src.gpsX;
        this.gpsY = src.gpsY;
    }
}
