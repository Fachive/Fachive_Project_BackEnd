package com.facaieve.backend.repository.image;

import com.facaieve.backend.entity.image.ImageEntityProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntityProfile, Long> {

    Optional<ImageEntityProfile> findByFileName(String filename);
}
