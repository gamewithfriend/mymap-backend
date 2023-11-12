package com.sillimfive.mymap.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sillimfive.mymap.domain.QAlarm.alarm;
import static com.sillimfive.mymap.domain.QCode.code;

@Repository
@RequiredArgsConstructor
public class AlarmQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public PageImpl<AlarmResponseDto> findByUserIdAlarm(Long userId, String userNickName, Pageable pageable, boolean readFlag) {

        List<AlarmResponseDto> resultList = queryFactory
                .select(Projections.fields(AlarmResponseDto.class,
                        alarm.id,
                        alarm.readFlag,
                        code.description.prepend(userNickName).as("content"),
                        alarm.alarmType,
                        alarm.alarmType
                                .when("alarm01").then(alarm.roadMapReply.id)
                                .when("alarm02").then(alarm.roadMapLike.id)
                                .otherwise(alarm.id)
                                .as("targetId")
                        ))
                        .from(alarm,code)
                        .where(alarm.alarmType.eq(code.id),alarm.readFlag.eq(readFlag))
                .orderBy(alarm.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countAlarmNumber = queryFactory
                .select(alarm.count())
                .from(alarm)
                .where(
                        alarm.user.id.eq(userId)
                                .and(alarm.deleteFlag.eq(false))
                                .and(alarm.readFlag.eq(readFlag))
                )
                .fetchOne();

        return new PageImpl<>(resultList,pageable,countAlarmNumber);
    }

    public void deleteAlarm(List<Long> alarmIdList) {

        queryFactory
                .update(alarm)
                .set(alarm.deleteFlag, true)
                .where(alarm.id.in(alarmIdList))
                .execute();

    }

    public void  readAlram(Long userId){
        queryFactory
                .update(alarm)
                .set(alarm.readFlag, true)
                .where(alarm.user.id.eq(userId), alarm.readFlag.eq(false)  )
                .execute();

    }



}
