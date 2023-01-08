package com.facaieve.backend.stubDate;

import com.facaieve.backend.Constant.UserActive;
import com.facaieve.backend.entity.basetime.BaseEntity;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserStubData extends BaseEntity {

    Long userEntityId = 1L;

    String displayName = "김스텁";

    String email = "STUB_DATE@naver.com";

    String password = "STUB_PASSWORD";

    String state = "Gyeonggi_Do";

    String city = "Suwon_City";

    String userInfo = "안녕하세요 신규회원입니다.";

    String career =  "커리어 스텁데이터";

    String education = "ABC 대학교 졸업";

    String Company = "ABC전자";

    UserActive userActive = UserActive.Active;

}
