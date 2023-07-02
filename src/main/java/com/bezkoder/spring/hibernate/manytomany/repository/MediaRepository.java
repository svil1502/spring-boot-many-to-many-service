package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.manytomany.model.Media;

public interface MediaRepository extends JpaRepository<Media, Long> {
  List<Media> findByPublished(boolean published);

  List<Media> findByTitleContaining(String title);
  
  List<Media> findMediasByQuestionsId(Long questionId);

  List<Media> findMediaWithAnswers();
}
