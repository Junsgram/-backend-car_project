package com.pratice.car.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
@Table
@Entity
@SequenceGenerator(name="carimg_seq", sequenceName = "carimg_seq", allocationSize = 1, initialValue = 1)
public class CarImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carimg_seq")
    @Column(name="car_img_id")
    private Long id;
    private String imgName;         // 이미지 이름
    private String oriImgName;      // 원본 이름
    private String imgUrl;          // 경로
    private String repimgYn;        // 대표경로

    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="car_id")
    private Car car;

    // 업데이트 메소드
    public void update(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
