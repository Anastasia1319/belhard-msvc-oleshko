package com.belhard.resourceservice.web;

import com.belhard.resourceservice.service.ResourceService;
import com.belhard.resourceservice.service.dto.ResourceIdDto;
import com.belhard.resourceservice.service.dto.ResourceIdsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping(consumes = "audio/mpeg")
    public ResourceIdDto upload(InputStream data) throws IOException {
        return resourceService.upload(data.readAllBytes());
    }

    @GetMapping("/{id}")
    public byte[] download(@PathVariable long id) {
        return resourceService.download(id);
    }

    @DeleteMapping
    public ResourceIdsDto delete(@RequestParam("id") List<Long> ids) {
        return resourceService.delete(ids);
    }

    @GetMapping("/all")
    public List<byte[]> downloadAll() {
        return resourceService.downloadAll();
    }
}
