package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.repository.ImageRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Long save(MultipartFile multipartFile, Long userId, ImageType type) throws IOException {
        String fullName = getFullName(userId, type);
        multipartFile.transferTo(new File(fullName));

        return imageRepository.save(new Image(URLEncoder.encode(fullName, StandardCharsets.UTF_8), type.toString())).getId();
    }

    @Transactional
    public void remove(Long imageId) {
        imageRepository.deleteById(imageId);
    }

    /**
     * @param userId
     * @param type
     * @return full name with dir path
     */
    private String getFullName(Long userId, ImageType type){
        // todo: code 테이블을 통해 DB에서 저장경로를 가져오는 기능 구현예정.

        String os = System.getProperty("os.name");
        String dirPath;
        if (os.equals("Linux")) dirPath = "/home/mymap";
        else dirPath = "D:\\path\\";

        dirPath += (File.separator + type.toString());

        if (!isValidPath(dirPath)) new File(dirPath).mkdirs();
        String fullName = dirPath + File.separator + getFileName(userId);

        return fullName;
    }

    @Transactional
    public Boolean swapImage(Long imageId, Long userId, MultipartFile multipartFile) throws IOException {
        Image image = imageRepository.findById(imageId).orElseThrow(()
                -> new IllegalArgumentException("There is no image for " + imageId));

        File file = new File(image.getPath());
        Assert.isTrue(file.exists(), "Can't find image file in file system");
        file.delete();

        String fullName = getFullName(userId, ImageType.valueOf(image.getImageType()));
        multipartFile.transferTo(new File(fullName));
        image.changePath(fullName);

        return true;
    }

    private boolean isValidPath(String dirPath) {
        return new File(dirPath).exists();
    }

    private String getFileName(Long userId) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

        return new StringBuffer()
                .append(LocalDateTime.now().format(pattern))
                .append(userId).toString();
    }

    public Resource load(String path) throws MalformedURLException {
        path = URLDecoder.decode(path, StandardCharsets.UTF_8);

        log.debug("resource load path: {}", path);
        Resource resource = new UrlResource(new File(path).toURI());

        if (resource.exists() || resource.isReadable()) return resource;
        else
            throw new IllegalArgumentException("The file(" + path + ") doesn't exist or the path is not readable.");
    }
}
