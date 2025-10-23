package com.back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;

@Configuration
@Profile("dev") // 개발 환경에서만 활성화
public class MinioConfig {

    @Value("${cloud.storage.endpoint}")
    private String endpoint;

    @Value("${cloud.storage.access-key}")
    private String accessKey;

    @Value("${cloud.storage.secret-key}")
    private String secretKey;

    @Bean
    public S3Client minioClient() {
        return S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(accessKey, secretKey)
                        )
                )
                .region(Region.US_EAST_1) // MinIO는 아무 리전이나 가능
                .forcePathStyle(true) // MinIO에서 필수!
                .build();
    }
}
