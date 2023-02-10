package com.facaieve.backend.service.comment;

import com.facaieve.backend.Constant.PostType;
import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
import com.facaieve.backend.repository.comment.PortfolioCommentRepository;
import com.facaieve.backend.service.post.PortfolioEntityService;
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

public class PortfolioCommentService implements CommentService<PortfolioCommentEntity> {

    @Autowired
    PortfolioCommentRepository portfolioCommentRepository;
    @Autowired
    UserService userService;
    @Autowired
    PortfolioEntityService portfolioEntityService;

    TotalCommentMapper totalCommentMapper;

    @Override
    public TotalCommentDTO.ResponseCommentDTO createComment(TotalCommentDTO.PostCommentDTO postCommentDTO) {

        UserEntity userEntity = userService.findUserEntityById(postCommentDTO.getUserId());
        PortfolioEntity portfolio = portfolioEntityService.findPortfolioEntity(postCommentDTO.getPostId());

        PortfolioCommentEntity portfolioComment =
                totalCommentMapper.totalPostCommentDtoToPortfolioCommentEntity(postCommentDTO);
        //todo mapper class 제대로 구현할것

        portfolioComment.setPortfolioEntity(portfolio);
        portfolioComment.setUserEntity(userEntity);
        portfolioComment.setMyPickEntity(new ArrayList<>());

        PortfolioCommentEntity portfolioCommentEntity =
                portfolioCommentRepository.save(portfolioComment);


        return totalCommentMapper.portfolioCommentEntityToResponseCommentDto(portfolioCommentEntity);

    }

    @Override
    public void deleteComment(Long portfolioCommentEntityId) {

        if(portfolioCommentRepository.existsById(portfolioCommentEntityId)){
            portfolioCommentRepository.deleteByPortfolioCommentEntityId(portfolioCommentEntityId);
        }else{
            throw new RuntimeException("there is no kind of comment");
        }

    }

    @Override
    @Transactional
    public TotalCommentDTO.ResponseCommentDTO modifyComment(TotalCommentDTO.FetchCommentDTO fetchCommentDTO) {
        if(portfolioCommentRepository.existsById(fetchCommentDTO.getCommentId())){

            PortfolioCommentEntity portfolioCommentUpdated =
                portfolioCommentRepository
                        .findPortfolioCommentEntityByPortfolioCommentEntityId(fetchCommentDTO.getCommentId());

            portfolioCommentUpdated.update(fetchCommentDTO.getCommentBody());
            //JPA context 자동 저장
            return totalCommentMapper.portfolioCommentEntityToResponseCommentDto(portfolioCommentUpdated);
        }else{
            throw new RuntimeException("there is no kind of comment");

        }

    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO getComment(Long portfolioEntityId) {

        if(portfolioCommentRepository.existsById(portfolioEntityId)){

            PortfolioCommentEntity portfolioComment =
                    portfolioCommentRepository
                            .findPortfolioCommentEntityByPortfolioCommentEntityId(portfolioEntityId);
            return totalCommentMapper.portfolioCommentEntityToResponseCommentDto(portfolioComment);
        }else{
            throw new RuntimeException("there is no kind of comment");
        }
    }
//    이미 좋아요를 누른 user 가 다시 같은 게시글에 좋아요를 누르 못하게 하는 메소드
    @Override
     public void validatePickedUser(PortfolioCommentEntity portfolioComment,
                                    TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO){

        List<UserEntity> pickedUserList =
                portfolioComment.getMyPickEntity().stream().map(MyPickEntity::getPickingUser).collect(Collectors.toList());

        for(UserEntity userEntity : pickedUserList){
            log.info("좋아요는 두번 누를 수 없습니다.");
            if(userEntity.getUserEntityId() == pushingMyPickAtCommentDTO.getPushingUserId()){
                throw new BusinessLogicException(ExceptionCode.ALREADY_EXSIT_MYPICK_USER);
            }
        }
    }

    @Override
    public TotalCommentDTO.ResponseCommentDTO pushMyPick(TotalCommentDTO.PushingMyPickAtCommentDTO pushingMyPickAtCommentDTO) {
        PortfolioCommentEntity portfolioComment = portfolioCommentRepository
                .findPortfolioCommentEntityByPortfolioCommentEntityId(pushingMyPickAtCommentDTO.getCommentId());

        MyPickEntity myPickEntity = MyPickEntity.builder()
                .portfolioCommentEntity(portfolioComment)
                .pickingUser(userService.findUserEntityById(pushingMyPickAtCommentDTO.getPushingUserId()))
                .build();

        validatePickedUser(portfolioComment,pushingMyPickAtCommentDTO);//좋아요를 누른 사람 validation logic implementation

        portfolioComment.getMyPickEntity().add(myPickEntity);

        return totalCommentMapper
                .portfolioCommentEntityToResponseCommentDto(portfolioCommentRepository.save(portfolioComment));

    }




}
