package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
