package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.repository.CategoryRepository;
import com.sillimfive.mymap.repository.ImageRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.repository.UserRepository;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeCreateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback(value = false)
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
                .email("test@gmail.com")
                .nickName("mun1103")
                .build();
        userRepository.save(user);
        categoryRepository.save(new Category("JPA"));
        imageRepository.save(new Image("/home/ubuntu/temp", "roadMap"));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("로드맵 생성")
    void create() {
        //given
        Long userId = userRepository.findByEmail("test@gmail.com").get().getId();
        Long categoryId = categoryRepository.findAll().get(0).getId();
        Long imageId = imageRepository.findAll().get(0).getId();
        List<String> newTags = Arrays.asList("association", "entity", "hierarchy");

        RoadMapCreateDto createDto = new RoadMapCreateDto();
        createDto.setTitle("Pure JPA study roadMap");
        createDto.setDescription("recommend to study with Inflearn");
        List<RoadMapNodeCreateDto> roadMapNodeDtoList = Arrays.asList(
//                new RoadMapNodeCreateDto(0, null, "All about EntityManager", "Persistence Context"),
//                new RoadMapNodeCreateDto(1, 0, "@Entity and @Id", "Mapping with Table in database"),
//                new RoadMapNodeCreateDto(2, 1, "basic api like persist, find, flush, clear, etc", "Repository"),
//                new RoadMapNodeCreateDto(3, 2, "createQuery, bulk Update, dynamic query", "JPQL")
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
    void findOne() {

    }
}