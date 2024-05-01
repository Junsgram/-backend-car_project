package com.pratice.car.repository;

import com.pratice.car.dto.CarImageDto;
import com.pratice.car.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    // 메인페이지 자동차 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y'")
    List<Object[]> getCarList();

    // 카테고리 - 국산차 외제차 기준으로 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y' and c.category.id=:id ")
    Page<Object[]> getCarMainList(Pageable pageable, @Param("id") Long id);
    // 페이지에 자동차 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y' and c.category.id=:id ")
    List<Object[]> getCarPageList(@Param("id") Long id);

    // 제조사 이름으로 자동차 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y' and c.maker.makerName=:maker ")
    List<Object[]> getListSearchMaker(@Param("maker") String maker);

    // 제조사와 모델이름으로 자동차 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y' and c.maker.makerName=:maker and c.model.modelName = :model ")
    List<Object[]> getListSearchMakerModel(@Param("maker") String maker, @Param("model") String model);

    // 딜러 ID로 자동차 조회
    @Query("select c,i from Car c left join CarImage i on i.car = c" +
            " where i.repimgYn='Y' and c.dealer.id = :dealerId")
    List<Object[]> getDealerIdList(@Param("dealerId") Long dealerId);
}
