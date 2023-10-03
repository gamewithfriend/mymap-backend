package com.sillimfive.mymap.web;

import com.sillimfive.mymap.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
    private final ImageService imageService;

    // @PathVariable 사용시 400 error 발생
    @GetMapping
    public ResponseEntity<Resource> send(@RequestParam(name = "path") String path) throws MalformedURLException {

        return ResponseEntity.ok()
                // todo: image domain -> 확장자 저장 필요.
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"image.jpg\"")
                .body(imageService.load(path));
    }
}
