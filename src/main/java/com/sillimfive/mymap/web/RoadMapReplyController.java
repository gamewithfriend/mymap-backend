package com.sillimfive.mymap.web;

import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.roadmap.reply.RoadMapReplyResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Tag(name = "RoadMapReply", description = "reply API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/roadmaps")
public class RoadMapReplyController {

    //todo: 로드맵 댓글 관련 영역 구현 필

    @Operation(summary = "로드맵 댓글 작성", description = "todo: implementation")
    @PostMapping(path = "/{roadMapId}/replies", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> registerReply(@PathVariable("roadMapId") Long roadMapId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }

    @Operation(summary = "로드맵 댓글 수정", description = "todo: implementation")
    @PutMapping(path = "/{roadMapId}/replies/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> editReply(@PathVariable("roadMapId") Long roadMapId,
                                         @PathVariable("replyId") Long replyId,
                                         @RequestParam String content) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }

    @Operation(summary = "로드맵 댓글 삭제", description = "todo: implementation")
    @DeleteMapping(path = "/{roadMapId}/replies/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> deleteReply(@PathVariable("roadMapId") Long roadMapId, @PathVariable("replyId") Long replyId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }

    @Operation(summary = "로드맵 댓글 목록 조회", description = "todo: implementation",
            parameters = {
                    @Parameter(name = "page", example = "0"),
                    @Parameter(name = "size", example = "10"),
                    @Parameter(name = "sort", example = "id,desc")
            }
    )
    @GetMapping(path = "/{roadMapId}/replies", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<PageImpl<RoadMapReplyResponseDto>> replies(@PathVariable("roadMapId") Long roadMapId, Pageable pageable) {
        List<RoadMapReplyResponseDto> list = new ArrayList<>();

        // temp
        return MyMapResponse.create()
                .succeed()
                .buildWith(new PageImpl<>(list));
    }

    @Operation(summary = "댓글을 통한 기여 요청", description = "todo: implementation")
    @PutMapping(path = "/{roadMapId}/replies/{replyId}/contribution", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> contribute(@PathVariable("roadMapId") Long roadMapId, @PathVariable("replyId") Long replyId) {

        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }

    @Operation(summary = "원작자가 댓글로 기여자 선정", description = "todo: implementation")
    @PutMapping(path = "/{roadMapId}/replies/{replyId}/contribution/{action}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Parameter(name = "action", description = "action 값은 [approval] or [denial]")
    public MyMapResponse<Long> actionForContribution(
            @PathVariable("roadMapId") Long roadMapId,
            @PathVariable("replyId") Long replyId,
            @PathVariable("action") String action) {


        return MyMapResponse.create()
                .succeed()
                .buildWith(1L);
    }
}
