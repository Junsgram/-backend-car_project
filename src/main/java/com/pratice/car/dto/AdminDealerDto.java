package com.pratice.car.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AdminDealerDto {
    private String name;
    private String phone;
    private String location;
    private Long memberid;
}
