package com.favorite.artists.config;

import com.fasterxml.jackson.databind.Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

@Configuration
class CustomMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    public void setSupportedMediaTypes(List<MediaType> supportedMediaTypes) {
        super.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                new MediaType("text", "javascript"))
        );
    }

    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }
}