package com.pratice.car.service;

import com.pratice.car.dto.CarImageDto;
import com.pratice.car.entity.CarImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CarImageService {
    // carImg엔티티 저장
    void saveCarImage(CarImage carImage, MultipartFile multipartFile) throws Exception;
    // 이미지 조회(carId에 일치하는)
    List<CarImageDto> findByCarId(Long carId);

    default CarImageDto entityToDto(CarImage carImage) {
        CarImageDto carImageDto = CarImageDto.builder()
                .id(carImage.getId())
                .oriImgName(carImage.getOriImgName())
                .imgName(carImage.getImgName())
                .imgUrl(carImage.getImgUrl())
                .repimgYn(carImage.getRepimgYn())
                .build();
        return carImageDto;
    }
}
