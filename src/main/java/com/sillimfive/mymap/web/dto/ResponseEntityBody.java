package com.sillimfive.mymap.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@Builder
public class ResponseEntityBody {
    private LocalDateTime timestamp;
    private String status;
    private String path;
    private String method;
    private String message;
}
