package com.sillimfive.mymap.web.dto.tag;


import com.sillimfive.mymap.domain.tag.Tag;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class TagDto {
    @Schema(example = "1")
    private Long id;
    @Schema(example = "jpa")
    private String name;

    public TagDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
    }
}
