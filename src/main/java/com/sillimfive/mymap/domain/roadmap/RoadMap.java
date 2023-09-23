package com.sillimfive.mymap.domain.roadmap;

import com.sillimfive.mymap.domain.BaseTimeEntity;
import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.tag.RoadMapTag;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RoadMap extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roadmap_id")
    private Long id;

    private boolean hiddenFlag;

    private boolean deleteFlag;

    @OneToMany(mappedBy = "roadMap", cascade = CascadeType.ALL)
    private List<RoadMapNode> nodes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime lastModified;

    @OneToMany(mappedBy = "roadMap")
    private List<RoadMapTag> tags = new ArrayList<>();

    private String title;
    private String description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;
}
