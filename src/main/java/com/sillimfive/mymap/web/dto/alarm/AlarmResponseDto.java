package com.sillimfive.mymap.web.dto.alarm;

import com.sillimfive.mymap.domain.Alarm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AlarmResponseDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "님의 로드맵에 댓글이 달렸습니다.")
    private String content;
    @Schema(example = "읽은 알람 Y 안 읽은 알람 N")
    private boolean readFlag;
    @Schema(example = "alarmType: alarm01: 댓글 달림 ,alarm02: 좋아요 눌림 ,alarm03: 공지사항")
    private  String alarmType;
    @Schema(example = "targetId: alarm01: reply_id ,alarm02: like_id ,otherwise: alarm_id ")
    private  Long targetId;

    public AlarmResponseDto(Alarm alarm , String content,String alarmType,Long targetId) {
        this.id = alarm.getId();
        this.content = content;
        this.readFlag = alarm.isReadFlag();
        this.alarmType = alarm.getAlarmType();
        this.targetId = targetId;
    }
}
