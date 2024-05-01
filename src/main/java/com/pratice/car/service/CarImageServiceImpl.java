package com.pratice.car.service;

import com.pratice.car.dto.CarImageDto;
import com.pratice.car.entity.CarImage;
import com.pratice.car.repository.CarImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class CarImageServiceImpl implements CarImageService{
    @Value("${carImgLocation}")
    String carImgLocation;
    private final CarImageRepository carImageRepository;
    private final FileService fileService;
    @Override
    public void saveCarImage(CarImage carImage, MultipartFile multipartFile) throws Exception {
        // 원본이름
        String oriImgName = multipartFile.getOriginalFilename();
        // 파일이 있을 때만 업로드
        if(oriImgName != null ) {
            // 파일 업로드
            String imgName = fileService.uploadFile(carImgLocation, oriImgName, multipartFile.getBytes());
            // 경로 + 파일명
            String imgUrl = "/images/carimg/" + imgName;
            carImage.update(oriImgName, imgName, imgUrl);
            carImageRepository.save(carImage);
        }
        // 없을 때 경고문 출력
        System.out.println("Multipart 파일이 존재하지 않습니다.");
    }

    // 이미지 조회
    @Override
    public List<CarImageDto> findByCarId(Long carId) {
        List<CarImage> result = carImageRepository.findByCarId(carId);
        List<CarImageDto> carImageDtos = result.stream().map(entity -> entityToDto(entity))
                .collect(Collectors.toList());
        return carImageDtos;
    }
}
