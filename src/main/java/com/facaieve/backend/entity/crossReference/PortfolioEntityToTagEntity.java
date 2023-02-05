package com.facaieve.backend.entity.crossReference;


import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PortfolioEntityToTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long PortfolioEntityToTagEntityId;

    @ManyToOne
    @JoinColumn(name = "tag_Name")
    TagEntity tagEntity;

    @ManyToOne
    @JoinColumn(name = "portfolio_Entity_Id")
    PortfolioEntity portfolioEntity;

}
