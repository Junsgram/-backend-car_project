package com.pratice.car.service;

import com.pratice.car.entity.Maker;
import com.pratice.car.entity.Model;
import com.pratice.car.repository.MakerRepository;
import com.pratice.car.repository.ModelRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@DisplayName("Maker 테스트")
@SpringBootTest
public class MakerTest {
    @Autowired
    private MakerRepository makerRepository;
    @Autowired
    private ModelRepository modelRepository;

    @Transactional
    @DisplayName("조회 테스트")
    @Test
    void getList() {
        List<Maker> result = makerRepository.getList(2L);
        for(Maker m : result) {
            System.out.println(m.toString());
        }
    }

    @Transactional
    @DisplayName("Maker 조회 테스트")
    @Test
    void getModelList() {
        List<Model> result = modelRepository.getModelList(1L);
        for(Model m : result) {
            System.out.println(m.toString());
        }
    }
}
