package com.facaieve.backend.dto.image;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.entity.user.UserEntity;
import lombok.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Getter
@Service
public class ImageEntityDto {


    @Getter
    @Setter
    @Service
    @AllArgsConstructor
    @NoArgsConstructor//필수
    @Builder
    public static class ResponseDto{
        Long imageEntityId;

        Long userEntityId;

        String imgUri;

        public static ImageEntityDto.ResponseDto of(Long imageEntityId,  Long userEntityId, String imgUri) {
            return new ImageEntityDto.ResponseDto(imageEntityId, userEntityId, imgUri);
        }
    }
}
