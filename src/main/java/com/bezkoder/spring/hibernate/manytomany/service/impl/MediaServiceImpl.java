package com.bezkoder.spring.hibernate.manytomany.service.impl;

import com.bezkoder.spring.hibernate.manytomany.model.Media;
import com.bezkoder.spring.hibernate.manytomany.repository.MediaRepository;
import com.bezkoder.spring.hibernate.manytomany.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

    @Override
    public void save(Media media) {
       mediaRepository.save(media);
    }

    @Override
    public Optional<Media> findById(Long id) {
        return  mediaRepository.findById(id);

    }
}
