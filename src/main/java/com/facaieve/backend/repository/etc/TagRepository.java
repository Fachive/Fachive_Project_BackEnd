package com.facaieve.backend.repository.etc;

import com.facaieve.backend.entity.etc.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity,Long> {

    boolean existsByTagName(String tagName);//중복 방지를 위해서 생성함.
    TagEntity findByTagName(String tagName);
    TagEntity findTagEntityByTagId(Long tagId);//tagId로 찾음

}
