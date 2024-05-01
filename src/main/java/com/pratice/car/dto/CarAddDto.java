package com.pratice.car.dto;


import com.pratice.car.entity.Dealer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CarAddDto {
    private String brand;
    private String model;
    private String color;
    private String registerNumber;
    private int year;
    private int price;
    private Long dealerId;
}
