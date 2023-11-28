package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.roadmap.RoadMapBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadMapBookmarkRepository extends JpaRepository<RoadMapBookmark, Long> {
}
