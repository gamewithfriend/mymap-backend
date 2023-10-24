package com.sillimfive.mymap.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String path;
    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    public Image(String path, ImageType imageType) {
        this.path = path;
        this.imageType = imageType;
    }

    public void changePath(String newPath) {
        path = newPath;
    }
}
