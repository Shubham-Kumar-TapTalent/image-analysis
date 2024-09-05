package com.pixsee.imageanalysis.dto;

import lombok.Data;

@Data
public class ImageMetadataResponseDTO {

    private Long id;
    private Long userId;
    private String originalFilename;
    private Long uploadDate;
    private Integer width;
    private Integer height;
    private Long fileSize;
    private String fileType;
}
