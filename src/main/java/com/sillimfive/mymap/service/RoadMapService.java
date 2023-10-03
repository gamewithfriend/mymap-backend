package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapTag;
import com.sillimfive.mymap.domain.tag.Tag;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.RoadMapResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoadMapService {

    private final RoadMapRepository roadMapRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    public Long create(Long userId, Long imageId, RoadMapCreateDto createDto) {

        // find category, tag information
        Optional<Category> category = categoryRepository.findById(createDto.getCategoryId());
        Assert.isTrue(category.isPresent(), "Category should not be null");

        List<Tag> tags = new ArrayList<>();

        if (Optional.ofNullable(createDto.getTagIds()).isPresent())
            tags.addAll(tagRepository.findByIdIn(createDto.getTagIds()));

        if(createDto.getNewTags().size() != 0) {
            List<Tag> tagList = createDto
                    .getNewTags().stream().map(Tag::new).collect(Collectors.toList());

            tags.addAll(tagList);
        }

        // create RoadMapTag
        List<RoadMapTag> roadMapTags = RoadMapTag.createRoadMapTags(tags);

        // create RoadMapNode, RoadMap
        Image image = imageRepository.findById(imageId).get();
        User user = userRepository.findOne(userId);

        RoadMap roadMap = createDto.convert(user, category.get(), image);
        roadMap.addRoadMapNodes(createDto.getRoadMapNodesFromDto());
        roadMap.addRoadMapTags(roadMapTags);

        roadMapRepository.save(roadMap);

        return roadMap.getId();
    }

    public RoadMapResponseDto findById(Long id) {
        RoadMap roadMap = roadMapRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("There is no roadMap"));

        return null;
    }

}
