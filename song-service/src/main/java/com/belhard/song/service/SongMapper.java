package com.belhard.song.service;


import com.belhard.song.data.entity.Song;
import com.belhard.song.service.dto.SongDto;
import com.belhard.song.service.dto.SongIdDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SongMapper {
    Song toEntity(SongDto dto);
    SongDto toDto(Song song);

    default SongIdDto toSongIdDto(Long id) {
        SongIdDto songIdDto = new SongIdDto();
        songIdDto.setId(id);
        return songIdDto;
    }
}
