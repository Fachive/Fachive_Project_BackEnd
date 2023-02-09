package com.facaieve.backend.service.comment;

import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.repository.comment.FashionPickupCommentRepository;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Slf4j
@AllArgsConstructor

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
public class FashionPickupCommentService implements CommentService{

    @Autowired
    FashionPickupCommentRepository fashionPickupCommentRepository;
    @Autowired
    FashionPickupEntityService fashionPickUpCommentService;
    @Autowired
    UserService userService;

    TotalCommentMapper totalCommentMapper;

    //댓글 생성
    public TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO){


            UserEntity userEntity = userService.findUserEntityById(postCommentDTO.getUserId());
            FashionPickupEntity fashionPickupEntity = fashionPickUpCommentService
                    .findFashionPickupEntity(postCommentDTO.getPostId());

            FashionPickUpCommentEntity fashionPickUpComment =
                    totalCommentMapper.totalPostCommentDtoToFashionPickupCommentEntity(postCommentDTO);
            //todo mapper class 제대로 구현할것

            fashionPickUpComment.setFashionPickupEntity(fashionPickupEntity);
            fashionPickUpComment.setUserEntity(userEntity);
            fashionPickUpComment.setMyPickEntity(new ArrayList<>());//초기에 빈 값으로 생성하는 과정이 필요함 자동으로 생성이 안됨.

            FashionPickUpCommentEntity fashionPickUpCommentSaved =
                    fashionPickupCommentRepository.save(fashionPickUpComment);


            return totalCommentMapper.fashionCommentEntityToResponseCommentDto(fashionPickUpCommentSaved);


    }
    //댓글 삭제
    public void deleteComment(Long fashionPickUpCommentEntityId){

        if(fashionPickupCommentRepository.existsById(fashionPickUpCommentEntityId)){
            fashionPickupCommentRepository.deleteByFashionPickupCommentEntityId(fashionPickUpCommentEntityId);
        }else{
            throw new RuntimeException("there is no kind of comment");
        }
    }

    //댓글 가져오기
    public TotalCommentDTO.ResponseCommentDTO getComment(Long fashionPickupEntityId){

        if(fashionPickupCommentRepository.existsById(fashionPickupEntityId)){
             FashionPickUpCommentEntity fashionPickUpComment =
                     fashionPickupCommentRepository.findById(fashionPickupEntityId).orElseThrow();
            return totalCommentMapper.fashionCommentEntityToResponseCommentDto(fashionPickUpComment);
        }else{

            throw new RuntimeException("there is no Comment");
        }
    }

    //사실 변경하는 거는 그냥 가져와서 새로운거 다시 넣고 반환할거임
    @Transactional
    public TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO){

        if(fashionPickupCommentRepository.existsById(fetchCommentDTO.getCommentId())){

            FashionPickUpCommentEntity fashionPickUpCommentUpdated = fashionPickupCommentRepository.
                    findFashionPickUpCommentEntityByFashionPickupCommentEntityId(fetchCommentDTO.getCommentId());

            fashionPickUpCommentUpdated.update(fetchCommentDTO.getCommentBody());
            //JPA 자동화 context로 저장함.
            return totalCommentMapper.fashionCommentEntityToResponseCommentDto(fashionPickUpCommentUpdated);
        }else{

            throw new RuntimeException("there is no comment");
        }

    }





}
