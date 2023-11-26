package com.belhard.song.service.impl;

import com.belhard.song.data.SongRepository;
import com.belhard.song.data.entity.Song;
import com.belhard.song.exceptions.NotFoundException;
import com.belhard.song.service.SongMapper;
import com.belhard.song.service.SongService;
import com.belhard.song.service.dto.SongDto;
import com.belhard.song.service.dto.SongIdDto;
import com.belhard.song.service.dto.SongIdsDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class SongServiceImpl implements SongService {

    private SongRepository songRepository;

    private SongMapper mapper;

    @Override
    public SongIdDto save(SongDto songDto) {
        log.info("Song save method was called");
        Song saved = songRepository.saveAndFlush(mapper.toEntity(songDto));
        log.info("Song was saved successfully");
        return mapper.toSongIdDto(saved.getId());
    }

    @Override
    public SongDto download(Long id) {
        log.info("Song download method was called");
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Song with id " + id + " not found"));
        log.info("Song with id {} was found", id);
        return mapper.toDto(song);
    }

    @Override
    public SongIdsDto deleteByResourceIds(List<Long> resourcesIds) {
        log.info("Song delete method was called");
        List<Long> deletedIds = new ArrayList<>();
        for (Long resourceId : resourcesIds) {
            if (songRepository.findSongByResourceId(resourceId).isEmpty()) {
                log.info("Song with resourceId {} not found", resourceId);
                continue;
            }
            songRepository.deleteSongByResourceId(resourceId);
            deletedIds.add(resourceId);
            log.info("Song with resourceId {} was deleted", resourceId);
        }
        SongIdsDto songIdsDto = new SongIdsDto();
        songIdsDto.setIds(deletedIds);
        log.info("Removal was successful");
        return songIdsDto;
    }
}
