package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.AwsS3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
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
    private final AwsS3Service awsS3Service;

    @Operation(summary = "이미지 업로드", description = "upload Image<br> type : to use 'roadmaps' or 'users'")
    @Parameter(name = "type", schema = @Schema(example = "roadmaps"))
    @ApiResponse(content = @Content(schema = @Schema(hidden = true)))
    @PostMapping(value = "/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JSONObject upload(@RequestPart MultipartFile multipartFile, @PathVariable("type") String type, Authentication authentication) {
        log.debug("type: {}", type);

        if (multipartFile.isEmpty())
            throw new NullPointerException("multipartFile is empty");

        return awsS3Service.upload(multipartFile, type, ((User) authentication.getPrincipal()).getId());
    }

    @Operation(summary = "이미지 변경", description = "change image<br>" +
            "type - to use 'roadmaps' or 'users'<br>" +
            "id - imageId to swap with")
    @Parameters(value = {
        @Parameter(name = "type", schema = @Schema(example = "roadmaps")),
        @Parameter(name = "id", schema = @Schema(minimum = "1", example = "1"))
    })
    @ApiResponse(content = @Content(schema = @Schema(hidden = true)))
    @PutMapping(value = "/{type}/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public JSONObject swap(
            @RequestPart MultipartFile multipartFile,
            @PathVariable("type") String type, @PathVariable("id") Long imageId) {

        return awsS3Service.swap(imageId, type, multipartFile);
    }

}
