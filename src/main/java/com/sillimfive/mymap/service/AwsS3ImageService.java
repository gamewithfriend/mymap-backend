package com.sillimfive.mymap.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.ImageRepository;
import com.sillimfive.mymap.web.dto.ImageResponseDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class AwsS3ImageService {

    private final AwsProperties awsProperties;
    private final ImageRepository imageRepository;

    private AmazonS3 s3Client;
    private Regions region = Regions.AP_NORTHEAST_2;

    private final String AWS_FILE_SEPARATOR = "/";

    @PostConstruct
    protected void initS3Client() {
        log.info("accessKey: {}, secretKey: {}", awsProperties.getAccessKey(), awsProperties.getSecretKey());
        AWSCredentials credentials = new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());

        s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region).build();
    }

    public ImageResponseDto upload(MultipartFile multipartFile, String type, Long userId) {
        validateImageType(type);

        String urlPath = uploadToS3(multipartFile, type, userId);
        Image image = imageRepository.save(new Image(urlPath, ImageType.valueOf(type.toUpperCase())));

        return new ImageResponseDto(image);
    }

    private void validateImageType(String type) {
        if (!type.equals(ImageType.ROADMAPS.toString().toLowerCase())
            && !type.equals(ImageType.USERS.toString().toLowerCase()))
            throw new IllegalArgumentException("이미지 업로드 시 타입은 'roadmaps' 또는 'users' 만 사용 가능합니다.");
    }

    private String uploadToS3(MultipartFile multipartFile, String type, Long userId) {
        try {
            String key = type + AWS_FILE_SEPARATOR + getFileName(userId);

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(multipartFile.getSize());
            metadata.setContentType(multipartFile.getContentType());

            s3Client.putObject(awsProperties.getBucketName(), key, multipartFile.getInputStream(), metadata);
            return awsProperties.getUrl() + AWS_FILE_SEPARATOR + key;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(Long userId) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return new StringBuffer()
                .append(LocalDateTime.now().format(pattern))
                .append(userId).toString();
    }

    public void delete(Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("image for " + imageId + " can't be found"));

        deleteFromS3(image);
        imageRepository.deleteById(image.getId());
    }

    private void deleteFromS3(Image image) {
        String key = getKeyFromUrlPath(image);
        s3Client.deleteObject(awsProperties.getBucketName(), key);
    }

    private String getKeyFromUrlPath(Image image) {
        String key = image.getPath().replace(awsProperties.getUrl() + AWS_FILE_SEPARATOR, "");

        return key;
    }

    public ImageResponseDto swap(Long imageId, String type, MultipartFile multipartFile) {
        validateImageType(type);

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("image for " + imageId + " can't be found"));

        if (!image.getImageType().equals(ImageType.valueOf(type.toUpperCase())))
            throw new IllegalStateException("변경을 요청한 이미지의 유형과 검색된 이미지의 유형이 다릅니다.");

        deleteFromS3(image);

        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        String changedUrlPath = uploadToS3(multipartFile, type, user.getId());
        image.changePath(changedUrlPath);

        return new ImageResponseDto(image);
    }

    public Image copyOf(Image origin, Long userId) {
        String key = getKeyFromUrlPath(origin);
        String destinationKey = key.split(AWS_FILE_SEPARATOR)[0] + getFileName(userId);

        CopyObjectRequest copyObjRequest = new CopyObjectRequest(awsProperties.getBucketName(), key, awsProperties.getBucketName(), destinationKey);
        s3Client.copyObject(copyObjRequest);

        return imageRepository.save(new Image(awsProperties.getUrl() + AWS_FILE_SEPARATOR + destinationKey, ImageType.ROADMAPS));
    }
}
