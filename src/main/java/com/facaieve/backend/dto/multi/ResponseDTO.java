package com.facaieve.backend.dto.multi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data

public class ResponseDTO<T>{//todo responseDTO list 형태로 반환되는 타입에 사용할것

    private String error;
    private List<T> data;
}
