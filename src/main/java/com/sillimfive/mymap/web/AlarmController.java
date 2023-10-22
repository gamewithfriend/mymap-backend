package com.sillimfive.mymap.web;

import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.service.AlarmService;
import com.sillimfive.mymap.web.dto.roadmap.AlarmDeleteDto;
import com.sillimfive.mymap.web.dto.roadmap.AlarmResponseDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "유저 알람 리스트 조회", description = "Get the User Alarm List")
    @GetMapping
    public ResponseEntity<?> findUserAlarmList(Authentication authentication) {
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        List<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userId);
        json.put("userAlarmList",userAlarmList);
        json.put("data",json.get("userAlarmList"));
        json.put("result",json.get("data"));
        return ResponseEntity.ok(json);
    }

    @Operation(summary = "유저 읽지 않은 알람 갯수 조회", description = "Get the User unRead Alarm Count")
    @GetMapping("/count")
    public ResponseEntity<?> countNotReadAlarm(Authentication authentication) {
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        Long unReadAlarmCount = alarmService.countNotReadAlarm(userId);
        json.put("unReadAlarmCount",unReadAlarmCount);
        json.put("data",json.get("unReadAlarmCount"));
        json.put("result",json.get("data"));
        return ResponseEntity.ok(json);
    }

    @Operation(summary = "유저 알람 읽기처리", description = "User read Alarm ")
    @PutMapping
    public ResponseEntity<?> readAlarm(Authentication authentication) {
        JSONObject json = new JSONObject();
        User user = (User) authentication.getPrincipal();
        Long userId = user.getId();
        alarmService.readAlarm(userId);
        return ResponseEntity.ok(json);
    }

    @Operation(summary = "유저 알람 삭제", description = "User delete Alarm ")
    @DeleteMapping
    public ResponseEntity<?> deleteAlarm(@RequestBody AlarmDeleteDto alarmDeleteDto) {
        JSONObject json = new JSONObject();
        List<Long> alarmList = alarmDeleteDto.getAlarmList();
        alarmService.deleteAlarmList(alarmList);
        return ResponseEntity.ok(json);
    }

}