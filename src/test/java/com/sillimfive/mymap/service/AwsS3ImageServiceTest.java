//package com.sillimfive.mymap.service;
//
//import com.sillimfive.mymap.domain.Image;
//import com.sillimfive.mymap.domain.ImageType;
//import com.sillimfive.mymap.repository.ImageRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.util.Comparator;
//
//@SpringBootTest
//@ActiveProfiles(value = {"cloud", "maria"})
//public class AwsS3ImageServiceTest {
//
//    @Autowired
//    AwsS3ImageService awsS3Service;
//
//    @Autowired
//    ImageRepository imageRepository;
//
//    @Test
//    void uploadToS3() throws IOException {
//        String os = System.getProperty("os.name");
//        String dirPath;
//        if (os.equals("Linux")) dirPath = "/home/mymap";
//        else dirPath = "D:\\path";
//
//        String name = dirPath + File.separator + "upload-test-s3.txt";
//
//        File file = new File(name);
//
//        FileWriter writer = new FileWriter(file);
//        writer.write("apple");
//        writer.close();
//
////        awsS3Service.upload(file, ImageType.ROADMAP.toString(), LocalDateTime.now().toString());
//    }
//
//
//    @Test
//    void deleteFromS3() {
//        Long imageId = imageRepository.findAll().stream()
//                .sorted(Comparator.comparing(Image::getId).reversed())
//                .toList().get(0).getId();
//
//        awsS3Service.delete(imageId);
//    }
//
//    @Test
//    void temp() {
//        String map = "roadmap";
//        ImageType imageType = ImageType.valueOf(map.toUpperCase());
//        System.out.println("imageType = " + imageType);
//    }
//}