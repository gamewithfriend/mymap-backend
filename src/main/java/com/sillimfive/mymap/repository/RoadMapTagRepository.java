package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.roadmap.RoadMapTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoadMapTagRepository extends JpaRepository<RoadMapTag, Long> {

    @Query("select rt from RoadMapTag rt join fetch rt.tag where rt.roadMap.id = :id")
    List<RoadMapTag> findByRoadMapId(@Param("id") Long roadMapId);
}
