package com.techie.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.mongodb.client.model.Collation;
import com.techie.document.Album;
import com.techie.document.User;
import com.techie.dto.AlbumListResponse;
import com.techie.dto.AlbumRequest;
import com.techie.exception.ResourceNotFoundException;
import com.techie.repository.AlbumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final Cloudinary cloudinary;
//    @Autowired
//    private MongoTemplate mongoTemplate;


    public Album addAlbum(AlbumRequest request) throws IOException {
        Map<String, Object> imageUploadResult = cloudinary.uploader().upload(request.getImageFile().getBytes(),
                ObjectUtils.asMap("resource_type", "image"));

        Album newAlbum = Album.builder()
                .name(request.getName())
                .desc(request.getDesc())
                .bgColour(request.getBgColour())
                .imageUrl(imageUploadResult.get("secure_url").toString())
                .build();
        return albumRepository.save(newAlbum);
    }


    public AlbumListResponse getAllAlbums() {
        return new AlbumListResponse(true, albumRepository.findAll());
    }


    public Boolean removeAlbum(String id) {
        Album exitsingAlbum = albumRepository.findById(id).orElseThrow(() -> new RuntimeException("Album not found"));
        albumRepository.delete(exitsingAlbum);
        return true;

    }

//    public Boolean removeAlbum(String id) {
//        Album existingAlbum = albumRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Album not found with ID: " + id));
//        albumRepository.delete(existingAlbum);
//        return true;
//    }



}
