package com.sillimfive.mymap.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapBookmarkResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sillimfive.mymap.domain.roadmap.QRoadMapBookmark.roadMapBookmark;
import static com.sillimfive.mymap.domain.users.QUser.user;

@Repository
@RequiredArgsConstructor
public class RoadMapBookmarkQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public PageImpl<RoadMapBookmarkResponseDto> findByUserIdRoadMapBookmark (Long userId, Pageable pageable){
        List<RoadMapBookmarkResponseDto> resultList = queryFactory
                .select(Projections.fields(RoadMapBookmarkResponseDto.class,
                        roadMapBookmark.id.as("roadmapBookmarkId"),
                        roadMapBookmark.roadMap.id.as("roadMapId")
                ))
                .from(roadMapBookmark,user)
                .where(roadMapBookmark.user.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        Long countBookmarkNumber = queryFactory
                .select(roadMapBookmark.count())
                .from(roadMapBookmark)
                .where(roadMapBookmark.user.id.eq(userId))
                .fetchOne();
        return new PageImpl<>(resultList,pageable,countBookmarkNumber);
    }

    public void deleteBookmarkList(List<Long> bookmarkIdList) {

        queryFactory
                .delete(roadMapBookmark)
                .where(roadMapBookmark.id.in(bookmarkIdList))
                .execute();

    }

    public Long findByRoadMapIdRoadMapBookmark (Long userId, Long roadMapId){
        Long roadMapBookmarkId = queryFactory.select(roadMapBookmark.id)
                .where(roadMapBookmark.roadMap.id.eq(roadMapId), roadMapBookmark.user.id.eq(userId))
                .from(roadMapBookmark)
                .fetchOne();
        return roadMapBookmarkId;
    }
}
