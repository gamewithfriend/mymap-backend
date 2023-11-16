package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.Alarm;
import com.sillimfive.mymap.domain.Report;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapLike;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.*;
import com.sillimfive.mymap.web.dto.report.ReportCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapLikeRequestDto;
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
    private final RoadMapLikeQuertdslRepository roadMapLikeQuertdslRepository;
    private final RoadMapRepository roadMapRepository;
    private final UserRepository userRepository;
    private final AlarmService alarmService;

    public Long CreateOrDelete(User user, Long roadMapId) {


        Long userId = user.getId();
        Long roadMapLikeId = roadMapLikeQuertdslRepository.findByRoadMapIdRoadMapLike(userId, roadMapId);
        RoadMap roadMap = roadMapRepository.getReferenceById(roadMapId);

        if(roadMapLikeId == null){
            RoadMapLike roadMapLike = RoadMapLike.createRoadMapLike(user, roadMap);
            roadMapLikeRepository.save(roadMapLike);
            roadMapLikeId = roadMapLike.getId();
            alarmService.create("alarm02",user,null,roadMapLikeId);
        }else {
            RoadMapLike roadMapLike = roadMapLikeRepository.getReferenceById(roadMapLikeId);
            roadMapLikeRepository.delete(roadMapLike);
            roadMapLikeId = 0l;
        }

        return roadMapLikeId;
    }
}
