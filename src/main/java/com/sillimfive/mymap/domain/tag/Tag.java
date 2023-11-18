package com.sillimfive.mymap.domain.tag;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    private String name;
    private int count;

    public Tag(String name) {
        this.name = name;
    }

    public void countIncrease() {
        count++;
    }

    public void countDecrease() {
        count--;
    }
}
