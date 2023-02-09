package com.facaieve.backend.service.comment;

import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.repository.comment.FundingCommentRepository;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
@Service
@Slf4j
@AllArgsConstructor

public class FundingCommentService implements CommentService {

    @Autowired
    FundingCommentRepository fundingCommentRepository;
    @Autowired
    UserService userService;
    @Autowired
    FundingEntityService fundingEntityService;

    TotalCommentMapper totalCommentMapper;

    @Override
    public TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO) {

            UserEntity userEntity = userService.findUserEntityById(postCommentDTO.getUserId());
            FundingEntity fundingEntity = fundingEntityService.findFundingEntity(postCommentDTO.getPostId());

            FundingCommentEntity fundingCommentEntity =
                    totalCommentMapper.totalPostCommentDtoToFundingCommentEntity(postCommentDTO);
            //todo mapper class 제대로 구현할것

            fundingCommentEntity.setFundingEntity(fundingEntity);
            fundingCommentEntity.setUserEntity(userEntity);
            fundingCommentEntity.setMyPickEntity(new ArrayList<>());

            FundingCommentEntity savedFundingComment = fundingCommentRepository.save(fundingCommentEntity);
            return totalCommentMapper.fundingCommentEntityToResponseCommentDto(savedFundingComment);


    }

    @Override
    public void deleteComment(Long fundingCommentEntityId) {

        if(fundingCommentRepository.existsById(fundingCommentEntityId)){
            fundingCommentRepository.deleteByFundingCommentEntityId(fundingCommentEntityId);
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }

    @Override
    @Transactional
    public TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO) {

        if(fundingCommentRepository.existsById(fetchCommentDTO.getCommentId())){

            FundingCommentEntity fundingCommentUpdated = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentEntityId(fetchCommentDTO.getCommentId());
            fundingCommentUpdated.update(fetchCommentDTO.getCommentBody());
            //JPA 자동 context로 저장
            return totalCommentMapper.fundingCommentEntityToResponseCommentDto(fundingCommentUpdated);// 새로 저장된거 반환함.
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO getComment(Long commentId) {

        if(fundingCommentRepository.existsById(commentId)){

            FundingCommentEntity fundingComment = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentEntityId(commentId);

            return totalCommentMapper.fundingCommentEntityToResponseCommentDto(fundingComment);// 새로 저장된거 반환함.
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }
}
