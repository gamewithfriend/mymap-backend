package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Category;
import com.sillimfive.mymap.domain.Image;
import com.sillimfive.mymap.domain.User;
import com.sillimfive.mymap.repository.CategoryRepository;
import com.sillimfive.mymap.repository.ImageRepository;
import com.sillimfive.mymap.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class RoadMapServiceTest {

    @PersistenceContext
    EntityManager em;

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
        User user = new User(null, "test@gmail.com", "mun1103");
        userRepository.save(user);
        categoryRepository.save(new Category("JPA"));
        imageRepository.save(new Image("/home/ubuntu/temp", "roadMap"));

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("")
    void create() {
        //given
        Long userId = userRepository.findByLoginId("mun1103").get(0).getId();
        Long categoryId = categoryRepository.findAll().get(0).getId();
        Long imageId = imageRepository.findAll().get(0).getId();
        List<String> newTags = Arrays.asList("association", "entity", "hierarchy");

        // todo: add more given part

        //when

        //then
    }
}