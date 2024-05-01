package com.pratice.car.dto;


import com.pratice.car.entity.Category;
import com.pratice.car.entity.Dealer;
import com.pratice.car.entity.Maker;
import com.pratice.car.entity.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private Long id;
    private Long categoryId;
    private Long makerId;
    private Long modelId;

    private Long dealerId;
    private Dealer dealer;
    private int displacement;           // 배기량
    private String transmission;        // 변속기
    private int mileage;                // 주행거리
    private String fuel;                // 연료
    private String color;
    private String registerNumber;
    private int year;
    private int price;
    private String title;
    private String cardesc;

    private List<CarImageDto> carImageDtos = new ArrayList<>();

    // update method
    public void update(Long categoryId, Long modelId, Long makerId, String color, String registerNumber, int year, int price,
                       Long id, int displacement, int mileage, String transmission, String fuel, String title, String cardesc) {
        this.categoryId = categoryId;
        this.modelId = modelId;
        this.makerId = makerId;
        this.color = color;
        this.registerNumber = registerNumber;
        this.year = year;
        this.price = price;
        this.id = id;
        this.displacement = displacement;
        this.mileage = mileage;
        this.transmission = transmission;
        this.fuel = fuel;
        this.title = title;
        this.cardesc = cardesc;
    }
}
