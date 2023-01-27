package com.facaieve.backend.service.post;

import com.facaieve.backend.entity.etc.CategoryEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import com.facaieve.backend.repository.post.PortfolioRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Setter
@AllArgsConstructor
public class PortfolioEntityService {

    PortfolioRepository portfolioRepository;

    Condition condition;
    public PortfolioEntity createPortfolioEntity(PortfolioEntity PortfolioEntity){// 포트폴리오 게시물 작성
        return portfolioRepository.save(PortfolioEntity);
    };

    public PortfolioEntity editPortfolioEntity(PortfolioEntity PortfolioEntity) {// 포트폴리오 게시물 수정
        PortfolioEntity editingPortfolioEntity = new PortfolioEntity();
        Optional.ofNullable(PortfolioEntity.getTitle())
                .ifPresent(editingPortfolioEntity::setTitle);
        Optional.ofNullable(PortfolioEntity.getBody())
                .ifPresent(editingPortfolioEntity::setBody);

        return portfolioRepository.save(editingPortfolioEntity);
    }

    public PortfolioEntity findPortfolioEntity(long foundingPortfolioEntityId) {// 포트폴리오 게시물 호출(1개)
        return portfolioRepository.findById(foundingPortfolioEntityId).orElseThrow();
    }

    public Page<PortfolioEntity> findPortfolioEntities(int pageIndex) {// 포트폴리오 게시물 호출(시간순)
        return portfolioRepository.findAll(PageRequest.of(pageIndex, 30, Sort.by("createdBy")));
    }

    public void removePortfolioEntity(long deletingPortfolioEntityId) {// 포트폴리오 게시물 삭제
        portfolioRepository.deleteById(deletingPortfolioEntityId);
    }

    public Page<PortfolioEntity>  findPortfolioEntitiesByCondition(List<CategoryEntity> categoryEntities, int pageIndex, int elementNum){
        return condition.conditionSort(categoryEntities,pageIndex,elementNum);
    }
}
