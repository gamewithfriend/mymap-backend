package com.sillimfive.mymap.web.dto.study;

import com.sillimfive.mymap.web.dto.roadmap.RoadMapEditDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class RoadMapStudyStartDto extends RoadMapEditDto {

    private boolean changedFlag;
}
