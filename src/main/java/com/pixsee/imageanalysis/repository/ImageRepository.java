package com.pixsee.imageanalysis.repository;

import com.pixsee.imageanalysis.entity.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageMetadata, Long> {
    List<ImageMetadata> findByUserId(Long userId);
}