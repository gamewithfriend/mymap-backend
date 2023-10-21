package com.sillimfive.mymap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sillimfive.mymap.config.converter.RoadMapCreateDtoConverter;
import com.sillimfive.mymap.config.converter.RoadMapEditDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CustomConverterConfig {

    private final ObjectMapper objectMapper;

    @Bean
    public RoadMapCreateDtoConverter roadMapCreateRequestConverter() {
        return new RoadMapCreateDtoConverter(objectMapper);
    }

    @Bean
    public RoadMapEditDtoConverter roadMapEditDtoConverter() {
        return new RoadMapEditDtoConverter(objectMapper);
    }

}
