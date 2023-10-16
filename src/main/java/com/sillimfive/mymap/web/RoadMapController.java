package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.service.ImageService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.CategoryDto;
import com.sillimfive.mymap.web.dto.roadmap.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.util.List;


@Tag(name = "RoadMap", description = "road map API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/roadmaps")
public class RoadMapController {

    private final ImageService imageService;
    private final RoadMapService roadMapService;

    @Operation(summary = "로드맵 생성", description = "Create a roadmap (desc)")
    @ApiResponses(value =
        @ApiResponse(
            responseCode = "201", description = "Created", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
            @Schema(example = "1", description = "생성된 로드맵 아이디 반환"))
        )
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@RequestBody RoadMapCreateDto roadMapCreateDto, MultipartFile multipartFile, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Long imageId = imageService.save(multipartFile, user.getId(), ImageType.ROADMAP);

        return roadMapService.create(user.getId(), imageId, roadMapCreateDto);
    }

    @Operation(summary = "로드맵 상세 조회", description = "Get roadmap details (desc)")
    @GetMapping("/{id}")
    public RoadMapDetailResponseDto findById(@PathVariable("id") Long roadMapId) {

        return roadMapService.findById(roadMapId);
    }

    @Operation(summary = "로드맵 수정", description = "Edit the roadmap (desc)")
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") Long roadMapId, MultipartFile multipartFile, @RequestBody RoadMapUpdateDto roadMapUpdateDto, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        if (roadMapUpdateDto.isImageChanged())
            imageService.swapImage(roadMapUpdateDto.getImageId(), user.getId(), multipartFile);

        return ResponseEntity.ok(roadMapService.edit(roadMapId, roadMapUpdateDto));
    }

    @Operation(summary = "로드맵 목록 조회", description = "Get roadmap list (desc)")
    @Parameter(name = "pageable", hidden = true)
    @GetMapping
    public List<RoadMapResponseDto> findAll(@RequestParam RoadMapSearch roadMapSearch, Pageable pageable) {
        return null;
    }

    @Operation(summary = "로드맵 카테고리 조회")
    @GetMapping("/categories")
    public List<CategoryDto> categories() {
        return null;
    }



}
