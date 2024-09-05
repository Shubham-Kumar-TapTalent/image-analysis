package com.pixsee.imageanalysis.mapper;

import com.pixsee.imageanalysis.dto.ImageMetadataRequestDTO;
import com.pixsee.imageanalysis.dto.ImageMetadataResponseDTO;
import com.pixsee.imageanalysis.entity.ImageMetadata;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ImageMetadataMapperTest {

    // Helper method to create an ImageMetadata entity
    private ImageMetadata createImageMetadata(Long id) {
        ImageMetadata imageMetadata = new ImageMetadata();
        imageMetadata.setId(id);
        imageMetadata.setUserId(1L);
        imageMetadata.setOriginalFilename("image.jpg");
        imageMetadata.setUploadDate(Instant.now().getEpochSecond());
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

    // Happy Path - Entity to Response DTO Mapping
    @Test
    void testToResponseDTO_Success() {
        ImageMetadata entity = createImageMetadata(1L);

        // Perform the mapping
        ImageMetadataResponseDTO responseDTO = ImageMetadataMapper.toResponseDTO(entity);

        // Verify the results
        assertNotNull(responseDTO);
        assertEquals(entity.getId(), responseDTO.getId());
        assertEquals(entity.getUserId(), responseDTO.getUserId());
        assertEquals(entity.getOriginalFilename(), responseDTO.getOriginalFilename());
        assertEquals(entity.getUploadDate(), responseDTO.getUploadDate());
        assertEquals(entity.getWidth(), responseDTO.getWidth());
        assertEquals(entity.getHeight(), responseDTO.getHeight());
        assertEquals(entity.getFileSize(), responseDTO.getFileSize());
        assertEquals(entity.getFileType(), responseDTO.getFileType());
    }

    // Edge Case - Entity to Response DTO Mapping when some fields are null
    @Test
    void testToResponseDTO_NullFields() {
        ImageMetadata entity = createImageMetadata(1L);
        entity.setWidth(null);
        entity.setHeight(null);

        // Perform the mapping
        ImageMetadataResponseDTO responseDTO = ImageMetadataMapper.toResponseDTO(entity);

        // Verify the results
        assertNotNull(responseDTO);
        assertEquals(entity.getId(), responseDTO.getId());
        assertNull(responseDTO.getWidth());
        assertNull(responseDTO.getHeight());
    }

    // Happy Path - Request DTO to Entity Mapping
    @Test
    void testToEntity_Success() {
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();

        // Perform the mapping
        ImageMetadata entity = ImageMetadataMapper.toEntity(requestDTO);

        // Verify the results
        assertNotNull(entity);
        assertEquals(requestDTO.getUserId(), entity.getUserId());
        assertEquals(requestDTO.getOriginalFilename(), entity.getOriginalFilename());
        assertEquals(requestDTO.getWidth(), entity.getWidth());
        assertEquals(requestDTO.getHeight(), entity.getHeight());
        assertEquals(requestDTO.getFileSize(), entity.getFileSize());
        assertEquals(requestDTO.getFileType(), entity.getFileType());
        assertNotNull(entity.getUploadDate());  // Ensure upload date is set
    }

    // Edge Case - Request DTO to Entity when fields are null
    @Test
    void testToEntity_NullFields() {
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        requestDTO.setFileSize(null);
        requestDTO.setFileType(null);

        // Perform the mapping
        ImageMetadata entity = ImageMetadataMapper.toEntity(requestDTO);

        // Verify the results
        assertNotNull(entity);
        assertNull(entity.getFileSize());
        assertNull(entity.getFileType());
    }

    // Happy Path - Updating Existing Entity from Request DTO
    @Test
    void testUpdateEntityFromDTO_Success() {
        ImageMetadata existingEntity = createImageMetadata(1L);
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        requestDTO.setOriginalFilename("new_image.png");
        requestDTO.setFileType("png");

        // Perform the update
        ImageMetadataMapper.updateEntityFromDTO(requestDTO, existingEntity);

        // Verify the entity is updated correctly
        assertEquals("new_image.png", existingEntity.getOriginalFilename());
        assertEquals("png", existingEntity.getFileType());
    }

    // Edge Case - Updating Existing Entity when DTO has null fields
    @Test
    void testUpdateEntityFromDTO_NullFields() {
        ImageMetadata existingEntity = createImageMetadata(1L);
        ImageMetadataRequestDTO requestDTO = createImageMetadataRequestDTO();
        requestDTO.setFileSize(null);  // Set some fields to null

        // Perform the update
        ImageMetadataMapper.updateEntityFromDTO(requestDTO, existingEntity);

        // Verify the entity is updated with null values
        assertNull(existingEntity.getFileSize());
        assertEquals("image.jpg", existingEntity.getOriginalFilename());  // Other fields remain unchanged
    }
}
