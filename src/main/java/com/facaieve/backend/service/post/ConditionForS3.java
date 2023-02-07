package com.facaieve.backend.service.post;

import java.util.*;
import org.springframework.stereotype.Component;

@Component
public interface ConditionForS3<T, E> {
    E conditionSort(T t, E e);
}
