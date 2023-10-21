package com.sillimfive.mymap.web.dto.roadmap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sillimfive.mymap.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AlarmDeleteDto {
    @JsonProperty("AlarmList")
    private List<Long> alarmList;

}
