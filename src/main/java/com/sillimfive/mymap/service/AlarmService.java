package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.AlarmType;
import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmQuerydslRepository alarmQuerydslRepository;
    private final RoadMapReplyRepository roadMapReplyRepository;
    private final RoadMapLikeRepository roadMapLikeRepository;

    public Long create(AlarmType alarmType, User user, Long roadMapReplyId, Long roadMapLikeId) {
        RoadMapReply roadMapReply =null;
        RoadMapLike roadMapLike =null;
        if(roadMapReplyId != null){
             roadMapReply = roadMapReplyRepository.findById(roadMapReplyId).get();
        }else {
             roadMapLike = roadMapLikeRepository.findById(roadMapLikeId).get();
        }

        Alarm alarm = Alarm.createAlarm(alarmType, false, false, user,roadMapReply,roadMapLike);
        alarm = alarmRepository.save(alarm);
        return alarm.getId();
    }

    public PageImpl<AlarmResponseDto> findUserAlarmList(Long userId, String userNickName, Pageable pageable,boolean readFlag) {
        userNickName ="SSSS";
        PageImpl<AlarmResponseDto>  alarmList= alarmQuerydslRepository.findByUserIdAlarm(userId, userNickName, pageable,readFlag);
        return alarmList;
    }

    public void deleteAlarmList(List<Long> alarmList){
        alarmQuerydslRepository.deleteAlarm(alarmList);
    }

    public void readAlarm(Long userId){
        alarmQuerydslRepository.readAlram(userId);
    }

}
