package com.facaieve.backend.entity.crossReference;


import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundingEntityToTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long FundingEntityToTagEntityId;

    @ManyToOne
    @JoinColumn(name = "tag_Name")
    TagEntity tagEntity;

    @ManyToOne
    @JoinColumn(name = "funding_Entity_Id")
    FundingEntity fundingEntity;

}
