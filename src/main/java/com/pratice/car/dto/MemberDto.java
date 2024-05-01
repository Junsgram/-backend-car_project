package com.pratice.car.dto;

import com.pratice.car.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MemberDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private Long dealerId;
    private Role role;
}
