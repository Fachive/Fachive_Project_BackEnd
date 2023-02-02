package com.facaieve.backend.dto.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class FollowDTO {


    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class PostFollow{
        Long userId;
        Long followedUserId;

    }
}
