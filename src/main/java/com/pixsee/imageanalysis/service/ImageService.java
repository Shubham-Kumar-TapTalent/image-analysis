package com.pixsee.imageanalysis.service;

import com.pixsee.imageanalysis.dto.ImageMetadataRequestDTO;
import com.pixsee.imageanalysis.dto.ImageMetadataResponseDTO;
import com.pixsee.imageanalysis.exception.ResourceNotFoundException;
import com.pixsee.imageanalysis.entity.ImageMetadata;
import com.pixsee.imageanalysis.mapper.ImageMetadataMapper;
import com.pixsee.imageanalysis.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public ImageMetadataResponseDTO saveImageMetadata(ImageMetadataRequestDTO imageMetadataRequestDTO) {
        ImageMetadata imageMetadata = ImageMetadataMapper.toEntity(imageMetadataRequestDTO);
        ImageMetadata savedImageMetadata = imageRepository.save(imageMetadata);
        return ImageMetadataMapper.toResponseDTO(savedImageMetadata);
    }

    public List<ImageMetadataResponseDTO> getImagesByUserId(Long userId) {
        List<ImageMetadata> imageMetadataList = imageRepository.findByUserId(userId);
        return imageMetadataList.stream()
                .map(ImageMetadataMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ImageMetadataResponseDTO getImageById(Long id) {
        ImageMetadata imageMetadata = getImageEntityById(id);
        return ImageMetadataMapper.toResponseDTO(imageMetadata);
    }

    public ImageMetadataResponseDTO updateImageMetadata(Long id, ImageMetadataRequestDTO imageMetadataRequestDTO) {
        ImageMetadata existingImageMetadata = getImageEntityById(id);
        ImageMetadataMapper.updateEntityFromDTO(imageMetadataRequestDTO, existingImageMetadata);
        ImageMetadata updatedImageMetadata = imageRepository.save(existingImageMetadata);
        return ImageMetadataMapper.toResponseDTO(updatedImageMetadata);
    }

    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }

    private ImageMetadata getImageEntityById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }
}

