package com.facaieve.backend.entity.crossReference;


import com.facaieve.backend.entity.etc.TagEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FashionPickupEntityToTagEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long FashionPickupEntityToTagEntityId;

    @ManyToOne
    @JoinColumn(name = "tag_Entity_Id")
    TagEntity tagEntity;

    @ManyToOne
    @JoinColumn(name = "fashionPickup_Entity_Id")
    FashionPickupEntity fashionPickupEntity;




}
