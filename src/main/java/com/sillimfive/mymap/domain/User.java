package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String loginId;

    public User(){

    }

    public User(Long id,String email,String loginId){
        this.id = id;
        this.email = email;
        this.loginId = loginId;
    }


}
