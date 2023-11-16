package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph(attributePaths = "parent")
    @Override
    List<Category> findAll();
}
