package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadMapRepository extends JpaRepository<RoadMap, Long> {
}
