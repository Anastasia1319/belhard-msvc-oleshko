package com.belhard.song.service.dto;

import lombok.Data;

@Data
public class SongDto {
    private String name;
    private String artist;
    private String album;
    private String length;
    private Long resourceId;
    private Integer year;
}
