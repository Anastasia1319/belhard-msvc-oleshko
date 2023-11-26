package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.client.SongClient;
import com.belhard.resourceservice.client.StorageS3Client;
import com.belhard.resourceservice.data.ResourceRepository;
import com.belhard.resourceservice.data.entity.Resource;
import com.belhard.resourceservice.exceptions.NotFoundException;
import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.ResourceMapper;
import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private final ResourceMapper mapper;

    private final SongClient songClient;

    private final MetaDataService metaDataService;

    private final StorageS3Client s3Client;

    @Value(value = "${clients.s3.bucket_name}")
    private String bucketName;

    @Override
    public ResourceIdDto upload(byte[] audio) {
        log.info("Audio loading method was called");
        String location = getKey();
        s3Client.upload(audio, location);
        Resource uploaded = resourceRepository.saveAndFlush(mapper.toEntity(location));
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
        return s3Client.download(resource.getLocation());
    }

    @Override
    public ResourceIdsDto delete(List<Long> ids) {
        log.info("Audio delete method was called");
        List<Long> deletedIds = new ArrayList<>();
        for (Long id : ids) {
            Optional<Resource> optionalResource = resourceRepository.findById(id);
            if (optionalResource.isEmpty()) {
                log.info("Audio with id {} not found", id);
                continue;
            }
            Resource resource = optionalResource.get();
            s3Client.delete(resource.getLocation());
            log.info("Audio with id {} was deleted from S3", id);
            songClient.deleteByResourceId(List.of(resource.getId()));
            log.info("MetaData with resourceId {} was deleted", id);
            resourceRepository.deleteById(id);
            deletedIds.add(id);
            log.info("Audio with id {} was deleted", id);
        }
        ResourceIdsDto resourceIdsDto = new ResourceIdsDto();
        resourceIdsDto.setIds(deletedIds);
        log.info("Audio recordings have been deleted");
        return resourceIdsDto;
    }

    @Override
    public List<byte[]> downloadAll() {
        return s3Client.downloadAll();
    }

    private String getKey() {
        UUID key = UUID.randomUUID();
        return bucketName + key;
    }
}
