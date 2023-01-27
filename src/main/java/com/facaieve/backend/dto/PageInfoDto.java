package com.facaieve.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfoDto {

    private int page;//현재 페이지 번호
    private int size;// 페이지당 출력할 데이터 개수
    private long totalElements;//전체 객체 갯수
    private int totalPages;//전체 페이지 갯수
}