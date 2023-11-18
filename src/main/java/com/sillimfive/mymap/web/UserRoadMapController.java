package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.RoadMapLikeService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapLikeResponseDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapResponseDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapSearch;
import com.sillimfive.mymap.web.dto.study.MemoDto;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyDetailDto;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyStartDto;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyStatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "User to RoadMap", description = "API for Relations between User and RoadMap")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users/roadmaps")
public class UserRoadMapController {

    private final RoadMapService roadMapService;
    private final RoadMapLikeService roadMapLikeService;
    @Operation(summary = "로드맵 학습하기", description = "Start to study the roadmap (desc)")
    @PostMapping(path = "/study/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> create(@PathVariable("id") Long roadMapId, @RequestBody RoadMapStudyStartDto studyStartDto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.startStudy(currentUser.getId(), roadMapId, studyStartDto));
    }

    @Operation(summary = "학습 중인 로드맵 상세 조회", description = "Get the roadmap details on study (desc)")
    @GetMapping(path = "/study/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapStudyDetailDto> findById(@PathVariable("id") Long roadMapStudyId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(new RoadMapStudyDetailDto());
    }

    @Operation(summary = "메모 작성 및 변경", description = "메모 정보 갱신")
    @PutMapping(path = "/study/{id}/memo", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> memoUpdate(@PathVariable("id") Long roadMapId, MemoDto memoDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }

    @Operation(summary = "학습중인 로드맵 상태변경", description = "")
    @PutMapping(path = "/study/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> update(@PathVariable("id") Long roadMapId, RoadMapStudyStatusDto statusDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }


    // todo: querydsl todo 확인
    @Operation(summary = "학습 중인 로드맵 목록 조회", description = "Get the roadmap list on study (desc)")
    @GetMapping(path = "/study", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<PageImpl<RoadMapResponseDto>> findAll(Pageable pageable, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        RoadMapSearch roadMapSearch = RoadMapSearch.builder()
                .userId(user.getId())
                .studyFlag(true)
                .build();

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.findListBy(roadMapSearch, pageable));
    }

    // ###############################################################################

    @Operation(summary = "로드맵 북마크 상태 업데이트", description = "todo: implementation")
    @GetMapping(path = "/bookmarks")
    public MyMapResponse<PageImpl<Object>> bookmark() {
        List<Object> list = new ArrayList<>();
        PageImpl<Object> page = new PageImpl<>(list);

        return MyMapResponse.create()
                .succeed()
                .buildWith(page);
    }


    @Operation(summary = "로드맵 좋아요 처리", description = "이미 좋아요 눌렀을경우 삭제 아니면 등록")
    @PutMapping(path = "/like/{roadMapId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapLikeResponseDto> like(@PathVariable("roadMapId") Long id, Authentication authentication) {

        User likeUser = (User) authentication.getPrincipal();
        Long roadMapLikeId = roadMapLikeService.createOrDelete(likeUser, id);
        RoadMapLikeResponseDto roadMapLikeResponseDto = new RoadMapLikeResponseDto(roadMapLikeId);
        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapLikeResponseDto);
    }
}
