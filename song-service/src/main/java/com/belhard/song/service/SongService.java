package com.belhard.song.service;

import com.belhard.song.service.dto.SongDto;
import com.belhard.song.service.dto.SongIdDto;
import com.belhard.song.service.dto.SongIdsDto;

import java.util.List;

public interface SongService {

    SongIdDto save(SongDto songDto);

    SongDto download(Long id);

    SongIdsDto deleteByResourceIds(List<Long> ids);

}
