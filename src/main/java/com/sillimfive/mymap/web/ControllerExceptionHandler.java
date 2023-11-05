package com.sillimfive.mymap.web;

import com.sillimfive.mymap.web.dto.Error;
import com.sillimfive.mymap.web.dto.MyMapResponse;
import com.sillimfive.mymap.web.dto.ResponseEntityBody;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ControllerExceptionHandler {

    @ExceptionHandler
    public MyMapResponse handle(HttpServletRequest request, Exception e) {
        e.printStackTrace();

        return MyMapResponse.create()
                .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage()))
                .buildWith(
                    ResponseEntityBody.builder()
                        .timestamp(LocalDateTime.now())
                        .path(request.getRequestURI())
                        .method(request.getMethod())
                        .build());
    }
}
