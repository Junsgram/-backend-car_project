package com.pratice.car.dto;

import com.pratice.car.entity.Dealer;
import lombok.*;

@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListCarDto {
    private Long id;
    private String imgName;
    private String title;
    private int year;
    private String fuel;
    private int mileage;
    private int price;
    private Dealer dealer;
}
