package com.belhard.resourceservice.service;

import com.belhard.resourceservice.data.entity.Resource;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ResourceMapper {
    ResourceIdDto toIdDto(Resource resource);

    default Resource toEntity(byte[] audio) {
        Resource resource = new Resource();
        resource.setAudio(audio);
        return resource;
    }
}
