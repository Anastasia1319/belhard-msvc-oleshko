package com.belhard.song.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Song")
@Data
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "resource_id")
    private Long resourceId;

    @Column(name = "name")
    private String name;

    @Column(name = "artist")
    private String artist;

    @Column(name = "album")
    private String album;

    @Column(name = "length")
    private String length;

    @Column(name = "year")
    private Integer year;
}
