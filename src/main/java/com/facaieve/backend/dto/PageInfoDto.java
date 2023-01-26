package com.facaieve.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PageInfoDto {

    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}