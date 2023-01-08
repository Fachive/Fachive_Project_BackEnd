package com.facaieve.backend.entity.etc;
import javax.persistence.*;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;

import lombok.*;

@Entity

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    Long categoryId;
    @Column
    String categoryName;

    @ManyToOne
    @JoinColumn(name = "fashion_Pickup_Entity_Id")
    FashionPickupEntity fashionPickupEntity;

    @ManyToOne
    @JoinColumn(name = "funding_Entity_Id")
    FundingEntity fundingEntity;
}
