package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.service.ImageService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapResponseDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapUpdateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long create(@RequestBody RoadMapCreateDto roadMapCreateDto, MultipartFile multipartFile, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        Long imageId = imageService.save(multipartFile, user.getId(), ImageType.ROADMAP);

        return roadMapService.create(user.getId(), imageId, roadMapCreateDto);
    }

    @Operation(summary = "로드맵 목록 조회", description = "Get roadmap list (desc)")
    @GetMapping
    public List<RoadMapResponseDto> findAll() {
        return null;
    }

    @Operation(summary = "로드맵 상세 조회", description = "Get roadmap details (desc)")
    @GetMapping("/{id}")
    public RoadMapResponseDto findById(@PathVariable("id") Long roadMapId) {
        log.info("findById");
        return roadMapService.findById(roadMapId);
    }

    @Operation(summary = "로드맵 수정", description = "Edit the roadmap (desc)")
    @PutMapping("/{id}")
    public Boolean edit(@PathVariable("id") Long roadMapId, MultipartFile multipartFile, @RequestBody RoadMapUpdateDto roadMapUpdateDto, Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();

        if (roadMapUpdateDto.isImageChanged())
            imageService.swapImage(roadMapUpdateDto.getImageId(), user.getId(), multipartFile);

        return null;
    }
}
