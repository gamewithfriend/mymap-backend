package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapTag;
import com.sillimfive.mymap.domain.tag.Tag;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.roadmap.*;
import com.sillimfive.mymap.web.dto.tag.TagDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoadMapService {

    private final RoadMapRepository roadMapRepository;
    private final RoadMapQuerydslRepository roadMapQuerydslRepository;
    private final RoadMapTagRepository roadMapTagRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    // todo: batch insert for node, tag
    @Transactional
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
        User user = userRepository.findById(userId).get();

        RoadMap roadMap = createDto.convert(user, category.get(), image);
        roadMap.addRoadMapNodes(createDto.getRoadMapNodesFromDto());
        roadMap.addRoadMapTags(roadMapTags);

        roadMapRepository.save(roadMap);

        return roadMap.getId();
    }

    @Transactional
    public JSONObject edit(Long roadMapId, RoadMapEditDto updateDto) {
        Optional<RoadMap> r1 = roadMapQuerydslRepository.findByIdWithNode(roadMapId);
        if (r1.isEmpty())
            throw new IllegalArgumentException("there is no roadMap");

        RoadMap roadMap = r1.get();

        Category foundCategory = categoryRepository.findById(updateDto.getCategoryId()).orElseThrow(()
                -> new IllegalArgumentException("There is no category searched"));

        if (!roadMap.getCategory().equals(foundCategory)) roadMap.changeCategory(foundCategory);

        boolean contentsChanged = roadMap.changeContents(updateDto.getTitle(), updateDto.getDescription());

        roadMap.changeNodeTree(updateDto.getNodeDtoList());

        List<RoadMapTag> newRoadMapTags = new ArrayList<>();

        if (updateDto.hasNewTags()) {
            List<Tag> tagList = updateDto.getNewTags().stream()
                    .map(Tag::new).collect(Collectors.toList());

            newRoadMapTags.addAll(RoadMapTag.createRoadMapTags(tagList));
        }
        roadMap.updateRoadMapTags(updateDto.getRoadMapTagIds(), newRoadMapTags);

        // todo: add to roadMapHistory

        JSONObject json = new JSONObject();
        json.put("id", roadMapId);
        json.put("result", true);

        return json;
    }

    public RoadMapDetailResponseDto findById(Long id) {
        RoadMap roadMap = roadMapQuerydslRepository.findByIdWithNode(id).orElseThrow(()
                -> new IllegalArgumentException("There is no roadMap for " + id));

        List<TagDto> tags = roadMapTagRepository.findByRoadMapId(id).stream()
                .map(roadMapTag -> new TagDto(roadMapTag.getTag()))
                .collect(Collectors.toList());

        RoadMapDetailResponseDto response = new RoadMapDetailResponseDto(roadMap);
        response.addTags(tags);

        return response;
    }

    public PageImpl<RoadMapResponseDto> findListBy(RoadMapSearch searchCondition, Pageable pageable) {

        return roadMapQuerydslRepository.searchList(searchCondition, pageable);
    }
}
