package com.facaieve.backend.service.comment;

public interface CommentService<T,E> {

    T createComment(E e);
    void deleteComment(E e);
    T modifyComment(E e);
    T getComment(E e);

}
