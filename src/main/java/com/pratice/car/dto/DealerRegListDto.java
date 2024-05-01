package com.pratice.car.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DealerRegListDto {
    private Long id;
    private String name;
    private String phone;
    private String location;
    private String message;
    private Long memberId;
    private String dealerState;
}
