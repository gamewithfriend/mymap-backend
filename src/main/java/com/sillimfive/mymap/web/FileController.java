package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Tag(name = "Images", description = "manage image files")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final ImageService imageService;

    // @PathVariable 사용시 400 error 발생
    @Operation(summary = "이미지 받기",
                description = "<b>download image file.</b><br>" +
                        "다운받고자하는 이미지의 저장경로(path)를 Querystring으로 전달한다.")
    @GetMapping
    public ResponseEntity<Resource> send(@RequestParam(name = "path") String path) throws MalformedURLException {

        return ResponseEntity.ok()
                // todo: image domain -> 확장자 저장 필요.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                .body(imageService.load(path));
    }
}
