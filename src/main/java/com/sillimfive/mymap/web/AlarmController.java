package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.service.AlarmService;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.alarm.AlarmDeleteDto;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Alarm", description = "Alarm API")
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;

    @Parameter(name = "pageable", hidden = true)
    @Operation(summary = "유저 알람 리스트 조회 (readFlag 파라미터로 읽은 안읽은 리스트 구분조회) 총 갯수 포함", description = "Get the User Alarm List and Count",
            parameters = {
                    @Parameter(name = "page", example = "0"),
                    @Parameter(name = "size", example = "10"),
                    @Parameter(name = "readFlag", example = "false: 안읽음, true: 읽음")
            }
    )
    @GetMapping
    public MyMapResponse<PageImpl<AlarmResponseDto>> findUserAlarmList(Authentication authentication, Pageable pageable, @RequestParam boolean readFlag) {
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        String userNickName = user.getNickName();
        PageImpl<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userId, userNickName, pageable,readFlag);

        return MyMapResponse.create()
                .succeed()
                .buildWith(userAlarmList);
    }

    @Operation(summary = "유저 알람 읽기처리", description = "User read Alarm ")
    @PutMapping
    public MyMapResponse<Long> readAlarm(Authentication authentication) {
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        alarmService.readAlarm(userId);
        return MyMapResponse.create()
                .succeed()
                .buildWith(userId);
    }

    @Operation(summary = "유저 알람 삭제", description = "User delete Alarm ")
    @DeleteMapping
    public MyMapResponse<List<Long>> deleteAlarm(@RequestBody AlarmDeleteDto alarmDeleteDto) {
        JSONObject json = new JSONObject();
        List<Long> alarmList = alarmDeleteDto.getAlarmList();
        alarmService.deleteAlarmList(alarmList);
        return MyMapResponse.create()
                .succeed()
                .buildWith(alarmList);
    }

}