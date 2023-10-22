package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.repository.AlarmRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.web.dto.roadmap.AlarmResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"oauth"})
public class AlarmServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    AlarmService alarmService;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        User user = User.builder()
                .email(UUID.randomUUID() + "testJung@gmail.com")
                .nickName(UUID.randomUUID() + "test")
                .build();
        userRepository.save(user);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("알람 생성")
    @Rollback(true)
    void create() {

        User userTest = userRepository.findAll().stream().findAny().get();

        //when
        Long alarmId = alarmService.create("alram01",userTest);
        Optional<Alarm> findOne = alarmRepository.findById(alarmId);

        //then
        assertTrue(findOne.isPresent());

        Alarm alarm = findOne.get();
        assertThat(alarm.getAlarmType()).isEqualTo(alarm.getAlarmType());
    }

    @Test
    @DisplayName("유저 알람 리스트 조회")
    @Rollback(true)
    void selectUserAlarmList() {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long alarmId1 = alarmService.create("alram01",userTest);
        Long alarmId2 = alarmService.create("alram02",userTest);
        Long alarmId3 = alarmService.create("alram03",userTest);
        Long alarmId4 = alarmService.create("alram04",userTest);


        //when
        List<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userTest.getId());

        //then
        assertThat(userAlarmList.size()).isEqualTo(4);

    }

    @Test
    @DisplayName("유저 알람 삭제")
    @Rollback(true)
    void deleteUserAlarmList() {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long alarmId1 = alarmService.create("alram01",userTest);
        Long alarmId2 = alarmService.create("alram02",userTest);
        Long alarmId3 = alarmService.create("alram03",userTest);
        Long alarmId4 = alarmService.create("alram04",userTest);

        List<Long> deleteAlarmList = new ArrayList<Long>();

        deleteAlarmList.add(alarmId1);
        deleteAlarmList.add(alarmId2);


        //when
        alarmService.deleteAlarmList(deleteAlarmList);


        //then
        List<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userTest.getId());
        assertThat(userAlarmList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("알람 읽기")
    @Rollback(true)
    void readAlarm() {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long userTestId = userTest.getId();

        //when
        alarmService.readAlarm(userTestId);


        //then
        List<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userTest.getId());
        Long notReadAlarmNumber = alarmService.countNotReadAlarm(userTest.getId());
        assertThat(notReadAlarmNumber).isEqualTo(0);

    }


    @Test
    @DisplayName("유저 읽지않은 알람 수")
    @Rollback(true)
    void countNotReadAlarm() {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long alarmId1 = alarmService.create("alram01",userTest);
        Long alarmId2 = alarmService.create("alram02",userTest);
        Long alarmId3 = alarmService.create("alram03",userTest);
        Long alarmId4 = alarmService.create("alram04",userTest);


        //when
        Long notReadAlarmNumber = alarmService.countNotReadAlarm(userTest.getId());


        //then
        assertThat(notReadAlarmNumber).isEqualTo(4);

    }
}
