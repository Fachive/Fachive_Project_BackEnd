package com.facaieve.backend.service.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.comment.FashionPickupCommentRepository;
import com.facaieve.backend.service.post.FashionPickupEntityService;
import com.facaieve.backend.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
public class FashionPickupCommentService implements CommentService<FashionPickUpCommentEntity> {

    @Autowired
    FashionPickupCommentRepository fashionPickupCommentRepository;
    @Autowired
    FashionPickupEntityService fashionPickUpCommentService;
    @Autowired
    UserService userService;

    TotalCommentMapper totalCommentMapper;

    //댓글 생성
    public TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO) {


        UserEntity userEntity = userService.findUserEntityById(postCommentDTO.getUserId());
        if (userEntity == null) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        FashionPickupEntity fashionPickupEntity = fashionPickUpCommentService
                .findFashionPickupEntity(postCommentDTO.getPostId());
        if(fashionPickupEntity == null){
            throw new BusinessLogicException(ExceptionCode.POST_NOT_FOUND);
        }

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
    public void deleteComment(Long fashionPickUpCommentEntityId) {

        if (fashionPickupCommentRepository.existsById(fashionPickUpCommentEntityId)) {
            fashionPickupCommentRepository.deleteByFashionPickupCommentEntityId(fashionPickUpCommentEntityId);
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
    }

    //댓글 가져오기
    public TotalCommentDTO.ResponseCommentDTO getComment(Long fashionPickupEntityId) {

        if (fashionPickupCommentRepository.existsById(fashionPickupEntityId)) {
            FashionPickUpCommentEntity fashionPickUpComment =
                    fashionPickupCommentRepository.findById(fashionPickupEntityId).orElseThrow();
            return totalCommentMapper.fashionCommentEntityToResponseCommentDto(fashionPickUpComment);
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }
    }


    //사실 변경하는 거는 그냥 가져와서 새로운거 다시 넣고 반환할거임
    @Transactional
    public TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO) {

        if (fashionPickupCommentRepository.existsById(fetchCommentDTO.getCommentId())) {

            FashionPickUpCommentEntity fashionPickUpCommentUpdated = fashionPickupCommentRepository.
                    findFashionPickUpCommentEntityByFashionPickupCommentEntityId(fetchCommentDTO.getCommentId());

            fashionPickUpCommentUpdated.update(fetchCommentDTO.getCommentBody());
            //JPA 자동화 context로 저장함.
            return totalCommentMapper.fashionCommentEntityToResponseCommentDto(fashionPickUpCommentUpdated);
        } else {
            throw new BusinessLogicException(ExceptionCode.COMMENT_NOT_FOUND);
        }

    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO pushMyPick(TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO) {

        FashionPickUpCommentEntity fashionPickUpComment = fashionPickupCommentRepository
                .findFashionPickUpCommentEntityByFashionPickupCommentEntityId(pushingMyPickAtCommentDTO.getCommentId());

        MyPickEntity myPickEntity = MyPickEntity.builder()
                .fashionPickupCommentEntity(fashionPickUpComment)
                .pickingUser(userService.findUserEntityById(pushingMyPickAtCommentDTO.getPushingUserId()))
                .build();

        fashionPickUpComment.getMyPickEntity().add(myPickEntity);
        fashionPickUpComment.setMyPicks(fashionPickUpComment.getMyPickEntity().size());

        return totalCommentMapper
                .fashionCommentEntityToResponseCommentDto(fashionPickupCommentRepository
                        .save(fashionPickUpComment));
    }

    @Override
    public void validatePickedUser(FashionPickUpCommentEntity fashionPickUpCommentEntity, TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO) {
        List<UserEntity> pickedUserList = fashionPickUpCommentEntity.getMyPickEntity()
                .stream().map(MyPickEntity::getPickingUser).collect(Collectors.toList());

        for (UserEntity userEntity : pickedUserList) {
            if (userEntity.getUserEntityId() == pushingMyPickAtCommentDTO.getPushingUserId()) {
                log.info("좋아요는 두번 누를 수 없습니다.");
                throw new BusinessLogicException(ExceptionCode.ALREADY_EXSIT_MYPICK_USER);//이미 누른 사람이 또 누른 경우의 exception 을 발생
            }
        }
    }


}
