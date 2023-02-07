package com.facaieve.backend.service.post;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.awt.print.Pageable;
import java.util.List;
@Component
public interface Condition<T,E> {
    Page<T> conditionSort(E e, int pageIndex, int elementNum);

}
