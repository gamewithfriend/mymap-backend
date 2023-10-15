package com.sillimfive.mymap.web.dto.tag;


import com.sillimfive.mymap.domain.tag.Tag;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
