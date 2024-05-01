package com.pratice.car.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDto<DTO, EN> {
    // field
    private List<DTO> dtoList;              // dto리스트 - 보내질 결과 타입
    private int totalPage;                  // 총 페이지 번호
    private int page;                       // 현재 페이지 번호
    private int size;                       // 목록 사이즈
    private int start;                      // 시작페이지 번호
    private int end;                        // 끝 페이지 번호
    private boolean prev;                   // 이전 페이지 번호
    private boolean next;                   // 다음페이지 번호
    private List<Integer> pageList;         // 페이지 번호 목록 ex) 1~10

    // constructor
    public PageResultDto(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    // 페이지 리스트 만들기
    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하기 때문에 +1
        this.size = pageable.getPageSize();
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;

        start = tempEnd - 9;
        end = totalPage > tempEnd ? tempEnd : totalPage;

        prev = start > 1;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
