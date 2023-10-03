package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String alarmType;
    private boolean deleteFlag;
    private boolean readFlag;


    @Builder
    protected Alarm(String alarmType, boolean deleteFlag, boolean readFlag){
        this.alarmType = alarmType;
        this.deleteFlag = deleteFlag;
        this.readFlag = readFlag;
    }

    public static Alarm createAlarm(String alarmType, boolean deleteFlag, boolean readFlag) {

        return Alarm.builder()
                .alarmType(alarmType)
                .deleteFlag(deleteFlag)
                .readFlag(readFlag)
                .build();

    }


}
