package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.repository.etc.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {

    TagRepository tagRepository;

    //태그의 중복 생성을 방지하고 이미 가지고 있는 태그의 생성요청이 왔다면 기존에 존재하는 태그 객체를 반환하게 수정함
    public TagEntity createTagEntity(final String tagName){
        TagEntity tagEntity = new TagEntity(tagName);
        if(isTagExist(tagEntity))
            return tagRepository.save(tagEntity);
        else
            return tagEntity;
    }



    @Transactional
    public TagEntity modifyTagEntity(TagEntity tagEntity, String NewTagName){

        if(isTagExist(tagEntity)){

            tagEntity.update(NewTagName);
            //JPA context에 의해서 자동으로 저장됨.

            return tagRepository.save(tagEntity);//변경된 객체를 반환함.

        }else{
            throw new RuntimeException("there is no kind of tagEntity" + tagEntity.getTagName());
        }

    }

    public TagEntity getTagEntityByTagName(String tagName){

        if(tagRepository.existsByTagName(tagName)){
            TagEntity tagEntity = tagRepository.findById(tagName).orElseThrow();
            return tagEntity;
        }else{
            //ex
            throw new RuntimeException("there is no kind of tag!");
        }

    }

    public TagEntity deleteTagEntityService(TagEntity tagEntity){// 존재하지 않는 tag 삭제 방지

       if(isTagExist(tagEntity)){
           //delete
           tagRepository.delete(tagEntity);
           return tagEntity;
       }else{
           //ex
           throw new RuntimeException("there no kind of tag!");
       }
    }


    private boolean isTagExist(TagEntity tagEntity) {
        return findByTagName(tagEntity.getTagName()).isEmpty();
    }

    private Optional<TagEntity> findByTagName(String tagEntity) {
        return tagRepository.findById(tagEntity);
    }

}
