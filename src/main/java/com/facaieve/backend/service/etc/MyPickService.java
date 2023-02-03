package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.exception.BusinessLogicException;
import com.facaieve.backend.exception.ExceptionCode;
import com.facaieve.backend.repository.comment.FashionPickupCommentRepository;
import com.facaieve.backend.repository.comment.FundingCommentRepository;
import com.facaieve.backend.repository.comment.PortfolioCommentRepository;
import com.facaieve.backend.repository.etc.MyPickRepository;
import com.facaieve.backend.repository.post.FashionPickupRepository;
import com.facaieve.backend.repository.post.FundingRepository;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class MyPickService {

    MyPickRepository myPickRepository;
    FashionPickupRepository fashionPickupRepository;
    FundingRepository fundingRepository;
    PortfolioRepository portfolioRepository;
    FashionPickupCommentRepository fashionPickupCommentRepository;
    FundingCommentRepository fundingCommentRepository;
    PortfolioCommentRepository portfolioCommentRepository;
    UserRepository userRepository;

    public void createMyPick(Long userId, String whatToPick, Long entityId) {

        UserEntity pickingUser = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        MyPickEntity myPickEntity = settingMyPickInfo(whatToPick, pickingUser, entityId).build();

        myPickRepository.save(myPickEntity);
        log.info("마이픽 정보가 저장되었습니다.");

    }


    public MyPickEntity.MyPickEntityBuilder settingMyPickInfo(String whatToPick, UserEntity pickingUser, Long entityId) {

        MyPickEntity.MyPickEntityBuilder pickEntity = MyPickEntity.builder().pickingUser(pickingUser);

        switch (whatToPick) {
            case "Portfolio":
                pickEntity.portfolioEntity(portfolioRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;

            case "FashionPickUp":
                pickEntity.fashionPickupEntity(fashionPickupRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;

            case "Funding":
                pickEntity.fundingEntity(fundingRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;

            case "PortfolioComment":
                pickEntity.portfolioCommentEntity(portfolioCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;

            case "FashionPickUpComment":
                pickEntity.fashionPickupCommentEntity(fashionPickupCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;

            case "FundingComment":
                pickEntity.fundingCommentEntity(fundingCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT))).build();
                break;
        }
        return pickEntity;
    }




    //유저가 누른 좋아요 모두 반환
    public List<MyPickEntity> getMyPick(MyPickEntity myPickEntity){

        if(myPickRepository.existsByPickingUser(myPickEntity.getPickingUser())){
            return myPickRepository.findMyPickEntitiesByPickingUser(myPickEntity.getPickingUser());
        }else{
            throw new RuntimeException("there is no mypick");
        }
    }



    @Transactional
    public void deleteMyPick(Long userId, String whatToPick, Long entityId){

        UserEntity pickingUser = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        switch (whatToPick) {
            case "Portfolio":
                PortfolioEntity portfolioEntity = portfolioRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByPortfolioEntityAndPickingUser(portfolioEntity, pickingUser);

                break;

            case "FashionPickUp":
                FashionPickupEntity fashionPickupEntity = fashionPickupRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByFashionPickupEntityAndPickingUser(fashionPickupEntity, pickingUser);
                break;

            case "Funding":
                FundingEntity fundingEntity = fundingRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByFundingEntityAndPickingUser(fundingEntity, pickingUser);

                break;

            case "PortfolioComment":
                PortfolioCommentEntity portfolioCommentEntity = portfolioCommentRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByPortfolioCommentEntityAndPickingUser(portfolioCommentEntity, pickingUser);

                break;

            case "FashionPickUpComment":
                FashionPickUpCommentEntity fashionPickUpCommentEntity = fashionPickupCommentRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByFashionPickupCommentEntityAndPickingUser(fashionPickUpCommentEntity, pickingUser);

                break;

            case "FundingComment":
                FundingCommentEntity fundingCommentEntity = fundingCommentRepository.findById(entityId).orElseThrow();
                myPickRepository.deleteByFundingCommentEntityAndPickingUser(fundingCommentEntity, pickingUser);
                break;
        }


    }

}
