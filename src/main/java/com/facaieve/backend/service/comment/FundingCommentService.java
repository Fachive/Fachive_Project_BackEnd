package com.facaieve.backend.service.comment;

import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.repository.comment.FundingCommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//todo 찬일님과 구현방식 상의해서 변경할거 변경할 수 있게 만들기
@Service
@Slf4j
@AllArgsConstructor

public class FundingCommentService implements CommentService<FundingCommentEntity,FundingCommentEntity> {

    @Autowired
    FundingCommentRepository fundingCommentRepository;

    @Override
    public FundingCommentEntity createComment(FundingCommentEntity fundingCommentEntity) {
        if(fundingCommentRepository.existsById(fundingCommentEntity.getFundingCommentId())){
            throw new RuntimeException("there is already ex");
        }else{
            FundingCommentEntity fundingCommentSaved = fundingCommentRepository.save(fundingCommentEntity);
            return fundingCommentSaved;
        }
    }

    @Override
    public void deleteComment(FundingCommentEntity fundingCommentEntity) {

        if(fundingCommentRepository.existsById(fundingCommentEntity.getFundingCommentId())){
            fundingCommentRepository.delete(fundingCommentEntity);
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }

    @Override
    @Transactional

    public FundingCommentEntity modifyComment(FundingCommentEntity fundingCommentEntity) {

        if(fundingCommentRepository.existsById(fundingCommentEntity.getFundingCommentId())){

            FundingCommentEntity fundingCommentUpdated = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentId(fundingCommentEntity.getFundingCommentId());

            fundingCommentUpdated.update(fundingCommentEntity.getCommentBody());
            //JPA 자동 context로 저장
            return fundingCommentUpdated;// 새로 저장된거 반환함.
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }

    @Override
    public FundingCommentEntity getComment(FundingCommentEntity fundingCommentEntity) {

        if(fundingCommentRepository.existsById(fundingCommentEntity.getFundingCommentId())){

            FundingCommentEntity fundingComment = fundingCommentRepository.
                    findFundingCommentEntityByFundingCommentId(fundingCommentEntity.getFundingCommentId());

            return fundingComment;// 새로 저장된거 반환함.
        }else{
            throw new RuntimeException("there is no kind of funding comment");
        }

    }
}
