package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.AlarmQuerydslRepository;
import com.sillimfive.mymap.repository.AlarmRepository;
import com.sillimfive.mymap.repository.CodeRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final AlarmQuerydslRepository alarmQuerydslRepository;
    private final CodeRepository codeRepository;
    private final UserRepository userRepository;

    public Long create(String alarmType, User user) {

        Alarm alarm = Alarm.createAlarm(alarmType, false, false, user);
        alarm = alarmRepository.save(alarm);
        return alarm.getId();
    }

    public List<AlarmResponseDto> findUserAlarmList(Long userId) {
        List<Alarm> alarmList = alarmQuerydslRepository.findByUserIdAlarm(userId);
        List<AlarmResponseDto> alarmResponseDtoList = new ArrayList<>();

        for (int i=0; i<alarmList.size(); i++ ){
            AlarmResponseDto alarmResponseDto = new AlarmResponseDto(alarmList.get(i));
            alarmResponseDtoList.add(alarmResponseDto);
        }
        return alarmResponseDtoList;
    }

    public Long countNotReadAlarm(Long userId) {
        Long countNotReadAlarmNumber = alarmQuerydslRepository.countNotReadAlarm(userId);
        return countNotReadAlarmNumber;
    }

    public void deleteAlarmList(List<Long> alarmList){
        alarmQuerydslRepository.deleteAlarm(alarmList);
    }

    public void readAlarm(Long userId){
        alarmQuerydslRepository.readAlram(userId);
    }

}
