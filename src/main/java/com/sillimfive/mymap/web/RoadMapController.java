package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.AwsS3ImageService;
import com.sillimfive.mymap.service.CategoryService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.CategoryDto;
import com.sillimfive.mymap.web.dto.CategorySearch;
import com.sillimfive.mymap.web.dto.MyMapResponse;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "RoadMap", description = "road map API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/roadmaps")
public class RoadMapController {

    private final AwsS3ImageService awsS3ImageService;
    private final RoadMapService roadMapService;

    private final CategoryService categoryService;

    @Operation(summary = "로드맵 생성", description = "Create a roadmap (desc)")
    @ApiResponses(value =
        @ApiResponse(
            responseCode = "200", description = "Created (생성된 로드맵 아이디 반환)", content =
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema =
            @Schema(ref = "#/components/schemas/id-schema")))
    )
//    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public MyMapResponse<Long> create(@RequestBody RoadMapCreateDto roadMapCreateDto, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        try {
            log.debug("roadMapCreateDto = {}", roadMapCreateDto);

            return MyMapResponse.create()
                    .succeed()
                    .buildWith(roadMapService.create(user.getId(), roadMapCreateDto));

        } catch (Exception e) {
            awsS3ImageService.delete(roadMapCreateDto.getImageId());

            e.printStackTrace();
            throw new IllegalStateException("Failed to create RoadMap. so delete image for " + roadMapCreateDto.getImageId());
        }
    }

    @Operation(summary = "로드맵 상세 조회", description = "Get roadmap details (desc)")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapDetailResponseDto> findById(@PathVariable("id") Long roadMapId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.findById(roadMapId));
    }

    @Operation(summary = "로드맵 포크", description = "todo: implementation")
    @GetMapping(path = "/{id}/fork", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> fork() {

        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }

    @Operation(summary = "로드맵 수정", description = "Edit the roadmap (desc)")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> edit(
            @PathVariable("id") Long roadMapId,
            @RequestBody RoadMapEditDto roadMapEditDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.edit(roadMapId, roadMapEditDto));
    }

    @Parameter(name = "pageable", hidden = true)
    @Operation(summary = "로드맵 목록 조회", description = "Get roadmap list (desc)",
        parameters = {
            @Parameter(name = "page", example = "0"),
            @Parameter(name = "size", example = "10"),
            @Parameter(name = "sort", example = "id,desc")
        }
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<PageImpl<RoadMapResponseDto>> findAll(RoadMapSearch roadMapSearch, Pageable pageable) {
        log.debug("searchCondition: {}", roadMapSearch.toString());

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.findListBy(roadMapSearch, pageable));
    }

    @Operation(summary = "로드맵 카테고리 목록 조회", description = "카테고리 목록 조회시 <br>{\"rootFlag\": true} 또는 {\"rootFlag\": false, \"parentId\": 1}")
    @GetMapping(path = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<List<CategoryDto>> categories(CategorySearch categorySearch) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(categoryService.findBy(categorySearch));
    }
}
