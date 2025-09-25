package com.techie.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.techie.document.Song;
import com.techie.dto.SongListResponse;
import com.techie.dto.SongRequest;
import com.techie.repository.AlbumRepository;
import com.techie.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final Cloudinary cloudinary;

    public Song addSong(SongRequest request) throws IOException {

        Map<String, Object> audioUploadResult = cloudinary.uploader().upload(request.getAudioFile().getBytes(),
                ObjectUtils.asMap("resource_type", "video"));

        Map<String, Object> imageUploadResult = cloudinary.uploader().upload(request.getImageFile().getBytes(),
                ObjectUtils.asMap("resource_type", "image"));

        Double durationInSeconds = (Double) audioUploadResult.get("duration");
        String duration = formatDuration(durationInSeconds);

        Song newSong = Song.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .album(request.getAlbum())
                .image(imageUploadResult.get("secure_url").toString())
                .file(audioUploadResult.get("secure_url").toString())
                .duration(duration)
                .build();

        log.info("New song added: {}", newSong);

        return songRepository.save(newSong);
    }

    private String formatDuration(Double durationInSeconds) {
        if (durationInSeconds == null) {
            return "00:00";
        }
        int minutes = (int) (durationInSeconds / 60);
        int seconds = (int) (durationInSeconds % 60);
        return String.format("%d:%02d", minutes, seconds);
    }

    public SongListResponse getAllSongs() {
        return new SongListResponse(true, songRepository.findAll());
    }

    public Boolean removeSong(String id) {
        Song exitingSong = songRepository.findById(id).orElseThrow(() -> new RuntimeException("song not found"));
        songRepository.delete(exitingSong);
        return true;

    }

}
