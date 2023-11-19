package com.sillimfive.mymap.web.dto.roadmap.reply;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sillimfive.mymap.domain.roadmap.ReplyStatus;
import com.sillimfive.mymap.web.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RoadMapReplyResponseDto {

    private Long id;
    @Schema(nullable = true)
    private Long parentId;
    private UserDto userDto;
    @Schema(example = "최고의 로드맵이네요 !!, deleteFlag가 true 일시 삭제된 댓글입니다.")
    private String content;
    @Schema(description = "replyStatus: reply01: 일반 댓글 ,reply02: 기여 댓글")
    private ReplyStatus replyStatus;
    @Schema(description = "flag: false: 댓글 보여주기, ture: 삭제된 댓글입니다.")
    private boolean deleteFlag;
    @JsonProperty("childrenList")
    @ArraySchema(schema = @Schema(example = "댓글 하위 리스트"))
    private List<RoadMapReplyResponseDto> childrenList;

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

}
