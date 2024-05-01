package com.pratice.car.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarImageDto {
    private Long id;
    private String oriImgName;
    private String imgName;
    private String imgUrl;
    private String repimgYn;
}
