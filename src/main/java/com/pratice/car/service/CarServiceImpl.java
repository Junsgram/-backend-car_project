package com.pratice.car.service;

import com.pratice.car.dto.*;
import com.pratice.car.entity.*;
import com.pratice.car.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;
    private final CarImageService carImageService;
    private final DealerService dealerService;
    private final CategoryRepository categoryRepository;
    private final MakerRepository makerRepository;
    private final ModelRepository modelRepository;

    // 등록
    @Override
    public void addCar(CarDto dto, List<MultipartFile> carImageFileList) throws Exception {
        Car car = dtoToEntity(dto);
        Dealer dealer = dealerRepository.findById(dto.getDealerId()).get();
        car.setDealer(dealer);
        // 카테고리, 메이커, 모델
        Category category = categoryRepository.findById(dto.getCategoryId()).get();
        Maker maker = makerRepository.findById(dto.getMakerId()).get();
        Model model = modelRepository.findById(dto.getModelId()).get();
        car.setCategory(category);
        car.setMaker(maker);
        car.setModel(model);
        carRepository.save(car);
        // 이미지 등록
        for (int i = 0; i < carImageFileList.size(); i++) {
            CarImage carImage = new CarImage();
            carImage.setCar(car);
            if (i == 0) {
                carImage.setRepimgYn("Y");
            } else {
                carImage.setRepimgYn("N");
            }
            carImageService.saveCarImage(carImage, carImageFileList.get(i));
        }
    }

    // 이미지 포함 조회
    @Override
    public List<MainCarDto> getCarList() {
        List<Object[]> result = carRepository.getCarList();
        List<MainCarDto> carDtoList = new ArrayList<>();
        for (Object[] obj : result) {
            MainCarDto carDto = entityObjectDto((Car) obj[0], (CarImage) obj[1]);
            carDtoList.add(carDto);
        }
        return carDtoList;
    }

    @Override
    public Long carRemove(Long id) {
        return null;
    }

    @Override
    public CarDto findById(Long id) {
        Car car = carRepository.findById(id).get();
        CarDto dto = new CarDto();
        //dto로 update 진행
        dto.update(car.getCategory().getId(), car.getModel().getId(), car.getModel().getId(), car.getColor(), car.getRegisterNumber(),
                car.getYear(), car.getPrice(), car.getId(), car.getDisplacement(), car.getMileage(), car.getTransmission(),
                car.getFuel(), car.getTitle(), car.getCardesc());
        // carid에 맞는 이미지 값
        List<CarImageDto> carImageDtoList = carImageService.findByCarId(id);
        dto.setCarImageDtos(carImageDtoList);
        // dealer정보 할당
        dto.setDealer(car.getDealer());
        return dto;
    }


    @Override
    public CategoryDto getCategory(Long categoryId, Long makerId) {
        CategoryDto result = new CategoryDto();
        List<Category> categories = categoryRepository.findAll();
        List<Maker> makers = (categoryId == 1 || categoryId == 2) ?
                makerRepository.getList(categoryId)
                : makerRepository.findAll();
        List<Model> models = makerId == 0 ? modelRepository.findAll() : modelRepository.getModelList(makerId);
        result.setCategories(categories);
        result.setMakers(makers);
        result.setModels(models);
        return result;
    }

    // 메인페이지 목록 조회(베스트 수입차, 베스트 국산차)
    @Override
    public PageResultDto<MainCarDto, Object[]> getMainList(PageRequestDto requestDto) {
        // Pageable 객체 생성
        Pageable pageable = requestDto.getPageable(Sort.by("id").descending());
        // 조회한 결과
        Page<Object[]> result = carRepository.getCarMainList(pageable, requestDto.getCategoryId());
        // 조회한 결과 처리 여부
        Function<Object[], MainCarDto> fn = (arr -> {
            return entityObjectDto((Car) arr[0], (CarImage) arr[1]);
        });
        return new PageResultDto<>(result, fn);
    }

    // 차 목록 페이지 조회하기
    @Override
    public List<ListCarDto> getPageList(Long id, String maker, String model) {
        // id==10이면(전체리스트) 전체 항목 출력 그 외 id값을 확인하여 값을 반환한다.
        List<Object[]> result = new ArrayList<>();
        // List<Object[]> result = id==10 ? carRepository.getCarList() : carRepository.getCarMainList(id);
        /**
         * maker값은 제조사 , model 값은 모델
         * maker값이 제조사가 아니고 model 값이 모델일 때
         * getListSearchMaker(maker)
         * maker값이 제조사가 아니고 model값도 모델이 아닐 때
         * getListSearchMakerModel(maker,model)
         */
        // 전체 자동차 조회
        if (id == 10) {
            if (maker.equals("제조사") && model.equals("모델")) {
                result = carRepository.getCarList();
            } else if (!maker.equals("제조사") && model.equals("모델")) {
                result = carRepository.getListSearchMaker(maker);
            } else if (!maker.equals("제조사") && !model.equals("모델")) {
                result = carRepository.getListSearchMakerModel(maker, model);
            }
        }
        // 국산차, 수입차 조회
        else {
            if (maker.equals("제조사") && model.equals("모델")) {
                result = carRepository.getCarPageList(id);
            } else if (!maker.equals("제조사") && model.equals("모델")) {
                result = carRepository.getListSearchMaker(maker);
            } else if (!maker.equals("제조사") && !model.equals("모델")) {
                result = carRepository.getListSearchMakerModel(maker, model);
            }
        }
        List<ListCarDto> listCarDtos = new ArrayList<>();
        for (Object[] obj : result) {
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .title(car.getTitle())
                    .year(car.getYear())
                    .fuel(car.getFuel())
                    .mileage(car.getMileage())
                    .price(car.getPrice())
                    .dealer(car.getDealer())
                    .imgName(carImage.getImgName())
                    .build();
            listCarDtos.add(dto);
        }
        return listCarDtos;
    }

    // 딜러 Id로 자동차 리스트 조회
    @Override
    public List<ListCarDto> getDealerCarList(Long dealerId) {
        List<Object[]> result = carRepository.getDealerIdList(dealerId);
        List<ListCarDto> list = new ArrayList<>();
        for(Object[] obj: result) {
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .title(car.getTitle())
                    .year(car.getYear())
                    .fuel(car.getFuel())
                    .mileage(car.getMileage())
                    .price(car.getPrice())
                    .dealer(car.getDealer())
                    .imgName(carImage.getImgName())
                    .build();
            list.add(dto);
        }
        return list;
    }

    // 수정

    @Override
    public void editCar(CarDto carDto) {
        Car car = carRepository.findById(carDto.getId()).get();
        car.setTitle(carDto.getTitle());
        car.setCardesc(carDto.getCardesc());
        car.setColor(carDto.getColor());
        car.setRegisterNumber(carDto.getRegisterNumber());
        car.setDisplacement(carDto.getDisplacement());
        car.setTransmission(carDto.getTransmission());
        car.setMileage(carDto.getMileage());
        car.setFuel(carDto.getFuel());
        car.setYear(carDto.getYear());
        car.setPrice(carDto.getPrice());
        carRepository.save(car);
    }
}
