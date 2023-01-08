package com.facaieve.backend.service.comment;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.repository.comment.FashionPickupCommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
public class FashionPickupCommentService implements CommentService<FashionPickUpCommentEntity,FashionPickUpCommentEntity>{

    @Autowired
    FashionPickupCommentRepository fashionPickupCommentRepository;

    //댓글 생성
    public FashionPickUpCommentEntity createComment(FashionPickUpCommentEntity fashionPickUpCommentEntity){

        if(fashionPickupCommentRepository.existsById(fashionPickUpCommentEntity.getFashionPickupCommentEntityId())){
            throw new RuntimeException("there is already comment");

        }else{

            FashionPickUpCommentEntity fashionPickUpCommentSaved =
                    fashionPickupCommentRepository.save(fashionPickUpCommentEntity);
            return fashionPickUpCommentSaved;
        }

    }
    //댓글 삭제
    public void deleteComment(FashionPickUpCommentEntity fashionPickUpCommentEntity){

        if(fashionPickupCommentRepository.existsById(fashionPickUpCommentEntity.getFashionPickupCommentEntityId())){
            fashionPickupCommentRepository.delete(fashionPickUpCommentEntity);
        }else{
            throw new RuntimeException("there is no kind of comment");
        }

    }

    //댓글 가져오기
    public FashionPickUpCommentEntity getComment(FashionPickUpCommentEntity fashionPickUpComment){

        if(fashionPickupCommentRepository.existsById(fashionPickUpComment.getFashionPickupCommentEntityId())){

             Optional<FashionPickUpCommentEntity> fashionPickUpCommentEntityList =
                     fashionPickupCommentRepository.findById(fashionPickUpComment.getFashionPickupCommentEntityId());

             return fashionPickUpCommentEntityList.get();
        }else{

            throw new RuntimeException("there is no Comment");
        }
    }

    //사실 변경하는 거는 그냥 가져와서 새로운거 다시 넣고 반환할거임
    @Transactional
    public FashionPickUpCommentEntity modifyComment(FashionPickUpCommentEntity fashionPickUpCommentEntity){

        if(fashionPickupCommentRepository.existsById(fashionPickUpCommentEntity.getFashionPickupCommentEntityId())){

            FashionPickUpCommentEntity fashionPickUpCommentUpdated = fashionPickupCommentRepository.
                    findFashionPickUpCommentEntityByFashionPickupCommentEntityId(fashionPickUpCommentEntity
                            .getFashionPickupCommentEntityId());

            fashionPickUpCommentUpdated.update(fashionPickUpCommentEntity.getCommentBody());
            //JPA 자동화 context로 저장함.
            return fashionPickUpCommentUpdated;

        }else{

            throw new RuntimeException("there is no comment");
        }

    }





}
