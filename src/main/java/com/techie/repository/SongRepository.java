package com.techie.repository;

import com.techie.document.Song;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SongRepository extends MongoRepository<Song, String> {
}
