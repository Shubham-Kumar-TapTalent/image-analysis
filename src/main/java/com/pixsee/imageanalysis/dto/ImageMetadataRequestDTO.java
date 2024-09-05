package com.pixsee.imageanalysis.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ImageMetadataRequestDTO {

    @NotNull(message = "User Id cannot be null")
    private Long userId;

    @NotBlank(message = "Original File Name cannot be empty/null")
    @Size(min = 1, max = 255, message = "Original File Name must be between {min} and {max} characters")
    private String originalFilename;

    @NotNull(message = "Width cannot be null")
    private Integer width;

    @NotNull(message = "Height cannot be null")
    private Integer height;

    @NotNull(message = "File Size cannot be null")
    private Long fileSize;

    @NotBlank(message = "File Type cannot be empty/null")
    private String fileType;
}
