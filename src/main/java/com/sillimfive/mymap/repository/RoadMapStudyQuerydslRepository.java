package com.sillimfive.mymap.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.web.dto.study.RoadMapStudyDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.sillimfive.mymap.domain.QCategory.category;
import static com.sillimfive.mymap.domain.QImage.image;
import static com.sillimfive.mymap.domain.roadmap.QRoadMap.roadMap;
import static com.sillimfive.mymap.domain.study.QRoadMapStudy.roadMapStudy;
import static com.sillimfive.mymap.domain.users.QUser.user;

@Repository
@RequiredArgsConstructor
public class RoadMapStudyQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public RoadMapStudyDetailDto findStudyDetailByRoadMapId(Long roadMapId) {

        return queryFactory
                .select(Projections.fields(RoadMapStudyDetailDto.class,
                        roadMap.id,
                        roadMap.title,
                        roadMap.description,
                        roadMapStudy.startDate,
                        roadMapStudy.endDate,
                        ExpressionUtils.as(
                            JPAExpressions.select(roadMap.creator.id, roadMap.creator.email, roadMap.creator.nickName)
                                .from(roadMap), "userDto"
                        ))
                )
                .from(roadMapStudy)
                // todo: left join or not??
                .join(roadMapStudy.roadMap, roadMap).fetchJoin()
                .join(roadMap.creator, user).fetchJoin()
                .join(roadMap.origin).fetchJoin()
                .join(roadMap.category, category).fetchJoin()
                .join(roadMap.image, image).fetchJoin()
                .fetchOne();
    }

}
