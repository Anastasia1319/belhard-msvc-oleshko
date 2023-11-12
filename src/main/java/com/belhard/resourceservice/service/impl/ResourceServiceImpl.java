package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entity.Resource;
import com.belhard.resourceservice.exceptions.NotFoundException;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    private ResourceRepository resourceRepository;
    private ResourceMapper mapper;

    @Override
    public ResourceIdDto upload(byte[] audio) {
        log.info("Audio loading method was called");
        Resource uploaded = resourceRepository.saveAndFlush(mapper.toEntity(audio));
        log.info("Audio was saved successfully to database");
        return mapper.toIdDto(uploaded);
    }

    @Override
    public byte[] download(Long id) {
        log.info("Audio download method was called");
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Audio with id " + id + " not found"));
        log.info("Audio with id {} was found", id);
        return resource.getAudio();
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        return null;
    }
}
