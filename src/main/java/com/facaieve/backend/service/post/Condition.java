package com.facaieve.backend.service.post;

import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface Condition<T,E> {
    Page<T> conditionSort(List<E> eList, int pageIndex, int elementNum);
}
