package com.sillimfive.mymap.config.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sillimfive.mymap.web.dto.roadmap.RoadMapCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;

@RequiredArgsConstructor
public class RoadMapCreateDtoConverter implements Converter<String, RoadMapCreateDto> {

    private final ObjectMapper objectMapper;

    @Override
    public RoadMapCreateDto convert(String source) {
        try {
            return objectMapper.readValue(source, RoadMapCreateDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
