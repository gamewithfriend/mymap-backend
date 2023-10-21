package com.sillimfive.mymap.web.dto.roadmap;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.web.dto.CategoryDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AlarmResponseDto {
    private Long id;
    private String alarmType;
    private boolean readFlag;


    public AlarmResponseDto(Alarm alarm) {
        this.id = alarm.getId();
        this.alarmType = alarm.getAlarmType();
        this.readFlag = alarm.isReadFlag();
    }
}
