package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.repository.ImageRepository;
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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public Long save(MultipartFile multipartFile, Long userId, ImageType type) throws IOException {

        // todo: code 테이블을 통해 DB에서 저장경로를 가져오는 기능 구현예정.
        String dirPath = "D:\\path\\" + File.separator + type.toString();
        if (!isValidPath(dirPath)) new File(dirPath).mkdirs();

        String fullName = dirPath + File.separator + getFileName(userId);
        multipartFile.transferTo(new File(fullName));

        fullName = URLEncoder.encode(fullName, StandardCharsets.UTF_8);

        return imageRepository.save(new Image(fullName, type.toString())).getId();
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
