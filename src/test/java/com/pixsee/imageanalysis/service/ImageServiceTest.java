package com.pixsee.imageanalysis.service;

import com.pixsee.imageanalysis.dto.ImageMetadataRequestDTO;
import com.pixsee.imageanalysis.dto.ImageMetadataResponseDTO;
import com.pixsee.imageanalysis.entity.ImageMetadata;
import com.pixsee.imageanalysis.exception.ResourceNotFoundException;
import com.pixsee.imageanalysis.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @InjectMocks
    private ImageService imageService;

    @Mock
    private ImageRepository imageRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Helper method to create an ImageMetadata entity
    private ImageMetadata createImageMetadata(Long id) {
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setId(id);
        imageMetadata.setUserId(1L);
        imageMetadata.setOriginalFilename("image.jpg");
        imageMetadata.setWidth(800);
        imageMetadata.setHeight(600);
        imageMetadata.setFileSize(12345L);
        imageMetadata.setFileType("jpg");
        return imageMetadata;
    }

    // Helper method to create an ImageMetadataRequestDTO
    private ImageMetadataRequestDTO createImageMetadataRequestDTO() {
        ImageMetadataRequestDTO requestDTO = new ImageMetadataRequestDTO();
        requestDTO.setUserId(1L);
        requestDTO.setOriginalFilename("image.jpg");
        requestDTO.setWidth(800);
        requestDTO.setHeight(600);
        requestDTO.setFileSize(12345L);
        requestDTO.setFileType("jpg");
        return requestDTO;
    }

    // Happy Path - Saving Image Metadata
    @Test
    void testSaveImageMetadata_Success() {
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        ImageMetadata savedEntity = createImageMetadata(1L);

        // Mock repository behavior
        when(imageRepository.save(any(ImageMetadata.class))).thenReturn(savedEntity);

        // Perform the service call
        ImageMetadataResponseDTO responseDTO = imageService.saveImageMetadata(requestDTO);

        // Verify the results
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("image.jpg", responseDTO.getOriginalFilename());

        // Verify interactions with the repository
        verify(imageRepository, times(1)).save(any(ImageMetadata.class));
    }

    // Negative Path - Saving Image Metadata with null User ID
    @Test
    void testSaveImageMetadata_NullUserId() {
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        requestDTO.setUserId(null);

        // When calling save, ensure validation error or exception occurs
        assertThrows(NullPointerException.class, () -> imageService.saveImageMetadata(requestDTO));
    }

    // Happy Path - Retrieving Images by User ID
    @Test
    void testGetImagesByUserId_Success() {
        List<ImageMetadata> images = Arrays.asList(createImageMetadata(1L), createImageMetadata(2L));

        // Mock repository behavior
        when(imageRepository.findByUserId(1L)).thenReturn(images);

        // Perform the service call
        List<ImageMetadataResponseDTO> responseDTOs = imageService.getImagesByUserId(1L);

        // Verify the results
        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
        assertEquals("image.jpg", responseDTOs.get(0).getOriginalFilename());

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findByUserId(1L);
    }

    // Negative Path - Retrieving Images with No Data
    @Test
    void testGetImagesByUserId_NoImagesFound() {
        // Mock repository behavior to return an empty list
        when(imageRepository.findByUserId(1L)).thenReturn(Arrays.asList());

        // Perform the service call
        List<ImageMetadataResponseDTO> responseDTOs = imageService.getImagesByUserId(1L);

        // Verify the results
        assertTrue(responseDTOs.isEmpty());

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findByUserId(1L);
    }

    // Happy Path - Get Image by ID
    @Test
    void testGetImageById_Success() {
        ImageMetadata imageMetadata = createImageMetadata(1L);

        // Mock repository behavior
        when(imageRepository.findById(1L)).thenReturn(Optional.of(imageMetadata));

        // Perform the service call
        ImageMetadataResponseDTO responseDTO = imageService.getImageById(1L);

        // Verify the results
        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findById(1L);
    }

    // Negative Path - Get Image by ID Not Found
    @Test
    void testGetImageById_NotFound() {
        // Mock repository behavior to return an empty Optional
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform the service call and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> imageService.getImageById(1L));

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findById(1L);
    }

    // Happy Path - Update Image Metadata
    @Test
    void testUpdateImageMetadata_Success() {
        ImageMetadata existingImage = createImageMetadata(1L);
        ImageMetadataRequestDTO updateRequestDTO = createImageMetadataRequestDTO();

        // Mock repository behavior
        when(imageRepository.findById(1L)).thenReturn(Optional.of(existingImage));
        when(imageRepository.save(any(ImageMetadata.class))).thenReturn(existingImage);

        // Perform the service call
        ImageMetadataResponseDTO updatedResponse = imageService.updateImageMetadata(1L, updateRequestDTO);

        // Verify the results
        assertNotNull(updatedResponse);
        assertEquals(1L, updatedResponse.getId());
        assertEquals("image.jpg", updatedResponse.getOriginalFilename());

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findById(1L);
        verify(imageRepository, times(1)).save(any(ImageMetadata.class));
    }

    // Negative Path - Update Image Metadata Not Found
    @Test
    void testUpdateImageMetadata_NotFound() {
        ImageMetadataRequestDTO updateRequestDTO = createImageMetadataRequestDTO();

        // Mock repository behavior to return an empty Optional
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform the service call and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> imageService.updateImageMetadata(1L, updateRequestDTO));

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findById(1L);
    }

    // Happy Path - Delete Image
    @Test
    void testDeleteImage_Success() {
        ImageMetadata existingImage = createImageMetadata(1L);

        // Mock repository behavior
        doNothing().when(imageRepository).deleteById(1L);

        // Perform the service call
        imageService.deleteImage(1L);

        // Verify interactions with the repository
        verify(imageRepository, times(1)).deleteById(1L);
    }

    // Negative Path - Delete Image Not Found
    @Test
    void testDeleteImage_NotFound() {
        // Mock repository behavior to return an empty Optional
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        // Perform the service call and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> imageService.deleteImage(1L));

        // Verify interactions with the repository
        verify(imageRepository, times(1)).findById(1L);
        verify(imageRepository, never()).deleteById(1L);
    }
}
