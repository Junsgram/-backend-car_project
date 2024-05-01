package com.pratice.car.repository;

import com.pratice.car.entity.Car;
import com.pratice.car.entity.Dealer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DealerRepositoryTest {
    @Autowired
    private DealerRepository dealerRepository;

    @DisplayName("DB연결 테스트")
    @Test
    void insertDealerTest() {
        Dealer dealer = Dealer.builder()
                .name("김광수")
                .phone("01091071234")
                .location("울산 남구")
                .build();
        Car car1 = Car.builder()
                .year(2024)
                .color("흰색")
                .price(2000)
                .dealer(dealer)
                .registerNumber("123456789123456") //17자리
                .build();
        List<Car> list = new ArrayList<>();
        list.add(car1);
        dealer.setCars(list);
        dealerRepository.save(dealer);
    }
}
