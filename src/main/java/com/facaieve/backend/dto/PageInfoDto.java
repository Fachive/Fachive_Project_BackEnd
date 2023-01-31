package com.facaieve.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfoDto {

    private Integer page;//현재 페이지 번호
    private Integer size;// 페이지당 출력할 데이터 개수
    private Long totalElements;//전체 객체 갯수
    private Integer totalPages;//전체 페이지 갯수
}