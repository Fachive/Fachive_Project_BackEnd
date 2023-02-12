package com.facaieve.backend.service.post;

import com.facaieve.backend.dto.post.PortfolioDto;
import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.PortfolioRepository;
import com.facaieve.backend.service.post.conditionsImp.portfolio.FindPortfolioEntitiesByDueDate;
import com.facaieve.backend.service.post.conditionsImp.portfolio.FindPortfolioPickupEntitiesByMyPicks;
import com.facaieve.backend.service.post.conditionsImp.portfolio.FindPortfolioPickupEntitiesByViews;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@Setter
@RequiredArgsConstructor
public class PortfolioEntityService {

    @Autowired
    PortfolioRepository portfolioRepository;

    Condition condition = new FindPortfolioPickupEntitiesByViews(portfolioRepository);

    public PortfolioEntity createPortfolioEntity(PortfolioEntity PortfolioEntity) {// 포트폴리오 게시물 작성
        return portfolioRepository.save(PortfolioEntity);
    }

    ;

    public PortfolioEntity editPortfolioEntity(PortfolioEntity editingPortfolioEntity, PortfolioDto.PatchDto patchDto) {// 포트폴리오 게시물 수정

        Optional.ofNullable(patchDto.getChangedTitle())
                .ifPresent(editingPortfolioEntity::setTitle);
        Optional.ofNullable(patchDto.getChangedBody())
                .ifPresent(editingPortfolioEntity::setBody);
        Optional.ofNullable(patchDto.getChangedCategoryEntity())
                .ifPresent(editingPortfolioEntity::setCategoryEntity);
        Optional.ofNullable(patchDto.getChangedTagList())
                .ifPresent(editingPortfolioEntity::setTagEntities);
        Optional.ofNullable(patchDto.getS3ImgInfo())
                .ifPresent(editingPortfolioEntity::setS3ImgInfo);

        return portfolioRepository.save(editingPortfolioEntity);
    }

    public PortfolioEntity findPortfolioEntity(Long foundingPortfolioEntityId) {// 포트폴리오 게시물 호출(1개)
        PortfolioEntity foundPortfolio = portfolioRepository.findById(foundingPortfolioEntityId).orElseThrow();
        foundPortfolio.plusViewNum();
        portfolioRepository.save(foundPortfolio);
        return foundPortfolio;
    }

    public Page<PortfolioEntity> findPortfolioEntities(int pageIndex) {// 포트폴리오 게시물 호출(시간순)
        return portfolioRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("createdBy")));
    }

    public void removePortfolioEntity(PortfolioEntity deletingPortfolioEntity) {// 포트폴리오 게시물 삭제
        portfolioRepository.delete(deletingPortfolioEntity);
    }

    public void setCondition(String sortWay) {
        switch (sortWay) {
            case "myPick":
                this.condition = new FindPortfolioPickupEntitiesByMyPicks(portfolioRepository);
                break;
            case "update":
                this.condition = new FindPortfolioEntitiesByDueDate(portfolioRepository);
                break;
            default:
                this.condition = new FindPortfolioPickupEntitiesByViews(portfolioRepository);
                break;
        }

    }

    public Page<PortfolioEntity> findPortfolioEntitiesByCondition(CategoryEntity categoryEntity, int pageIndex, int elementNum) {
        return condition.conditionSort(categoryEntity, pageIndex, elementNum);
    }
}
