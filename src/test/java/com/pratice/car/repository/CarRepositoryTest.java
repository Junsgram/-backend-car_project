package com.pratice.car.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@DisplayName("Car Repository 테스트")
@SpringBootTest
public class CarRepositoryTest {

    @Autowired private CarRepository carRepository;

    @Transactional
    @DisplayName("이미지를 포함한 List 출력 테스트")
    @Test
    void givenNothing_whenSearchingCarList_thenReturnCarLists() {
        // given
        // when & then
        List<Object[]> result = carRepository.getCarList();
        for(Object[] o : result) {
            System.out.println(Arrays.toString(o));
        }
    }
}
