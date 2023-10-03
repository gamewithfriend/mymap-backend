package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
}
