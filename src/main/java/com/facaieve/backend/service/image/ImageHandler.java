package com.facaieve.backend.service.image;

public interface ImageHandler<E,T> {
    E getImage(T t);
    E modifyImage(T t);
    String deleteImage(T t);
    E createImage(T t);

}
