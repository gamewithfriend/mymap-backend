package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles(value = {"oauth"})
public class AlarmServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AlarmRepository alarmRepository;

    @Autowired
    AlarmService alarmService;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    RoadMapReplyRepository roadMapReplyRepository;

    @Autowired
    RoadMapLikeRepository roadMapLikeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    RoadMapService roadMapService;

    @Autowired
    RoadMapRepository roadMapRepository;

    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {
        User user = User.builder()
                .email(UUID.randomUUID() + "testJung@gmail.com")
                .nickName(UUID.randomUUID() + "test")
                .build();
        userRepository.save(user);
        categoryRepository.save(new Category(UUID.randomUUID() + "백엔드"));
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("알람 생성")
    @Rollback(false)
    void create() {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long roadMapTestId = 1L;
        RoadMap roadMap = roadMapRepository.findById(roadMapTestId).get();

        RoadMapLike roadMapLikeTest = RoadMapLike.builder()
                .user(userTest)
                .roadMap(roadMap)
                .build();

        Long roadMapLikeTestTd = roadMapLikeTest.getId();
        //when
        Long alarmId = alarmService.create("alram01",userTest,null,roadMapLikeTestTd);
        Optional<Alarm> findOne = alarmRepository.findById(alarmId);

        //then
        assertTrue(findOne.isPresent());

        Alarm alarm = findOne.get();
        assertThat(alarm.getAlarmType()).isEqualTo(alarm.getAlarmType());
    }

    @Test
    @DisplayName("유저 알람 리스트 조회")
    @Rollback(true)
    void selectUserAlarmList(Pageable pageable) {



        User userTest = userRepository.findAll().stream().findAny().get();
        Long alarmId1 = alarmService.create("alram01",userTest,null,null);
        Long alarmId2 = alarmService.create("alram02",userTest,null,null);
        Long alarmId3 = alarmService.create("alram03",userTest,null,null);
        Long alarmId4 = alarmService.create("alram04",userTest,null,null);


        //when
        PageImpl<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userTest.getId(), userTest.getNickName(), pageable,false);

        //then
        assertThat(userAlarmList.stream().count()).isEqualTo(4);

    }

    @Test
    @DisplayName("유저 알람 삭제")
    @Rollback(true)
    void deleteUserAlarmList(Pageable pageable) {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long alarmId1 = alarmService.create("alram01",userTest,null,null);
        Long alarmId2 = alarmService.create("alram02",userTest,null,null);
        Long alarmId3 = alarmService.create("alram03",userTest,null,null);
        Long alarmId4 = alarmService.create("alram04",userTest,null,null);

        List<Long> deleteAlarmList = new ArrayList<Long>();

        deleteAlarmList.add(alarmId1);
        deleteAlarmList.add(alarmId2);


        //when
        alarmService.deleteAlarmList(deleteAlarmList);


        //then
        PageImpl<AlarmResponseDto> userAlarmList = alarmService.findUserAlarmList(userTest.getId(), "", pageable,false);
        assertThat(userAlarmList.stream().count()).isEqualTo(2);

    }

    @Test
    @DisplayName("알람 읽기")
    @Rollback(true)
    void readAlarm(Pageable pageable) {

        User userTest = userRepository.findAll().stream().findAny().get();
        Long userTestId = userTest.getId();

        //when
        alarmService.readAlarm(userTestId);


        //then

    }

}
