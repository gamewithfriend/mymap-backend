package com.sillimfive.mymap.domain;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public abstract class BaseTimeEntity {

    private LocalDateTime createdDate;
    private LocalDateTime lastModified;

}
