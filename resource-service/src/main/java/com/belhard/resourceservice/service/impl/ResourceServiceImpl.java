package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entity.Resource;
import com.belhard.resourceservice.exceptions.NotFoundException;
import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    private ResourceRepository resourceRepository;
    private ResourceMapper mapper;
    private SongClient songClient;
    private MetaDataService metaDataService;

    @Override
    public ResourceIdDto upload(byte[] audio) {
        log.info("Audio loading method was called");
        Resource uploaded = resourceRepository.saveAndFlush(mapper.toEntity(audio));
        log.info("Audio was saved successfully to database");
        MetaDataDto metaDataDto = metaDataService.getMetaData(audio);
        metaDataDto.setResourceId(uploaded.getId());
        songClient.create(metaDataDto);
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
        log.info("Audio delete method was called");
        List<Long> deletedIds = new ArrayList<>();
        for (Long id : ids) {
            if (resourceRepository.findById(id).isEmpty()) {
                log.info("Audio with id {} not found", id);
                continue;
            }
            resourceRepository.deleteById(id);
            deletedIds.add(id);
            log.info("Audio with id {} was deleted", id);
        }
        ResourceIdsDto resourceIdsDto = new ResourceIdsDto();
        resourceIdsDto.setIds(deletedIds);
        log.info("Audio recordings have been deleted");
        songClient.deleteByResourceId(deletedIds);
        return resourceIdsDto;
    }
}
