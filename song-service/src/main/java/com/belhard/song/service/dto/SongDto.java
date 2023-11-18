package com.belhard.song.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class SongDto {

    private Long id;

    private String name;

    private String artist;

    private String album;

    @Pattern(regexp = "(\\d+:)?[0-5]?\\d:[0-5]\\d")
    private String length;

    @NotNull
    private Long resourceId;
    private Integer year;
}
