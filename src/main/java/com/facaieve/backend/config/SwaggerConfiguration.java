package com.facaieve.backend.config;

import com.facaieve.backend.dto.UserDto;
import com.facaieve.backend.dto.etc.CategoryDTO;
import com.facaieve.backend.dto.etc.TagDTO;
import com.facaieve.backend.dto.post.FashionPickupDto;
import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.dto.post.PortfolioDto;
import com.fasterxml.classmate.TypeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 /*스웨거 2.x 버전 설정*/
public class SwaggerConfiguration {

    @Bean
    public Docket api(TypeResolver typeResolver){// Swagger 설정의 핵심이 되는 Bean
        return new Docket(DocumentationType.OAS_30)  // open api spec 3.0, DocumentationType.SWAGGER_2    스웨거 2.x 버전 설정
                .additionalModels(
                        typeResolver.resolve(UserDto.ResponseUserDto.class),
                        typeResolver.resolve(FashionPickupDto.ResponseFashionPickupDto.class),
                        typeResolver.resolve(PortfolioDto.ResponsePortfolioDto.class),
                        typeResolver.resolve(FundingDto.ResponseFundingDto.class),
                        typeResolver.resolve(CategoryDTO.ResponseCategoryDTO.class),
                        typeResolver.resolve(TagDTO.ResponseTagDTO.class)
                )
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.facaieve.backend"))// basePackage("com.facaieve.backend")스웨거에서 문서 작성위해 스캔할 패키지의 범위 설정(루트패키지 입력 "com.main.project")
                .paths(PathSelectors.any())//모든 컨트롤러(핸들러)를 위에서 설정한 패키지를 돌면서 체크
                .build()
                .useDefaultResponseMessages(true);//메소드 결과값에 설정되는 기본 200,401, 402, 404 메시지 여부 설정
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Project Fachive with Swagger")
                .description("디자이너들의 공간 패카이브입니다!")
                .version("1.0")
                .build();
    }


}
