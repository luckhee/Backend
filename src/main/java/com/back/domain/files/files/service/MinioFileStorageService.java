package com.back.domain.files.files.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@Profile("dev") // 개발 환경에서만 활성화
@RequiredArgsConstructor
public class MinioFileStorageService implements FileStorageService {

    private final S3Client minioClient;

    @Value("${cloud.storage.bucket-name}")
    private String bucketName;

    @Value("${cloud.storage.endpoint}")
    private String endpoint;

    @Value("${spring.servlet.multipart.max-file-size:10MB}")
    private String maxFileSizeStr;

    @Override
    public String storeFile(MultipartFile file, String subFolder) {

        // 파일 타입 검증
        String contentType = file.getContentType();
        if (contentType == null || !isAllowedFileType(contentType)) {
            throw new RuntimeException("허용되지 않는 파일 형식입니다.");
        }

        // 파일명 생성
        String originalFileName = file.getOriginalFilename();
        String fileExtension = getExtension(originalFileName);
        String fileNameInStorage = subFolder + "/" + UUID.randomUUID().toString() + fileExtension;

        try {
            // MinIO에 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileNameInStorage)
                    .contentType(contentType)
                    .build();

            minioClient.putObject(
                    putObjectRequest,
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize())
            );

            // URL 반환 (MinIO 접근 URL)
            String fileUrl = String.format("%s/%s/%s", endpoint, bucketName, fileNameInStorage);
            log.info("파일 업로드 성공: {}", fileUrl);
            return fileUrl;

        } catch (IOException e) {
            log.error("MinIO 파일 저장 중 오류 발생", e);
            throw new RuntimeException("파일 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void deletePhysicalFile(String fileUrl) {

    }

    @Override
    public Resource loadFileAsResource(String fileUrl) {
        return null;
    }

    private boolean isAllowedFileType(String contentType) {
        // CloudFileStorageService와 동일한 로직 사용
        return contentType.startsWith("image/") ||
                contentType.startsWith("video/") ||
                contentType.equals("application/pdf");
    }

    private String getExtension(String fileName) {
        if (fileName == null) return "";
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot) : "";
    }
}
