package com.belhard.song.web;

import com.belhard.song.service.SongService;
import com.belhard.song.service.dto.SongDto;
import com.belhard.song.service.dto.SongIdDto;
import com.belhard.song.service.dto.SongIdsDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;

    @PostMapping
    public SongIdDto upload(@Valid @RequestBody SongDto dto) {
        return songService.save(dto);
    }

    @GetMapping("/{id}")
    public SongDto download(@PathVariable long id) {
        return songService.download(id);
    }

    @DeleteMapping
    public SongIdsDto delete(@RequestParam("id") List<Long> ids) {
        return songService.deleteByResourceIds(ids);
    }


}
