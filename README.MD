# Image Analysis Platform Lite

### Overview
This is a simple image management platform that allows users to upload image metadata, retrieve it, update it, and delete it. This implementation is built with Java and Spring Boot.

## Common Steps

1. Extract the project folder

2. Install [Mysql 8](https://hackernoon.com/how-to-install-mysql-8-on-macos-using-homebrew). Run it on port 3306

3. Create Database and necessary table(s)
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

## After completing the above steps, the application can be run in 2 ways:

### Setup via CLI
   
1. Install [JDK 21](https://formulae.brew.sh/formula/openjdk@21)
2. Install [Maven](https://mkyong.com/maven/install-maven-on-mac-osx/#homebrew-install-maven-on-macos) and run `mvn clean install` in the base directory. This will create the executable JAR in the `target` directory.
3. Move to the `target` directory and run `java -jar image-analysis-0.0.1-SNAPSHOT.jar`. This will start the server on port 8080 and logs will appear in the terminal itself.


### Setup via IntelliJ IDEA

1. Install [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
2. **If not done already**, Install [JDK 21](https://www.jetbrains.com/guide/java/tips/download-jdk/)
2. Open the project in IntelliJ IDEA and wait for the dependencies to be installed.
3. Run the project from the class `ImageAnalysisApplication`. This will start the server on port 8080.

#### The service is ready to start serving the requests once the server starts on port 8080 