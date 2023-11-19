package com.sillimfive.mymap.service;

import com.sillimfive.mymap.domain.roadmap.ReplyStatus;
import com.sillimfive.mymap.domain.roadmap.RoadMap;
import com.sillimfive.mymap.domain.roadmap.RoadMapReply;
import com.sillimfive.mymap.domain.users.User;
import com.sillimfive.mymap.repository.RoadMapReplyRepository;
import com.sillimfive.mymap.repository.RoadMapRepository;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import com.sillimfive.mymap.web.dto.roadmap.reply.RoadMapReplyCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class RoadMapReplyService {

    private final RoadMapReplyRepository roadMapReplyRepository;
    private final RoadMapRepository roadMapRepository;

    public Long create(User user, Long roadMapId, RoadMapReplyCreateDto roadMapReplyCreateDto){
        RoadMapReply roadMapReplyParent =null;
        Long parentId = roadMapReplyCreateDto.getParentId();
        String content = roadMapReplyCreateDto.getContent();
        RoadMap roadMap = roadMapRepository.getReferenceById(roadMapId);
        if(parentId !=null){
            roadMapReplyParent = roadMapReplyRepository.findById(parentId).get();
        }
        RoadMapReply roadMapReply = RoadMapReply.createRoadMapReply(user, roadMap, content, roadMapReplyParent, ReplyStatus.reply01, false);
        roadMapReplyRepository.save(roadMapReply);

        return roadMapReply.getId();
    }

    public Long edit(Long replyId, String content){
        RoadMapReply roadMapReply = roadMapReplyRepository.findById(replyId).get();
        roadMapReply.changeContent(content);
        return replyId;
    }

    public Long delete(Long replyId){
        RoadMapReply roadMapReply = roadMapReplyRepository.findById(replyId).get();
        roadMapReply.deleteRoadMapRelpy();
        return replyId;
    }

    public Long changeReplyisContributeReply(Long replyId){
        RoadMapReply roadMapReply = roadMapReplyRepository.findById(replyId).get();
        roadMapReply.changeReplyisContributeReply();
        return replyId;
    }
}
