package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.repository.etc.MyPickRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MyPickService {

    MyPickRepository myPickRepository;

    public MyPickEntity createMyPick(MyPickEntity myPickEntity){
        return myPickRepository.save(myPickEntity);
    }

    //유저가 누른 좋아요 모두 반환
    public List<MyPickEntity> getMyPick(MyPickEntity myPickEntity){

        if(myPickRepository.existsByUserId(myPickEntity.getUserId())){
            return myPickRepository.findMyPickEntitiesByUserId(myPickEntity.getUserId());
        }else{
            throw new RuntimeException("there is no mypick");
        }
    }



    public void deleteMyPick(MyPickEntity myPickEntity){

        if(myPickRepository.existsByUserId(myPickEntity.getUserId())){
            myPickRepository.delete(myPickEntity);
        }else{
            throw new RuntimeException("there is no mypick");
        }


    }

}
