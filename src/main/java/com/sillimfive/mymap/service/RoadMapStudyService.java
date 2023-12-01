package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.study.RoadMapStudy;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RoadMapQuerydslRepository;
import com.sillimfive.mymap.repository.RoadMapStudyRepository;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCopyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RoadMapStudyService {

    private final RoadMapQuerydslRepository roadMapQuerydslRepository;
    private final RoadMapService roadMapService;
    private final RoadMapStudyRepository roadMapStudyRepository;

    private static String DEFAULT_MESSAGE = "";

    @Transactional
    public Long startStudy(User user, Long roadMapId, RoadMapCopyDto roadMapCopyDto) {
        RoadMap targetRoadMap = roadMapQuerydslRepository.findByIdWithNode(roadMapId).orElseThrow(()
                -> new IllegalArgumentException("roadMapId is not valid"));

        if (!targetRoadMap.getCreator().getId().equals(user.getId())) {
            targetRoadMap = roadMapService.forkWith(user, roadMapId, roadMapCopyDto);
        }

        RoadMapStudy roadMapStudy = new RoadMapStudy(user, targetRoadMap, null);
        roadMapStudy.addStudyNode(targetRoadMap.getRoadMapNodes());

        return roadMapStudyRepository.save(roadMapStudy).getId();
    }

    //todo: logic to add memo

}
