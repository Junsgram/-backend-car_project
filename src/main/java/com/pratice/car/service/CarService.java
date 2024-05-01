package com.pratice.car.service;

import com.pratice.car.dto.*;
import com.pratice.car.entity.Car;
import com.pratice.car.entity.CarImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CarService {
    // 차량 등록
    void addCar(CarDto dto, List<MultipartFile> carImgFileList) throws Exception;
    // 삭제
    Long carRemove(Long id);
    // 상세조회
    CarDto findById(Long id);

    // 이미지 포함된 리스트 조회
    List<MainCarDto> getCarList();

    // 카테고리 리스트 출력
    CategoryDto getCategory(Long categoryId, Long makerId);

    // Main Page 리스트 출력
    PageResultDto<MainCarDto, Object[]> getMainList(PageRequestDto requestDto);
    // 차 목록 페이지 조회하기
    List<ListCarDto> getPageList(Long id, String maker, String model);
    // 딜러ID로 조회
    List<ListCarDto> getDealerCarList(Long dealerId);
    // 게시글 수정
    void editCar(CarDto carDto);

    // dto -> entity
    default Car dtoToEntity(CarDto dto) {
        Car car = Car.builder()
                .price(dto.getPrice())
                .cardesc(dto.getCardesc())
                .title(dto.getTitle())
                .displacement(dto.getDisplacement())
                .mileage(dto.getMileage())
                .transmission(dto.getTransmission())
                .fuel(dto.getFuel())
                .color(dto.getColor())
                .year(dto.getYear())
                .registerNumber(dto.getRegisterNumber())
                .build();
        return car;
    }

    // entity -> dto
    default MainCarDto entityObjectDto(Car car, CarImage img) {
        MainCarDto dto = MainCarDto.builder()
                .id(car.getId())
                .title(car.getTitle())
                .price(car.getPrice())
                .makerName(car.getMaker().getMakerName())
                .modelName(car.getModel().getModelName())
                .imgName(img.getImgName())
                .build();
        return dto;
    }
}
