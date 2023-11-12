package com.belhard.resourceservice.service;

import com.belhard.resourceservice.service.dto.MetaDataDto;

public interface MetaDataService {

    MetaDataDto getMetaData(byte[] data);
}
