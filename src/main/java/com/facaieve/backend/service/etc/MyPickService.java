package com.facaieve.backend.service.etc;

import com.facaieve.backend.entity.comment.FashionPickUpCommentEntity;
import com.facaieve.backend.entity.comment.FundingCommentEntity;
import com.facaieve.backend.entity.comment.PortfolioCommentEntity;
import com.facaieve.backend.entity.etc.MyPickEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.entity.user.UserEntity;
import com.facaieve.backend.mapper.exception.BusinessLogicException;
import com.facaieve.backend.mapper.exception.ExceptionCode;
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

        //유저 확인
        UserEntity pickingUser = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));


        MyPickEntity myPickEntity = settingMyPickInfo(whatToPick, pickingUser, entityId).build();

        myPickRepository.save(myPickEntity);
        log.info("마이픽 정보가 저장되었습니다.");

    }

    @Transactional
    public MyPickEntity.MyPickEntityBuilder settingMyPickInfo(String whatToPick, UserEntity pickingUser, Long entityId) {

        MyPickEntity.MyPickEntityBuilder pickEntity = MyPickEntity.builder().pickingUser(pickingUser);

        switch (whatToPick) {
            case "PORTFOLIO":
                PortfolioEntity portfolioEntity = portfolioRepository.findById(entityId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                portfolioEntity.plusMypickNum();//좋아요 +1
                pickEntity.portfolioEntity(portfolioEntity).build();
                portfolioRepository.save(portfolioEntity);

                break;

            case "FASHIONPICKUP":
                FashionPickupEntity fashionPickupEntity = fashionPickupRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                fashionPickupEntity.plusMypickNum();
                pickEntity.fashionPickupEntity(fashionPickupEntity).build();
                fashionPickupRepository.save(fashionPickupEntity);
                break;

            case "FUNDING":
                FundingEntity fundingEntity = fundingRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                fundingEntity.plusMypickNum();
                pickEntity.fundingEntity(fundingEntity).build();
                fundingRepository.save(fundingEntity);
                break;

            case "PORTFOLIO COMMENT":
                PortfolioCommentEntity portfolioCommentEntity = portfolioCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                portfolioCommentEntity.plusMypickNum();
                pickEntity.portfolioCommentEntity(portfolioCommentEntity).build();
                portfolioCommentRepository.save(portfolioCommentEntity);
                break;

            case "FASHIONPICKUP COMMENT":
                FashionPickUpCommentEntity fashionPickUpCommentEntity =  fashionPickupCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                fashionPickUpCommentEntity.plusMypickNum();
                pickEntity.fashionPickupCommentEntity(fashionPickUpCommentEntity).build();
                fashionPickupCommentRepository.save(fashionPickUpCommentEntity);
                break;

            case "FUNDING COMMENT":
                FundingCommentEntity fundingCommentEntity = fundingCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                fundingCommentEntity.plusMypickNum();
                pickEntity.fundingCommentEntity(fundingCommentEntity).build();
                fundingCommentRepository.save(fundingCommentEntity);
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
        //유저 체크(create와 달리 유저 확인을 한 메서드에서 처리
        UserEntity pickingUser = userRepository.findById(userId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        switch (whatToPick) {
            case "PORTFOLIO":
                PortfolioEntity portfolioEntity = portfolioRepository.findById(entityId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByPortfolioEntityAndPickingUser(portfolioEntity,pickingUser);
                portfolioEntity.minusMypickNum();//좋아요 -1

                portfolioRepository.save(portfolioEntity);

                break;

            case "FASHIONPICKUP":
                FashionPickupEntity fashionPickupEntity = fashionPickupRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByFashionPickupEntityAndPickingUser(fashionPickupEntity,pickingUser);
                fashionPickupEntity.minusMypickNum();

                fashionPickupRepository.save(fashionPickupEntity);
                break;

            case "FUNDING":
                FundingEntity fundingEntity = fundingRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByFundingEntityAndPickingUser(fundingEntity,pickingUser);
                fundingEntity.minusMypickNum();

                fundingRepository.save(fundingEntity);
                break;

            case "PORTFOLIO COMMENT":
                PortfolioCommentEntity portfolioCommentEntity = portfolioCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByPortfolioCommentEntityAndPickingUser(portfolioCommentEntity,pickingUser);
                portfolioCommentEntity.minusMypickNum();

                portfolioCommentRepository.save(portfolioCommentEntity);
                break;

            case "FASHIONPICKUP COMMENT":
                FashionPickUpCommentEntity fashionPickUpCommentEntity =  fashionPickupCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByFashionPickupCommentEntityAndPickingUser(fashionPickUpCommentEntity,pickingUser);
                fashionPickUpCommentEntity.minusMypickNum();

                fashionPickupCommentRepository.save(fashionPickUpCommentEntity);
                break;

            case "FUNDING COMMENT":
                FundingCommentEntity fundingCommentEntity = fundingCommentRepository.findById(entityId)
                        .orElseThrow(() -> new BusinessLogicException(ExceptionCode.NO_SUCH_ELEMENT));
                myPickRepository.deleteByFundingCommentEntityAndPickingUser(fundingCommentEntity,pickingUser);
                fundingCommentEntity.minusMypickNum();

                fundingCommentRepository.save(fundingCommentEntity);
                break;
        }


    }

}
