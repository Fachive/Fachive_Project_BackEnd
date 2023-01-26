package com.facaieve.backend.dto.multi;

import com.facaieve.backend.dto.PageInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Multi_ResponseDTO<T>{

    private List<T> data;
    private PageInfoDto pageInfo;

    public Multi_ResponseDTO(List<T> data, Page page) {
        this.data = data;
        this.pageInfo = new PageInfoDto(page.getNumber()+1, page.getSize(), page.getTotalElements(), page.getTotalPages()) ;
    }
}
