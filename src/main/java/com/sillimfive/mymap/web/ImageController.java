package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.AwsS3ImageService;
import com.sillimfive.mymap.web.dto.ImageResponseDto;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Images", description = "manage image files")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
@Slf4j
public class ImageController {
    private final AwsS3ImageService awsS3ImageService;

    @Operation(summary = "이미지 업로드", description = "upload Image<br> type : to use 'roadmaps' or 'users'")
    @Parameter(name = "type", schema = @Schema(example = "roadmaps"))
    @PostMapping(value = "/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MyMapResponse<ImageResponseDto> upload(@RequestPart MultipartFile multipartFile, @PathVariable("type") String type, Authentication authentication) {
        log.debug("type: {}", type);

        if (multipartFile.isEmpty())
            throw new NullPointerException("multipartFile is empty");

        return MyMapResponse.create()
                .succeed()
                .buildWith(awsS3ImageService.upload(multipartFile, type, ((User) authentication.getPrincipal()).getId()));
    }

    @Operation(summary = "이미지 변경", description = "change image<br>" +
            "type - to use 'roadmaps' or 'users'<br>" +
            "id - imageId to swap with")
    @Parameters(value = {
        @Parameter(name = "type", schema = @Schema(example = "roadmaps")),
        @Parameter(name = "id", schema = @Schema(minimum = "1", example = "1"))
    })
    @PutMapping(value = "/{type}/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public MyMapResponse<ImageResponseDto> swap(
            @RequestPart MultipartFile multipartFile,
            @PathVariable("type") String type, @PathVariable("id") Long imageId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(awsS3ImageService.swap(imageId, type, multipartFile));
    }

}
