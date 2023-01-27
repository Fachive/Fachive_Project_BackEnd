package com.facaieve.backend.service.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.repository.post.FundingRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@AllArgsConstructor
@Setter
public class FundingEntityService {

    FundingRepository fundingRepository;

    Condition condition;//정렬 메소드 가지고 있는 객체

    public Page<FundingEntity>  findFundingEntitiesByCondition(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum){
        return condition.conditionSort(categoryEntities,pageIndex,elementNum);
    }

    public FundingEntity createFundingEntity(FundingEntity fundingEntity){// 펀딩 게시글 작성
       return fundingRepository.save(fundingEntity);
    };

    public FundingEntity editFundingEntity(FundingEntity fundingEntity) {// 펀딩 게시글 수정

        FundingEntity newFundingEntity = new FundingEntity();
        Optional.ofNullable(fundingEntity.getTitle())// 제목 체크
                .ifPresent(newFundingEntity::setTitle);// 제목 수정
        Optional.ofNullable(fundingEntity.getBody())// 본문 체크
                .ifPresent(newFundingEntity::setBody);// 본문 수정
        Optional.ofNullable(fundingEntity.getFundedPrice())// 모금액 체크
                .ifPresent(newFundingEntity::setFundedPrice);// 모금액 수정
        Optional.ofNullable(fundingEntity.getTargetPrice())// 목표액 체크
                .ifPresent(newFundingEntity::setTargetPrice);// 목표액 수정

        return fundingRepository.save(newFundingEntity);

    }

    public FundingEntity findFundingEntity(long foundingFundingEntityId) {// 펀딩 게시글 호출
         return fundingRepository.findById(foundingFundingEntityId).orElseThrow();
    }

    public Page<FundingEntity> findFundingEntitiesByDate(int pageIndex) {// 펀딩 게시글 페이지 별로 호출(최신순)
        log.info("펀딩 게시물이 호출되었습니다.(등록일 최신순)");
        return fundingRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("updateTime").descending()));
    }

//    public Page<FundingEntity> findFundingEntitiesByMypick(int pageIndex) {// 펀딩 게시글 페이지 별로 호출(마이픽순)
//        log.info("펀딩 게시물이 호출되었습니다.(마이픽 숫자 기준)");
//        return fundingRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("myPick").descending()));// 마이픽 매핑 관련 에러 발생 가능능
//   }

    public void removeFundingEntity(long deletingFundingEntityId) {// 펀딩 게시글 삭제
        log.info("펀딩 게시물이 삭제되었습니다.");
        fundingRepository.deleteById(deletingFundingEntityId);
    }


}
