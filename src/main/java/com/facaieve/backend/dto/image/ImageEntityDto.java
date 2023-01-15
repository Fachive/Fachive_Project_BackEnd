package com.facaieve.backend.dto.image;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Service
public class ImageEntityDto {


    @Getter
    @Service
    @AllArgsConstructor
    @NoArgsConstructor//필수
    @Builder
    public static class ResponseDto{
        long imageEntityId;

        long userEntityId;
    }
}
