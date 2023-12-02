package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.study.Memo;
import com.sillimfive.mymap.domain.study.RoadMapStudy;
import com.sillimfive.mymap.domain.study.RoadMapStudyNode;
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
@Transactional
@Slf4j
public class RoadMapStudyService {

    private final RoadMapQuerydslRepository roadMapQuerydslRepository;
    private final RoadMapService roadMapService;
    private final RoadMapStudyRepository roadMapStudyRepository;

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

    public Boolean registerMemo(Long currentUserId, Long roadMapId, Long roadMapNodeId, String memo) {
        RoadMapStudyNode roadMapStudyNode = getValidRoadMapStudyNode(currentUserId, roadMapId, roadMapNodeId);
        roadMapStudyNode.addMemo(new Memo(memo));

        return true;
    }

    public Boolean updateMemo(Long currentUserId, Long roadMapId, Long roadMapNodeId, String memo) {
        RoadMapStudyNode roadMapStudyNode = getValidRoadMapStudyNode(currentUserId, roadMapId, roadMapNodeId);
        roadMapStudyNode.getMemo().changeMemo(memo);

        return true;
    }

    // todo: 모든 노드에 대한 처리가 complete 된 경우 처리하는 로직 추가 필요.
    public Boolean turnNodeComplete(Long currentUserId, Long roadMapId, Long roadMapNodeId) {
        RoadMapStudyNode roadMapStudyNode = getValidRoadMapStudyNode(currentUserId, roadMapId, roadMapNodeId);
        roadMapStudyNode.turnComplete();

        return true;
    }

    private RoadMapStudyNode getValidRoadMapStudyNode(Long currentUserId, Long roadMapId, Long roadMapNodeId) {
        RoadMapStudy roadMapStudy = roadMapStudyRepository.findByRoadMapId(roadMapId).orElseThrow(()
                -> new IllegalArgumentException("해당 로드맵 식별정보에 대한 로드맵이 존재하지 않습니다."));

        if (!roadMapStudy.getUser().getId().equals(currentUserId))
            throw new IllegalStateException("현재 인증된 사용자의 식별정보가 학습 로드맵의 사용자 정보와 일치하지 않습니다.");

        RoadMapStudyNode roadMapStudyNode = roadMapStudy.getRoadMapStudyNodes().stream()
                .filter(node -> node.getId().equals(roadMapNodeId))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("주어진 노드의 식별자에 대한 노드가 존재하지 않습니다."));

        return roadMapStudyNode;
    }
}
