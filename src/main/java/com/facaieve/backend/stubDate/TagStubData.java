package com.facaieve.backend.stubDate;

import com.facaieve.backend.entity.basetime.BaseEntity;
import com.facaieve.backend.entity.post.FashionPickupEntity;
import com.facaieve.backend.entity.post.FundingEntity;
import com.facaieve.backend.entity.post.PortfolioEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagStubData {

    long tagId = 1L;

    String tagName = "생애 첫 태그!";

    String description = "test 태그 설명";

}
