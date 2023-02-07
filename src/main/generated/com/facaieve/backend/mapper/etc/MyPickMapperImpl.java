package com.facaieve.backend.mapper.etc;

import com.facaieve.backend.dto.etc.MyPickDTO;
import com.facaieve.backend.entity.etc.MyPickEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-02-01T22:45:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MyPickMapperImpl implements MyPickMapper {

    @Override
    public MyPickEntity responseMyPickDtoToMyPickEntity(MyPickDTO.ResponseMyPickDTO responseMyPickDTO) {
        if ( responseMyPickDTO == null ) {
            return null;
        }

        MyPickEntity.MyPickEntityBuilder myPickEntity = MyPickEntity.builder();

        myPickEntity.myPickId( responseMyPickDTO.getMyPickId() );
        myPickEntity.userId( responseMyPickDTO.getUserId() );

        return myPickEntity.build();
    }

    @Override
    public MyPickEntity postMyPickDtoToMyPickEntity(MyPickDTO.PostMyPickDTO postMyPickDTO) {
        if ( postMyPickDTO == null ) {
            return null;
        }

        MyPickEntity.MyPickEntityBuilder myPickEntity = MyPickEntity.builder();

        myPickEntity.userId( postMyPickDTO.getUserId() );

        return myPickEntity.build();
    }

    @Override
    public MyPickDTO.ResponseMyPickDTO myPickEntityToResponseMyPickDTO(MyPickEntity myPickEntity) {
        if ( myPickEntity == null ) {
            return null;
        }

        MyPickDTO.ResponseMyPickDTO.ResponseMyPickDTOBuilder responseMyPickDTO = MyPickDTO.ResponseMyPickDTO.builder();

        responseMyPickDTO.myPickId( myPickEntity.getMyPickId() );
        responseMyPickDTO.userId( myPickEntity.getUserId() );

        return responseMyPickDTO.build();
    }
}
