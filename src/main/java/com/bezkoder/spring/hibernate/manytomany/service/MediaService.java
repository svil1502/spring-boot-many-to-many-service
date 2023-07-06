package com.bezkoder.spring.hibernate.manytomany.service;

import com.bezkoder.spring.hibernate.manytomany.model.Media;

import java.util.Optional;

public interface MediaService {
    void save(Media media);
   Optional<Media> findById(Long mediaId);
}
