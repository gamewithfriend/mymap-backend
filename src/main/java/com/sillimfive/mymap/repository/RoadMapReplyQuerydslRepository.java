package com.sillimfive.mymap.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sillimfive.mymap.domain.users.QUser;
import com.sillimfive.mymap.web.dto.roadmap.reply.RoadMapReplyResponseDto;
import com.sillimfive.mymap.web.dto.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sillimfive.mymap.domain.QAlarm.alarm;
import static com.sillimfive.mymap.domain.roadmap.QRoadMapReply.roadMapReply;
import static com.sillimfive.mymap.domain.users.QUser.user;

@Repository
@RequiredArgsConstructor
public class RoadMapReplyQuerydslRepository {
    private final JPAQueryFactory queryFactory;

    public PageImpl<RoadMapReplyResponseDto> findByRoadMapIdReply(Long userId, Long roadMapId, Pageable pageable) {
        List<RoadMapReplyResponseDto> childrenList = queryFactory
                .select(Projections.fields(RoadMapReplyResponseDto.class,
                        roadMapReply.id,
                        roadMapReply.parent.id.as("parentId"),
                        new CaseBuilder()
                                .when(roadMapReply.deleteFlag.eq(false)).then(roadMapReply.content)
                                .otherwise("삭제된 댓글입니다.")
                                .as("content"),
                        Projections.fields(UserDto.class,
                                roadMapReply.user.id,
                                roadMapReply.user.email,
                                roadMapReply.user.nickName).as("userDto"),
                        roadMapReply.replyStatus,
                        roadMapReply.deleteFlag
                        ))
                        .from(roadMapReply,user)
                        .where(roadMapReply.parent.isNotNull()
                                .and(roadMapReply.user.id.eq(userId))
                        )
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        List<RoadMapReplyResponseDto> resultList = queryFactory
                .select(Projections.fields(RoadMapReplyResponseDto.class,
                        roadMapReply.id,
                        roadMapReply.content,
                        Projections.fields(UserDto.class,
                                roadMapReply.user.id,
                                roadMapReply.user.email,
                                roadMapReply.user.nickName).as("userDto"),
                        roadMapReply.replyStatus,
                        roadMapReply.deleteFlag
                ))
                .from(roadMapReply,user)
                .where(roadMapReply.parent.isNull()
                        .and(roadMapReply.user.id.eq(userId))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        for (int i=0; i< resultList.size(); i++){
            resultList.get(i).setChildrenList(childrenList);
        }
        Long countAlarmNumber = queryFactory
                .select(roadMapReply.count())
                .from(roadMapReply)
                .where(
                        roadMapReply.roadMap.id.eq(roadMapId)
                                .and(roadMapReply.deleteFlag.eq(false))
                )
                .fetchOne();

        return new PageImpl<>(resultList,pageable,countAlarmNumber);
    }
}
