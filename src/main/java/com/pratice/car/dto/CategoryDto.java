package com.pratice.car.dto;

import com.pratice.car.entity.Category;
import com.pratice.car.entity.Maker;
import com.pratice.car.entity.Model;
import lombok.*;

import java.util.List;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private List<Category> categories;
    private List<Maker> makers;
    private List<Model> models;
}
