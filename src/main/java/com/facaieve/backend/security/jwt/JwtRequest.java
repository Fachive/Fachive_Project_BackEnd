package com.facaieve.backend.security.jwt;

import com.facaieve.backend.Constant.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtRequest {//jwt 에서 사용자의 이메일과 권한을 함호화하기 위해서 변수로 둠
    private String email;
    private UserRole role;
}
