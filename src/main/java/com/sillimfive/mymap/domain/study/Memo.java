package com.sillimfive.mymap.domain.study;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {

    private String memo;
    private LocalDateTime created;
    private LocalDateTime lastModified;

    public Memo(String memo) {
        this.memo = memo;
        this.created = LocalDateTime.now();
        this.lastModified = LocalDateTime.now();
    }

    public void changeMemo(String memo) {
        this.memo = memo;
        this.lastModified = LocalDateTime.now();
    }
}
