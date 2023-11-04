package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapDetailResponseDto;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "user API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final RoadMapService roadMapService;

    @Operation(summary = "로드맵 학습하기", description = "Start to study the roadmap (desc)")
    @PostMapping(path = "/roadmap-studies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> create(@PathVariable("id") Long roadMapId, @RequestBody RoadMapStudyStartDto studyStartDto, Authentication authentication) {
        User currentUser = (User) authentication.getPrincipal();

        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapService.startStudy(currentUser.getId(), roadMapId, studyStartDto));
    }

    @Operation(summary = "학습 중인 로드맵 상세 조회", description = "Get the roadmap details on study (desc)")
    @GetMapping(path = "/roadmap-studies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<RoadMapStudyDetailDto> findById(@PathVariable("id") Long roadMapStudyId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(new RoadMapStudyDetailDto());
    }

//    @Operation(summary = "학습 중인 로드맵 수정", description = "Edit the roadmap on study (desc)")
//    @PutMapping(path = "/roadmap-studies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> edit(@PathVariable("id") Long roadMapStudyId) {
//
//        return ResponseEntity.ok(new Object());
//    }

    @Operation(summary = "메모 작성 및 변경", description = "메모 정보 갱신")
    @PutMapping(path = "/roadmap-studies/{id}/memo", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> memoUpdate(@PathVariable("id") Long roadMapId, MemoDto memoDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }

    @Operation(summary = "학습중인 로드맵 상태변경", description = "")
    @PutMapping(path = "/roadmap-studies/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Boolean> update(@PathVariable("id") Long roadMapId, RoadMapStudyStatusDto statusDto) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(true);
    }


    // todo: querydsl todo 확인
    @Operation(summary = "학습 중인 로드맵 목록 조회", description = "Get the roadmap list on study (desc)")
    @GetMapping(path = "/roadmap-studies", produces = MediaType.APPLICATION_JSON_VALUE)
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
}