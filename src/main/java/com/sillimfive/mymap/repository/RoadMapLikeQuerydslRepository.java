package com.sillimfive.mymap.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sillimfive.mymap.domain.roadmap.QRoadMapLike.roadMapLike;

@Repository
@RequiredArgsConstructor
public class RoadMapLikeQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public Long findByRoadMapIdRoadMapLike (Long userId, Long roadMapId){
        Long roadMapLikeId = queryFactory.select(roadMapLike.id)
                .where(roadMapLike.roadMap.id.eq(roadMapId), roadMapLike.user.id.eq(userId))
                .from(roadMapLike)
                .fetchOne();
        return roadMapLikeId;
    }
}
