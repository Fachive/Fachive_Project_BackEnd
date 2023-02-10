package com.facaieve.backend.controller.comment;

import org.springframework.http.ResponseEntity;

public interface Comment<T,E> {
    public ResponseEntity postComment(T t,E e);
    public ResponseEntity getComment(T t,E e);
    public ResponseEntity fetchComment(T t,E e);
    public ResponseEntity deleteComment(T t,E e);
}
