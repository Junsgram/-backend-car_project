package com.pratice.car.controller;

import com.pratice.car.dto.CarDto;
import com.pratice.car.dto.ListCarDto;
import com.pratice.car.service.DealerRegService;
import com.pratice.car.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dealer")
@CrossOrigin(origins = "http://localhost:3000")
public class DealerController {
    private final DealerRegService dealerRegService;
    private final CarService carService;

    // 등록
    @PostMapping("/addCar")
    public ResponseEntity addCar(CarDto dto,
                                 @RequestParam("uploadFiles") List<MultipartFile> uploadFiles){
        try {
            System.out.println(dto.toString());
            carService.addCar(dto,uploadFiles);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("ok", HttpStatus.OK);
    }
    // 조회
    @GetMapping("/carList")
    public ResponseEntity listCar(@RequestParam("dealerId") Long dealerId) {
        List<ListCarDto> result = carService.getDealerCarList(dealerId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 수정
    @PostMapping("/carEdit")
    public ResponseEntity editCar(CarDto carDto) {
        System.out.println(carDto);
        try{
            carService.editCar(carDto);
        }catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }
}
