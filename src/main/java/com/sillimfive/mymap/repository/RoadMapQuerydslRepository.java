package com.sillimfive.mymap.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.sillimfive.mymap.domain.roadmap.QRoadMap.roadMap;

@Repository
@RequiredArgsConstructor
public class RoadMapQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public Optional<RoadMap> findByIdWithNodeFetch(Long id) {
        RoadMap result = queryFactory
                .selectFrom(roadMap)
                .join(roadMap.roadMapNodes).fetchJoin()
                .join(roadMap.user).fetchJoin()
                .join(roadMap.creator).fetchJoin()
                .join(roadMap.category).fetchJoin()
                .join(roadMap.image).fetchJoin()
                .where(roadMap.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(result);
    }
}
