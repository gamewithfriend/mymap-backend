package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapNode;
import com.sillimfive.mymap.domain.roadmap.RoadMapTag;
import com.sillimfive.mymap.repository.CategoryRepository;
import com.sillimfive.mymap.repository.ImageRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeEditDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapEditDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"maria", "oauth"})
class RoadMapServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    RoadMapRepository roadMapRepository;

    @Autowired
    RoadMapService roadMapService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ImageRepository imageRepository;

    @BeforeEach
    void init() {
        User user = User.builder()
                .email(UUID.randomUUID() + "@gmail.com")
                .nickName("mun1103")
                .build();
        userRepository.save(user);
        categoryRepository.save(new Category("백엔드"));
        imageRepository.save(new Image("/home/ubuntu/temp", "roadMap"));

        em.flush();
        em.clear();
    }

    @Test
    @Rollback(value = false)
    @DisplayName("로드맵 생성")
    void create() {
        //given
        Long userId = userRepository.findAll().stream().findAny().get().getId();
        Long categoryId = categoryRepository.findAll().get(0).getId();
        Long imageId = imageRepository.findAll().get(0).getId();
        List<String> newTags = Arrays.asList("association", "entity", "hierarchy");

        RoadMapCreateDto createDto = new RoadMapCreateDto();
        createDto.setTitle("Pure JPA study roadMap");
        createDto.setDescription("recommend to study with Inflearn");
        List<RoadMapNodeCreateDto> roadMapNodeDtoList = Arrays.asList(
                new RoadMapNodeCreateDto(0, "All about EntityManager", "Persistence Context"),
                new RoadMapNodeCreateDto(1, "@Entity and @Id", "Mapping with Table in database"),
                new RoadMapNodeCreateDto(2, "basic api like persist, find, flush, clear, etc", "Repository"),
                new RoadMapNodeCreateDto(3, "createQuery, bulk Update, dynamic query", "JPQL")
        );
        createDto.setNodeDtoList(roadMapNodeDtoList);
        createDto.setCategoryId(categoryId);
        createDto.setNewTags(newTags);

        //when
        Long roadMapId = roadMapService.create(userId, imageId, createDto);
        Optional<RoadMap> findOne = roadMapRepository.findById(roadMapId);

        //then
        assertTrue(findOne.isPresent());

        RoadMap roadMap = findOne.get();
        assertThat(roadMap.getTitle()).isEqualTo(createDto.getTitle());
        assertThat(roadMap.getRoadMapTags().size()).isEqualTo(newTags.size());
    }

    @Test
    @DisplayName("로드맵 편집")
    void edit() {
        //given
        RoadMap givenRoadMap = roadMapRepository.findAll().stream().findAny().get();
        Long roadMapId = givenRoadMap.getId();
        Long categoryId = categoryRepository.findAll().stream().findAny().get().getId();

        List<Long> roadMapTagIdList = givenRoadMap.getRoadMapTags()
                .stream()
                .map(RoadMapTag::getId).collect(Collectors.toList());

        roadMapTagIdList.remove(0);

        List<RoadMapNodeEditDto> nodeUpdateDtoList = new ArrayList<>();
        int index = 0;
        for (RoadMapNode roadMapNode : givenRoadMap.getRoadMapNodes()) {
            RoadMapNodeEditDto updateDto = new RoadMapNodeEditDto();
            updateDto.setId(roadMapNode.getId());
            if (index > 1) {
                updateDto.setNodeTitle(roadMapNode.getNodeTitle() + " changed");
                updateDto.setNodeContent(roadMapNode.getNodeContent() + " changed");
            }
            else {
                updateDto.setNodeTitle(roadMapNode.getNodeTitle());
                updateDto.setNodeContent(roadMapNode.getNodeContent());
            }
            updateDto.setOrder(roadMapNode.getNodeOrder());

            nodeUpdateDtoList.add(updateDto);
            index++;
        }

        RoadMapEditDto updateDto = new RoadMapEditDto();
        updateDto.setCategoryId(categoryId);
        updateDto.setTitle("changed Title");
        updateDto.setDescription("changed RoadMap");
        updateDto.setRoadMapTagIds(roadMapTagIdList);
        updateDto.setNodeDtoList(nodeUpdateDtoList);

        //when
        JSONObject jsonObject = roadMapService.edit(roadMapId, updateDto);

        em.flush();
        em.clear();

        boolean result = (boolean) jsonObject.get("result");
        RoadMap foundRoadMap = roadMapRepository.findById(roadMapId).get();
        //then
        assertTrue(result);
    }
}