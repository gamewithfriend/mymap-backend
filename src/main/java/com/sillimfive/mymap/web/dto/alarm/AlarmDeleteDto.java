package com.sillimfive.mymap.web.dto.alarm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sillimfive.mymap.domain.Alarm;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class AlarmDeleteDto {
    @JsonProperty("AlarmList")
    @ArraySchema(schema = @Schema(example = "1"))
    private List<@Min(value = 1) Long> alarmList;

}
