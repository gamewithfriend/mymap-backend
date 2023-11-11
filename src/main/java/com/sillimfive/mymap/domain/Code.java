package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Code implements Persistable<String> {

    @Id
    @Column(name = "code_id")
    private String id;

    private String value;
    private String description;
    private String codeType;

    @Transient
    private boolean isCreated;

    @Builder
    protected Code(String id, String value, String description,String codeType){
        this.id = id;
        this.value = value;
        this.description = description;
        this.codeType = codeType;
        this.isCreated = true;
    }

    @Override
    public boolean isNew() {
        return isCreated;
    }

}
