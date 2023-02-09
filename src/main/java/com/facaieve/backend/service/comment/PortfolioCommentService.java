package com.facaieve.backend.service.comment;

import com.facaieve.backend.dto.comment.TotalCommentDTO;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.comment.TotalCommentMapper;
import com.facaieve.backend.repository.comment.PortfolioCommentRepository;
import com.facaieve.backend.service.post.PortfolioEntityService;
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

public class PortfolioCommentService implements CommentService {

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
}
