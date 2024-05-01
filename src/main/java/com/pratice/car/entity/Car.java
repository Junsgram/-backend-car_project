package com.pratice.car.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Table
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@SequenceGenerator(name = "car_seq", sequenceName = "car_seq", initialValue = 1, allocationSize = 1)
public class Car extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "car_seq")
    @Column(name="car_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="maker_id")
    private Maker maker;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="model_id")
    private Model model;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="dealer_id")
    private Dealer dealer;

    private String title;               // 매물 제목
    private String cardesc;             // 매물 설명글
    private String color;               // 색상
    private String registerNumber;      // 등록 번호
    private int displacement;           // 배기량
    private String transmission;        // 변속기
    private int mileage;                // 주행거리
    private String fuel;                // 연료

    private int year;                   // 년식
    private int price;                  // 가격




}
