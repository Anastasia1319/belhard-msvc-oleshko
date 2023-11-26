package com.belhard.song.data;

import com.belhard.song.data.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {

    Optional<Song> findSongByResourceId(Long resourceId);

    @Transactional
    void deleteSongByResourceId(Long resourceId);
}
