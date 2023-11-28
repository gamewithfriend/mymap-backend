package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.AlarmType;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapBookmark;
import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RoadMapBookmarkQuerydslRepository;
import com.sillimfive.mymap.repository.RoadMapBookmarkRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapBookmarkResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoadMapBookmarkService {
    private final RoadMapBookmarkQuerydslRepository roadMapBookmarkQuerydslRepository;
    private final RoadMapBookmarkRepository roadMapBookmarkRepository;
    private final RoadMapRepository roadMapRepository;

    public PageImpl<RoadMapBookmarkResponseDto> findByUserIdRoadMapBookmarkResponseDtoList(Long userId, Pageable pageable){
        return roadMapBookmarkQuerydslRepository.findByUserIdRoadMapBookmark(userId,pageable);
    }

    public void deleteBookmarkList(List<Long> bookmarkList){
        roadMapBookmarkQuerydslRepository.deleteBookmarkList(bookmarkList);
    }

    public Long createOrDelete(User user, Long roadMapId) {
        Long userId = user.getId();
        Long roadMapBookmarkId = roadMapBookmarkQuerydslRepository.findByRoadMapIdRoadMapBookmark(userId, roadMapId);
        RoadMap roadMap = roadMapRepository.getReferenceById(roadMapId);

        if(roadMapBookmarkId == null){
            RoadMapBookmark roadMapBookmark = RoadMapBookmark.createRoadMapBookmark(user, roadMap);
            roadMapBookmarkRepository.save(roadMapBookmark);
            roadMapBookmarkId = roadMapBookmark.getId();
        }else {
            RoadMapBookmark roadMapBookmark = roadMapBookmarkRepository.getReferenceById(roadMapBookmarkId);
            roadMapBookmarkRepository.delete(roadMapBookmark);
            roadMapBookmarkId = 0L;
        }

        return roadMapBookmarkId;
    }
}
