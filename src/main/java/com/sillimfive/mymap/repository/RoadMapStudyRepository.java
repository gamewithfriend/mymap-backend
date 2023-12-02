package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.study.RoadMapStudy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoadMapStudyRepository extends JpaRepository<RoadMapStudy, Long> {

    @Query("select rs from RoadMapStudy rs join fetch rs.user join fetch rs.roadMapStudyNodes where rs.roadMap.id = :id")
    Optional<RoadMapStudy> findByRoadMapId(@Param("id") Long roadMapId);
}
