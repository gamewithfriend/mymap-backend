package com.sillimfive.mymap.domain.tag;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Tag {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;
    private String name;
    private int count;
}
