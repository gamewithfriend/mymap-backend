package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.RoadMapBookmarkService;
import com.sillimfive.mymap.service.RoadMapLikeService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.service.RoadMapStudyService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.alarm.AlarmDeleteDto;
import com.sillimfive.mymap.web.dto.roadmap.*;
import com.sillimfive.mymap.web.dto.study.MemoDto;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyDetailDto;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyStatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
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
    private final RoadMapStudyService roadMapStudyService;
    private final RoadMapLikeService roadMapLikeService;
    private final RoadMapBookmarkService roadMapBookmarkService;

    @Operation(summary = "로드맵 학습하기", description = "Start to study the roadmap (desc)")
    @PostMapping(path = "/study/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> create(@PathVariable("id") Long roadMapId, @RequestBody RoadMapCopyDto roadMapCopyDto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapStudyService.startStudy(currentUser, roadMapId, roadMapCopyDto));
    }

    @Operation(summary = "학습 중인 로드맵 상세 조회", description = "Get the roadmap details on study (desc)")
    @GetMapping(path = "/study/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapStudyDetailDto> findById(@PathVariable("id") Long roadMapStudyId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(new RoadMapStudyDetailDto());
    }

    /*
     * todo: check - 메모를 등록할 때, 노드에 따라 연쇄적으로 등록하는 것인지 아니면 노드당 하나만 메모를 등록하도록 설정할지.
     */
    @Operation(summary = "메모 등록하기", description = "메모 정보 등록")
    @PostMapping(path = "/study/{id}/{nodeId}/memo", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> memoRegister(@PathVariable("id") Long roadMapId, @PathVariable("nodeId") Long roadMapStudyNodeId, MemoDto memoDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }

    @Operation(summary = "메모 변경하기", description = "메모 정보 변경")
    @PutMapping(path = "/study/{id}/{nodeId}/memo", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> memoUpdate(@PathVariable("id") Long roadMapId, @PathVariable("nodeId") Long roadMapStudyNodeId, MemoDto memoDto) {

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
    @Parameter(name = "pageable", hidden = true)
    @Operation(summary = "유저 로드맵 북마크 조회", description = "get User BookmarkList",
            parameters = {
                    @Parameter(name = "page", example = "0"),
                    @Parameter(name = "size", example = "10")
            }
    )
    @GetMapping(path = "/bookmark")
    public MyMapResponse<PageImpl<RoadMapBookmarkResponseDto>> bookmark(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        PageImpl<RoadMapBookmarkResponseDto> userRoadMapBookmarkList = roadMapBookmarkService.findByUserIdRoadMapBookmarkResponseDtoList(userId,pageable);

        return MyMapResponse.create()
                .succeed()
                .buildWith(userRoadMapBookmarkList);
    }
    @Operation(summary = "유저 북마크 다중 삭제", description = "User delete BookmarkList ")
    @DeleteMapping(path = "/bookmark", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<List<Long>> deleteBookmarkList(@RequestBody RoadMapBookmarkDeleteDto roadMapBookmarkDeleteDto) {
        List<@Min(value = 1) Long> roadMapBookmarkList = roadMapBookmarkDeleteDto.getRoadMapBookmarkList();
        roadMapBookmarkService.deleteBookmarkList(roadMapBookmarkList);
        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapBookmarkList);
    }


    @Operation(summary = "로드맵 북마크 처리", description = "이미 북마크 등록했을경우 삭제 아니면 등록")
    @PutMapping(path = "/bookmark/{roadMapId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapBookmarkResponseDto> bookmark(@PathVariable("roadMapId") Long roadMapId, Authentication authentication) {

        User likeUser = (User) authentication.getPrincipal();
        Long roadMapBookmarkId = roadMapBookmarkService.createOrDelete(likeUser, roadMapId);
        RoadMapBookmarkResponseDto roadMapBookmarkResponseDto = new RoadMapBookmarkResponseDto(roadMapId,roadMapBookmarkId);
        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapBookmarkResponseDto);
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
