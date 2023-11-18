package com.sillimfive.mymap.repository;

import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadMapLikeRepository extends JpaRepository<RoadMapLike, Long> {

    @Query("select count(rl) from RoadMapLike rl where rl.roadMap.id = :id")
    int getLikeCount(@Param("id") Long roadMapId);
}
