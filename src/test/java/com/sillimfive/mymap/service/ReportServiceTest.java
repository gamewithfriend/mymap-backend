package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.ImageType;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapNodeCreateDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
@ActiveProfiles(value = {"oauth"})
public class ReportServiceTest {

    @Autowired
    RoadMapRepository roadMapRepository;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    RoadMapService roadMapService;


    @PersistenceContext
    EntityManager em;

    @BeforeEach
    void init() {



        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("신고 생성")
    @Rollback(false)
    void create() {
        User user = User.builder()
                .email("test@gmail.com")
                .nickName("mun1103")
                .build();
        userRepository.save(user);
        User reporter = User.builder()
                .email("reporter@gmail.com")
                .nickName("reporter")
                .build();
        userRepository.save(reporter);
        categoryRepository.save(new Category("백엔드", null));
        ImageType imageType = ImageType.ROADMAPS;
        imageRepository.save(new Image("/home/ubuntu/temp", imageType));

        Long userId = userRepository.findByEmail("test@gmail.com").get().getId();
        Long reporterId = userRepository.findByEmail("reporter@gmail.com").get().getId();
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
        createDto.setTags(newTags);
        createDto.setImageId(imageId);
//        JSONObject jsonObject = roadMapService.create(userId, createDto);
//        Long roadMapId =  (Long) jsonObject.get("id");
//
//
//        //when
//        Long report01 = reportService.create("신고합니다.", "report01", roadMapId,reporterId);
//        Optional<Report> findOne = reportRepository.findById(report01);
//
//
//        //then
//        assertTrue(findOne.isPresent());
//
//        Report report = findOne.get();
//        assertThat(report.getContent()).isEqualTo("신고합니다.");
    }
}
