package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.repository.etc.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTML;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class TagService {

    TagRepository tagRepository;

    //태그의 중복 생성을 방지하고 이미 가지고 있는 태그의 생성요청이 왔다면 기존에 존재하는 태그 객체를 반환하게 수정함
    public TagEntity createTagEntity(TagEntity tagEntity){

        if(tagRepository.findByTagName(tagEntity.getTagName())!=null){
            return tagRepository.findByTagName(tagEntity.getTagName());
        }else{
            TagEntity savedTagEntity = tagRepository.save(tagEntity);
            return savedTagEntity;
        }
    }

    @Transactional
    public TagEntity modifyTagEntity(TagEntity tagEntity){

        if(tagRepository.existsByTagName(tagEntity.getTagName())){

            TagEntity tagEntityChanged = tagRepository.findTagEntityByTagId(tagEntity.getTagId());
            tagEntityChanged.update(tagEntity.getTagName(),tagEntity.getDescription());
            //JPA context에 의해서 자동으로 저장됨.

            return tagEntityChanged;//변경된 객체를 반환함.

        }else{
            throw new RuntimeException("there is no kind of tagEntity" + tagEntity.getTagName());
        }

    }

    public TagEntity getTagEntityByTagName(String tagName){

        if(tagRepository.existsByTagName(tagName)){
            TagEntity tagEntity = tagRepository.findByTagName(tagName);
            return tagEntity;
        }else{
            //ex
            throw new RuntimeException("there is no kind of tag!");
        }

    }

    public TagEntity deleteTagEntityService(TagEntity tagEntity){// 존재하지 않는 tag 삭제 방지

       if(tagRepository.existsById(tagEntity.getTagId())){
           //delete
           tagRepository.delete(tagEntity);
           return tagEntity;
       }else{
           //ex
           throw new RuntimeException("there no kind of tag!");
       }
    }

}
