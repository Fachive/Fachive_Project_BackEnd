package com.facaieve.backend.service.comment;

import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.repository.comment.PortfolioCommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기

@Service
@Slf4j
@AllArgsConstructor

public class PortfolioCommentService implements CommentService<PortfolioCommentEntity,PortfolioCommentEntity> {

    @Autowired
    PortfolioCommentRepository portfolioCommentRepository;

    @Override
    public PortfolioCommentEntity createComment(PortfolioCommentEntity portfolioCommentEntity) {

        if(portfolioCommentRepository.existsById(portfolioCommentEntity.getPortfolioCommentId())){
            throw new RuntimeException("there is already ex");
        }else{
            return portfolioCommentRepository.save(portfolioCommentEntity);
        }
    }

    @Override
    public void deleteComment(PortfolioCommentEntity portfolioCommentEntity) {

        if(portfolioCommentRepository.existsById(portfolioCommentEntity.getPortfolioCommentId())){
            portfolioCommentRepository.delete(portfolioCommentEntity);
        }else{
            throw new RuntimeException("there is no kind of comment");

        }

    }

    @Override
    @Transactional

    public PortfolioCommentEntity modifyComment(PortfolioCommentEntity portfolioCommentEntity) {
        if(portfolioCommentRepository.existsById(portfolioCommentEntity.getPortfolioCommentId())){

            PortfolioCommentEntity portfolioCommentUpdated =
                portfolioCommentRepository
                        .findPortfolioCommentEntityByPortfolioCommentId(portfolioCommentEntity.getPortfolioCommentId());


            portfolioCommentUpdated.update(portfolioCommentEntity.getCommentBody());
            //JPA context 자동 저장
            return portfolioCommentUpdated;
        }else{
            throw new RuntimeException("there is no kind of comment");

        }

    }

    @Override
    public PortfolioCommentEntity getComment(PortfolioCommentEntity portfolioCommentEntity) {

        if(portfolioCommentRepository.existsById(portfolioCommentEntity.getPortfolioCommentId())){

            PortfolioCommentEntity portfolioComment =
                    portfolioCommentRepository
                            .findPortfolioCommentEntityByPortfolioCommentId(portfolioCommentEntity.getPortfolioCommentId());
            return portfolioComment;
        }else{
            throw new RuntimeException("there is no kind of comment");
        }
    }
}
