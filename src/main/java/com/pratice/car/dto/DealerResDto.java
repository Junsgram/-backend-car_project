package com.pratice.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DealerResDto {
    private String name;
    private String phone;
    private String location;
    private String message;
    private Long memberId;
}
