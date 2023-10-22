package com.sillimfive.mymap.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.domain.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sillimfive.mymap.domain.QAlarm.alarm;

@Repository
@RequiredArgsConstructor
public class AlarmQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Alarm> findByUserIdAlarm(Long userId) {
        List<Alarm> result = queryFactory
                .select(alarm)
                .from(alarm)
                .where(
                        alarm.user.id.eq(userId)
                                .and(alarm.deleteFlag.eq(false))
                      )
                .orderBy(alarm.id.desc())
                .fetch();

        return result;
    }

    public Long countNotReadAlarm(Long userId) {

        Long countNotReadAlarmNumber = queryFactory
                .select(alarm.count())
                .from(alarm)
                .where(
                        alarm.user.id.eq(userId)
                                .and(alarm.deleteFlag.eq(false))
                                .and(alarm.readFlag.eq(false))
                )
                .fetchOne();
        return countNotReadAlarmNumber;
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
