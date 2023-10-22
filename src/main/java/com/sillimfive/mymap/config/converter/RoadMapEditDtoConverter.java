package com.sillimfive.mymap.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapEditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class RoadMapEditDtoConverter implements Converter<String, RoadMapEditDto> {

    private final ObjectMapper objectMapper;

    @Override
    public RoadMapEditDto convert(String source) {
        try {
            return objectMapper.readValue(source, RoadMapEditDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
