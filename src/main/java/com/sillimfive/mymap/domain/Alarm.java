package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Alarm extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private String alarmType;
    private boolean deleteFlag;
    private boolean readFlag;
    private LocalDateTime createdDate;

    @Builder
    protected Alarm(String alarmType, boolean deleteFlag, boolean readFlag, User user){
        this.alarmType = alarmType;
        this.deleteFlag = deleteFlag;
        this.readFlag = readFlag;
        this.user = user;
    }

    public static Alarm createAlarm(String alarmType, boolean deleteFlag, boolean readFlag, User user) {

        return Alarm.builder()
                .alarmType(alarmType)
                .deleteFlag(deleteFlag)
                .readFlag(readFlag)
                .user(user)
                .build();

    }


}
