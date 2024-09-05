package com.pixsee.imageanalysis.controller;

import com.pixsee.imageanalysis.dto.ImageMetadataRequestDTO;
import com.pixsee.imageanalysis.dto.ImageMetadataResponseDTO;
import com.pixsee.imageanalysis.service.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping
    public ResponseEntity<ImageMetadataResponseDTO> uploadImage(@RequestBody @Valid ImageMetadataRequestDTO imageMetadataRequestDTO) {
        ImageMetadataResponseDTO createdImage = imageService.saveImageMetadata(imageMetadataRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdImage);
    }

    @GetMapping
    public ResponseEntity<List<ImageMetadataResponseDTO>> listImages(@RequestParam Long userId) {
        List<ImageMetadataResponseDTO> images = imageService.getImagesByUserId(userId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageMetadataResponseDTO> getImageDetails(@PathVariable Long id) {
        ImageMetadataResponseDTO image = imageService.getImageById(id);
        return ResponseEntity.ok(image);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ImageMetadataResponseDTO> updateImageMetadata(@PathVariable Long id, @RequestBody @Valid ImageMetadataRequestDTO imageMetadataRequestDTO) {
        ImageMetadataResponseDTO updatedImage = imageService.updateImageMetadata(id, imageMetadataRequestDTO);
        return ResponseEntity.ok(updatedImage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }
}

