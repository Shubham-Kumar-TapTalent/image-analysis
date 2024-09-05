# Image Analysis Platform Lite

## Overview
This is a simple image management platform that allows users to upload image metadata, retrieve it, update it, and delete it. This implementation is built with Java and Spring Boot.

## Setup Instructions

1. Clone the repository:
   ```sh
   git clone <repository-url>
   cd <repository-directory>

2. Create DB and table
    ``` sql
    CREATE DATABASE IF NOT EXISTS image_analysis_db;
    
    CREATE TABLE IF NOT EXISTS image_metadata (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT NOT NULL,
        original_filename VARCHAR(255) NOT NULL,
        upload_date BIGINT NOT NULL,
        width INT NOT NULL,
        height INT NOT NULL,
        file_size BIGINT NOT NULL,
        file_type VARCHAR(10) NOT NULL,
    
        INDEX user_id_idx (user_id)
    );