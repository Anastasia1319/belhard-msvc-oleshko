package com.belhard.resourceservice.service.impl;

import com.belhard.resourceservice.service.MetaDataService;
import com.belhard.resourceservice.service.dto.MetaDataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;

@Service
@Slf4j
public class MetaDataServiceImpl implements MetaDataService {
    private static final String DURATION_FORMAT = "%02d:%02d:%02d";

    @Override
    public MetaDataDto getMetaData(byte[] data) {
        try {
            log.info("Method getMetaData was called");
            Metadata metadata = new Metadata();
            Mp3Parser mp3Parser = new Mp3Parser();
            mp3Parser.parse(new ByteArrayInputStream(data), new BodyContentHandler(), metadata);
            log.info("Data were parsed");
            return buildMetaDataDto(metadata);
        } catch (IOException | SAXException | TikaException e) {
            throw new RuntimeException(e);
        }
    }

    private MetaDataDto buildMetaDataDto(Metadata metadata) {
        MetaDataDto metaDataDto = new MetaDataDto();
        metaDataDto.setName(metadata.get("dc:title"));
        metaDataDto.setArtist(metadata.get("xmpDM:albumArtist"));
        metaDataDto.setAlbum(metadata.get("xmpDM:album"));
        metaDataDto.setLength(getDuration(metadata));
        metaDataDto.setYear(getYear(metadata));
        log.info("MetaDataDto was created");
        return metaDataDto;
    }

    private Integer getYear(Metadata metadata) {
        String raw = metadata.get("xmpDM:releaseDate");
        if (raw == null) {
            return null;
        }
        return Integer.valueOf(raw);
    }

    private String getDuration(Metadata metadata) {
        String raw = metadata.get("xmpDM:duration");
        if (raw == null) {
            return null;
        }
        double seconds = Double.parseDouble(raw);
        Duration duration = Duration.ofSeconds((long) seconds);
        return String.format(DURATION_FORMAT, duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
    }
}
