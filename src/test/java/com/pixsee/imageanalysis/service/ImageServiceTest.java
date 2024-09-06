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

        when(imageRepository.save(any(ImageMetadata.class))).thenReturn(savedEntity);

        ImageMetadataResponseDTO responseDTO = imageService.saveImageMetadata(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());
        assertEquals("image.jpg", responseDTO.getOriginalFilename());

        verify(imageRepository, times(1)).save(any(ImageMetadata.class));
    }

    // Negative Path - Saving Image Metadata with null User ID
    @Test
    void testSaveImageMetadata_NullUserId() {
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        requestDTO.setUserId(null);

        assertThrows(NullPointerException.class, () -> imageService.saveImageMetadata(requestDTO));
    }

    // Happy Path - Retrieving Images by User ID
    @Test
    void testGetImagesByUserId_Success() {
        List<ImageMetadata> images = Arrays.asList(createImageMetadata(1L), createImageMetadata(2L));

        when(imageRepository.findByUserId(1L)).thenReturn(images);

        List<ImageMetadataResponseDTO> responseDTOs = imageService.getImagesByUserId(1L);

        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
        assertEquals("image.jpg", responseDTOs.getFirst().getOriginalFilename());

        verify(imageRepository, times(1)).findByUserId(1L);
    }

    // Negative Path - Retrieving Images with No Data
    @Test
    void testGetImagesByUserId_NoImagesFound() {
        when(imageRepository.findByUserId(1L)).thenReturn(List.of());

        List<ImageMetadataResponseDTO> responseDTOs = imageService.getImagesByUserId(1L);

        assertTrue(responseDTOs.isEmpty());

        verify(imageRepository, times(1)).findByUserId(1L);
    }

    // Happy Path - Get Image by ID
    @Test
    void testGetImageById_Success() {
        ImageMetadata imageMetadata = createImageMetadata(1L);

        when(imageRepository.findById(1L)).thenReturn(Optional.of(imageMetadata));

        ImageMetadataResponseDTO responseDTO = imageService.getImageById(1L);

        assertNotNull(responseDTO);
        assertEquals(1L, responseDTO.getId());

        verify(imageRepository, times(1)).findById(1L);
    }

    // Negative Path - Get Image by ID Not Found
    @Test
    void testGetImageById_NotFound() {
        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.getImageById(1L));

        verify(imageRepository, times(1)).findById(1L);
    }

    // Happy Path - Update Image Metadata
    @Test
    void testUpdateImageMetadata_Success() {
        ImageMetadata existingImage = createImageMetadata(1L);
        ImageMetadataRequestDTO updateRequestDTO = createImageMetadataRequestDTO();

        when(imageRepository.findById(1L)).thenReturn(Optional.of(existingImage));
        when(imageRepository.save(any(ImageMetadata.class))).thenReturn(existingImage);

        ImageMetadataResponseDTO updatedResponse = imageService.updateImageMetadata(1L, updateRequestDTO);

        assertNotNull(updatedResponse);
        assertEquals(1L, updatedResponse.getId());
        assertEquals("image.jpg", updatedResponse.getOriginalFilename());

        verify(imageRepository, times(1)).findById(1L);
        verify(imageRepository, times(1)).save(any(ImageMetadata.class));
    }

    // Negative Path - Update Image Metadata Not Found
    @Test
    void testUpdateImageMetadata_NotFound() {
        ImageMetadataRequestDTO updateRequestDTO = createImageMetadataRequestDTO();

        when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> imageService.updateImageMetadata(1L, updateRequestDTO));

        verify(imageRepository, times(1)).findById(1L);
    }

    // Happy Path - Delete Image
    @Test
    void testDeleteImage_Success() {
        doNothing().when(imageRepository).deleteById(1L);

        imageService.deleteImage(1L);

        verify(imageRepository, times(1)).deleteById(1L);
    }

}
