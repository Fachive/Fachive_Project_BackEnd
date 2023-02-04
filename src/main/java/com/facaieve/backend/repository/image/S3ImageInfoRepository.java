package com.facaieve.backend.repository.image;

import com.facaieve.backend.entity.image.S3ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface S3ImageInfoRepository extends JpaRepository<S3ImageInfo, Long> {
    void deleteByFileName(String fileName);
}
