package com.bezkoder.spring.hibernate.manytomany.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Media;
import com.bezkoder.spring.hibernate.manytomany.repository.MediaRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class MediaController {

  @Autowired
  MediaRepository mediaRepository;

  @GetMapping("/medias")
  public ResponseEntity<List<Media>> getAllMedias(@RequestParam(required = false) String title) {
    List<Media> medias = new ArrayList<Media>();

    if (title == null)
      mediaRepository.findAll().forEach(medias::add);
    else
      mediaRepository.findByTitleContaining(title).forEach(medias::add);

    if (medias.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(medias, HttpStatus.OK);
  }

  @GetMapping("/medias/{id}")
  public ResponseEntity<Media> getMediaById(@PathVariable("id") long id) {
    Media media= mediaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + id));

    return new ResponseEntity<>(media, HttpStatus.OK);
  }

  @PostMapping("/medias")
  public ResponseEntity<Media> createMedia(@RequestBody Media media) {
    Media _media = mediaRepository.save(new Media(media.getTitle(), media.getDescription(), true));
    return new ResponseEntity<>(_media, HttpStatus.CREATED);
  }

  @PutMapping("/medias/{id}")
  public ResponseEntity<Media> updateMedia(@PathVariable("id") long id, @RequestBody Media media) {
    Media _media = mediaRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + id));

    _media.setTitle(media.getTitle());
    _media.setDescription(media.getDescription());
    _media.setPublished(media.isPublished());
    
    return new ResponseEntity<>(mediaRepository.save(_media), HttpStatus.OK);
  }

  @DeleteMapping("/medias/{id}")
  public ResponseEntity<HttpStatus> deleteMedia(@PathVariable("id") long id) {
    mediaRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/medias")
  public ResponseEntity<HttpStatus> deleteAllMedias() {
    mediaRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/medias/published")
  public ResponseEntity<List<Media>> findByPublished() {
    List<Media> medias = mediaRepository.findByPublished(true);

    if (medias.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    return new ResponseEntity<>(medias, HttpStatus.OK);
  }
}
