package com.facaieve.backend.repository.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface conditionRepo<T,E> {
    Page<T> findAsCondition(E e, Pageable pageable);
}
