package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RoadMapLikeQuerydslRepository;
import com.sillimfive.mymap.repository.RoadMapLikeRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoadMapLikeService {

    private final RoadMapLikeRepository roadMapLikeRepository;
    private final RoadMapLikeQuerydslRepository roadMapLikeQuerydslRepository;
    private final RoadMapRepository roadMapRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;

    public Long CreateOrDelete(User user, Long roadMapId) {
        Long userId = user.getId();
        Long roadMapLikeId = roadMapLikeQuerydslRepository.findByRoadMapIdRoadMapLike(userId, roadMapId);
        RoadMap roadMap = roadMapRepository.getReferenceById(roadMapId);

        if(roadMapLikeId == null){
            RoadMapLike roadMapLike = RoadMapLike.createRoadMapLike(user, roadMap);
            roadMapLikeRepository.save(roadMapLike);
            roadMapLikeId = roadMapLike.getId();
            alarmService.create("alarm02",user,null,roadMapLikeId);
        }else {
            RoadMapLike roadMapLike = roadMapLikeRepository.getReferenceById(roadMapLikeId);
            roadMapLikeRepository.delete(roadMapLike);
            roadMapLikeId = 0L;
        }

        return roadMapLikeId;
    }
}
