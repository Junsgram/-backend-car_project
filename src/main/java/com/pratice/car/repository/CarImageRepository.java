package com.pratice.car.repository;

import com.pratice.car.dto.CarImageDto;
import com.pratice.car.entity.CarImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarImageRepository extends JpaRepository<CarImage,Long > {
    // carid로 조회
    List<CarImage> findByCarId(Long carId);
}
