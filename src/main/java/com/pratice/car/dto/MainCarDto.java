package com.pratice.car.dto;

import com.pratice.car.entity.Category;
import lombok.*;

@Builder
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainCarDto {
    private Long id;
    private int price;
    private String makerName;
    private String modelName;
    private String imgName;
    private String title;

}
