package com.techie.dto;

import com.techie.document.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.function.BooleanSupplier;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongListResponse {
    private Boolean success;
    private List<Song> songs;
}
