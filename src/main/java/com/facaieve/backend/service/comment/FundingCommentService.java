package com.facaieve.backend.service.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.comment.FundingCommentRepository;
import com.facaieve.backend.service.post.FundingEntityService;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
@Service
@Slf4j
@AllArgsConstructor

public class FundingCommentService implements CommentService<FundingCommentEntity> {

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
        if(userEntity == null){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        FundingEntity fundingEntity = fundingEntityService.findFundingEntity(postCommentDTO.getPostId());
        if(fundingEntity == null){
            throw new BusinessLogicException(ExceptionCode.POST_NOT_FOUND);
        }

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

        if (fundingCommentRepository.existsById(fundingCommentEntityId)) {
            fundingCommentRepository.deleteByFundingCommentEntityId(fundingCommentEntityId);
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }

    }

    @Override
    @Transactional
    public TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO) {

        if (fundingCommentRepository.existsById(fetchCommentDTO.getCommentId())) {

            FundingCommentEntity fundingCommentUpdated = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentEntityId(fetchCommentDTO.getCommentId());
            fundingCommentUpdated.update(fetchCommentDTO.getCommentBody());
            //JPA 자동 context로 저장
            return totalCommentMapper.fundingCommentEntityToResponseCommentDto(fundingCommentUpdated);// 새로 저장된거 반환함.
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }

    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO getComment(Long commentId) {

        if (fundingCommentRepository.existsById(commentId)) {

            FundingCommentEntity fundingComment = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentEntityId(commentId);

            return totalCommentMapper.fundingCommentEntityToResponseCommentDto(fundingComment);// 새로 저장된거 반환함.
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }

    }

    @Override
    public void validatePickedUser(FundingCommentEntity fundingCommentEntity, TotalCommentDTO.PushingMyPickAtCommentDTO
            pushingMyPickAtCommentDTO) {

        List<UserEntity> pickedUserEntityList = fundingCommentEntity
                .getMyPickEntity().stream().map(MyPickEntity::getPickingUser).collect(Collectors.toList());

        for(UserEntity userEntity : pickedUserEntityList){
            if(userEntity.getUserEntityId() == pushingMyPickAtCommentDTO.getPushingUserId()){
                log.info("좋아요는 두번 누를 수 없습니다.");

                throw new BusinessLogicException(ExceptionCode.ALREADY_EXSIT_MYPICK_USER);//이미 좋아요를 누른 사람 이라는 오류를 발생시킴.
            }
        }

    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO pushMyPick(TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO) {

        FundingCommentEntity fundingComment = fundingCommentRepository
                .findFundingCommentEntityByFundingCommentEntityId(pushingMyPickAtCommentDTO.getCommentId());

        MyPickEntity myPickEntity = MyPickEntity.builder()
                .fundingCommentEntity(fundingComment)
                .pickingUser(userService.findUserEntityById(pushingMyPickAtCommentDTO.getPushingUserId()))
                .build();

        validatePickedUser(fundingComment,pushingMyPickAtCommentDTO);//validation method for picking user

        fundingComment.getMyPickEntity().add(myPickEntity);
        fundingComment.setMyPicks(fundingComment.getMyPickEntity().size());


        return totalCommentMapper.fundingCommentEntityToResponseCommentDto(fundingCommentRepository.save(fundingComment));
    }


}
