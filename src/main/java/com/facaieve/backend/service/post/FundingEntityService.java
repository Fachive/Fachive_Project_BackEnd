package com.facaieve.backend.service.post;

import com.facaieve.backend.dto.post.FundingDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.repository.post.FundingRepository;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.funding.FindFundingEntitiesByMyPicks;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class FundingEntityService {
    @Autowired
    FundingRepository fundingRepository;

    Condition condition = new FindFundingEntitiesByDueDate(fundingRepository);;//정렬 메소드 가지고 있는 객체

    public void setCondition(String sortWay) {

        switch (sortWay){
            case "myPick" :  this.condition= new FindFundingEntitiesByMyPicks(fundingRepository);
                break;
            case "update" :  this.condition= new FindFundingEntitiesByDueDate(fundingRepository);
                break;
            default :  this.condition = new FindFundingEntitiesByDueDate(fundingRepository);
                break;
        }
    }

    public Page<FundingEntity>  findFundingEntitiesByCondition(CategoryEntity categoryEntity, int pageIndex, int elementNum){
        return condition.conditionSort(categoryEntity,pageIndex,elementNum);
    }

    @Transactional
    public FundingEntity createFundingEntity(FundingEntity fundingEntity){// 펀딩 게시글 작성
       return fundingRepository.save(fundingEntity);
    };

    public FundingEntity editFundingEntity(FundingEntity editingFundingEntity, FundingDto.PatchDto patchDto) {// 펀딩 게시글 수정


        Optional.ofNullable(patchDto.getChangedTitle())
                .ifPresent(editingFundingEntity::setTitle);
        Optional.ofNullable(patchDto.getChangedBody())
                .ifPresent(editingFundingEntity::setBody);
        Optional.ofNullable(patchDto.getChangedCategoryEntity())
                .ifPresent(editingFundingEntity::setCategoryEntity);
        Optional.ofNullable(patchDto.getChangedTagList())
                .ifPresent(editingFundingEntity::setTagEntities);
        Optional.ofNullable(patchDto.getS3ImgInfo())
                .ifPresent(editingFundingEntity::setS3ImgInfo);
        Optional.ofNullable(patchDto.getFundedPrice())
                .ifPresent(editingFundingEntity::setFundedPrice);
        Optional.ofNullable(patchDto.getTargetPrice())
                .ifPresent(editingFundingEntity::setTargetPrice);


        return fundingRepository.save(editingFundingEntity);

    }

    public FundingEntity findFundingEntity(Long foundingFundingEntityId) {// 펀딩 게시글 호출
         return fundingRepository.findById(foundingFundingEntityId).orElseThrow();
    }

    public Page<FundingEntity> findFundingEntitiesByDate(int pageIndex) {// 펀딩 게시글 페이지 별로 호출(최신순)
        log.info("펀딩 게시물이 호출되었습니다.(등록일 최신순)");
        return fundingRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("updateTime").descending()));
    }

    public void removeFundingEntity(Long deletingFundingEntityId) {// 펀딩 게시글 삭제
        log.info("펀딩 게시물이 삭제되었습니다.");
        fundingRepository.deleteById(deletingFundingEntityId);
    }

    public FundingDto.ResponseFundingDtoForEntity calculatingPercentage(FundingDto.ResponseFundingDtoForEntity responseFundingDto){
        System.out.println(responseFundingDto.getFundedPrice()/ responseFundingDto.getTargetPrice());

        responseFundingDto.setPercentage(
                (double) Math.round( (Double.valueOf(responseFundingDto.getFundedPrice()) / responseFundingDto.getTargetPrice()) * 100000)/1000.00);

        //소수점 둘째 자리까지 출력함.
        return responseFundingDto;
    }

}
