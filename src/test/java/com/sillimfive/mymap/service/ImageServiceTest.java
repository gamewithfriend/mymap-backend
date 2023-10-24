package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.repository.ImageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles(value = "oauth")
class ImageServiceTest {

    @Autowired
    ImageService imageService;
    @Autowired
    ImageRepository imageRepository;

    @Test
    @DisplayName("이미지_파일_네이밍_확인_테스트")
    void naming() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String datetime = LocalDateTime.now().format(pattern);
        System.out.println("formattedDatetime = " + datetime);

        String os = System.getProperty("os.name");
        String dirPath;
        if (os.equals("Linux")) dirPath = "/home/mymap";
        else dirPath = "D:\\path\\";

        StringBuffer sb = new StringBuffer(datetime);
        sb.insert(0, dirPath + File.separator + "{imageType}" + File.separator);
        System.out.println("imageFileName = " + sb);
    }

    @Test
    @DisplayName("이미지_저장")
    @Rollback(value = false)
    void upload() throws IOException {
        //given
        MultipartFile mockMultipartFile = new MockMultipartFile("test", "imageFile content".getBytes());
        Long userId = 1L;
        ImageType imageType = ImageType.ROADMAPS;

        //when
        Long imageId = imageService.save(mockMultipartFile, userId, imageType);
        Image findOne = imageRepository.findById(imageId).get();
        File image = new File(URLDecoder.decode(findOne.getPath(), StandardCharsets.UTF_8));

        //then
        assertThat(findOne.getImageType()).isEqualTo(imageType.toString());
        System.out.println("imagePath = " + image.getAbsolutePath());
        assertTrue(image.exists());
    }
}