package com.pixsee.imageanalysis.mapper;

import com.pixsee.imageanalysis.dto.ImageMetadataRequestDTO;
import com.pixsee.imageanalysis.dto.ImageMetadataResponseDTO;
import com.pixsee.imageanalysis.entity.ImageMetadata;

import java.time.Instant;

public class ImageMetadataMapper {

    public static ImageMetadata toEntity(ImageMetadataRequestDTO dto) {
        ImageMetadata entity = new ImageMetadata();
        entity.setUserId(dto.getUserId());
        entity.setOriginalFilename(dto.getOriginalFilename());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setFileSize(dto.getFileSize());
        entity.setFileType(dto.getFileType());
        entity.setUploadDate(Instant.now().getEpochSecond()); // Set the upload date during creation
        return entity;
    }

    public static ImageMetadataResponseDTO toResponseDTO(ImageMetadata entity) {
        ImageMetadataResponseDTO dto = new ImageMetadataResponseDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setOriginalFilename(entity.getOriginalFilename());
        dto.setUploadDate(entity.getUploadDate());
        dto.setWidth(entity.getWidth());
        dto.setHeight(entity.getHeight());
        dto.setFileSize(entity.getFileSize());
        dto.setFileType(entity.getFileType());
        return dto;
    }

    public static void updateEntityFromDTO(ImageMetadataRequestDTO dto, ImageMetadata entity) {
        entity.setOriginalFilename(dto.getOriginalFilename());
        entity.setWidth(dto.getWidth());
        entity.setHeight(dto.getHeight());
        entity.setFileSize(dto.getFileSize());
        entity.setFileType(dto.getFileType());
    }
}
