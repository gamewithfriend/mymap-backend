package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Alram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alram_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String alramType;
    private boolean deleteFlag;
    private boolean readFlag;


    protected Alram(){

    }

    public Alram(Long id,String alramType,boolean deleteFlag,boolean readFlag){
        this.id =  id;
        this.alramType =  alramType;
        this.deleteFlag = deleteFlag;
        this.readFlag = readFlag;
    }


}
