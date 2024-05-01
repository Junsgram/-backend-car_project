package com.pratice.car.repository;

import com.pratice.car.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long> {
    @Query("select m from Model m where m.maker.id = :makerId")
    List<Model> getModelList (@Param("makerId") Long makerId);
}
