package com.pixsee.imageanalysis.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "image_metadata")
@Data
public class ImageMetadata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String originalFilename;

    @Column(nullable = false)
    private Long uploadDate;

    @Column(nullable = false)
    private Integer width;

    @Column(nullable = false)
    private Integer height;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String fileType;
}
