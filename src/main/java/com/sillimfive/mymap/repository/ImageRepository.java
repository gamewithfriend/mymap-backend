package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
