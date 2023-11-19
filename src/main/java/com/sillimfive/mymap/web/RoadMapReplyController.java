package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.AlarmType;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RoadMapReplyQuerydslRepository;
import com.sillimfive.mymap.repository.RoadMapReplyRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.service.AlarmService;
import com.sillimfive.mymap.service.RoadMapReplyService;
import com.sillimfive.mymap.service.RoadMapService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapDetailResponseDto;
import com.sillimfive.mymap.web.dto.roadmap.reply.RoadMapReplyCreateDto;
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
import org.springframework.security.core.Authentication;
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

    private final RoadMapReplyService roadMapReplyService;
    private final AlarmService alarmService;
    private final RoadMapService roadMapService;
    private final RoadMapRepository roadMapRepository;
    private final RoadMapReplyRepository roadMapReplyRepository;
    private final RoadMapReplyQuerydslRepository roadMapReplyQuerydslRepository;
    private final UserRepository userRepository;
    
    @Operation(summary = "로드맵 댓글 작성", description = "todo: implementation")
    @PostMapping(path = "/{roadMapId}/replies", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> registerReply(@PathVariable("roadMapId") Long roadMapId, Authentication authentication, RoadMapReplyCreateDto roadMapReplyCreateDto) {
        User user = (User) authentication.getPrincipal();
        Long roadMapReplyId = roadMapReplyService.create(user, roadMapId, roadMapReplyCreateDto);
        RoadMap roadMap = roadMapRepository.findById(roadMapId).get();

        Long alarmTargetUserId = roadMap.getCreator().getId();
        User alarmTargetUser = userRepository.getReferenceById(alarmTargetUserId);
        alarmService.create(AlarmType.alarm01,alarmTargetUser,roadMapReplyId,null);
        return MyMapResponse.create()
                .succeed()
                .buildWith(roadMapReplyId);
    }

    @Operation(summary = "로드맵 댓글 수정", description = "todo: implementation")
    @PutMapping(path = "/{roadMapId}/replies/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> editReply(@PathVariable("roadMapId") Long roadMapId,@PathVariable("replyId") Long replyId,
                                         @RequestParam String content) {
        replyId = roadMapReplyService.edit(replyId, content);

        return MyMapResponse.create()
                .succeed()
                .buildWith(replyId);
    }

    @Operation(summary = "로드맵 댓글 삭제", description = "todo: implementation")
    @DeleteMapping(path = "/{roadMapId}/replies/{replyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> deleteReply(@PathVariable("roadMapId") Long roadMapId,@PathVariable("replyId") Long replyId) {
        replyId = roadMapReplyService.delete(replyId);
        return MyMapResponse.create()
                .succeed()
                .buildWith(replyId);
    }

    @Operation(summary = "로드맵 댓글 목록 조회", description = "todo: implementation",
            parameters = {
                    @Parameter(name = "page", example = "0"),
                    @Parameter(name = "size", example = "10"),
                    @Parameter(name = "sort", example = "id,desc")
            }
    )
    @GetMapping(path = "/{roadMapId}/replies", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<PageImpl<RoadMapReplyResponseDto>> replies(@PathVariable("roadMapId") Long roadMapId, Pageable pageable,Authentication authentication) {
        List<RoadMapReplyResponseDto> list = new ArrayList<>();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        PageImpl<RoadMapReplyResponseDto> resultList = roadMapReplyQuerydslRepository.findByRoadMapIdReply(userId, roadMapId, pageable);
        // temp
        return MyMapResponse.create()
                .succeed()
                .buildWith(resultList);
    }

    @Operation(summary = "원작자가 댓글로 기여자 선정", description = "todo: implementation")
    @PutMapping(path = "/{roadMapId}/replies/{replyId}/contribution", produces = MediaType.APPLICATION_JSON_VALUE)
    public MyMapResponse<Long> actionForContribution(
            @PathVariable("roadMapId") Long roadMapId,
            @PathVariable("replyId") Long replyId){
        replyId = roadMapReplyService.changeReplyisContributeReply(replyId);

        return MyMapResponse.create()
                .succeed()
                .buildWith(replyId);
    }
}
