package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Code {

    @Id
    @Column(name = "code_id")
    private String id;

    private String title;
    private String description;
    private String codeType;

    @Builder
    public Code(String id, String title, String description,String codeType){
        this.id = id;
        this.title = title;
        this.description = description;
        this.codeType = codeType;
    }

}
