package com.sillimfive.mymap.web.dto.roadmap.reply;

import com.sillimfive.mymap.domain.roadmap.ReplyStatus;
import com.sillimfive.mymap.web.dto.user.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoadMapReplyResponseDto {

    private Long id;
    @Schema(nullable = true)
    private Long parentId;
    private UserDto userDto;
    @Schema(example = "공부하기 싫어요...ㅠㅠ")
    private String content;
    private ReplyStatus replyStatus;

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

}
