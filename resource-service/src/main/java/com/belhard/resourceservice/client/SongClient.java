package com.belhard.resourceservice.client;

import com.belhard.resourceservice.service.dto.MetaDataDto;

import java.util.List;

public interface SongClient {
    void create (MetaDataDto metaDataDto);
    void deleteByResourceId(List<Long> resourceIds);
}
