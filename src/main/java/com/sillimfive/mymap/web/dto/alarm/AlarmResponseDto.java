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


    public AlarmResponseDto(Alarm alarm , String alarmContent) {
        this.id = alarm.getId();
        this.content = alarmContent;
        this.readFlag = alarm.isReadFlag();
    }
}
