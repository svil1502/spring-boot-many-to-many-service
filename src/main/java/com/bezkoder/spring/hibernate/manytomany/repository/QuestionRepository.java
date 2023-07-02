package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bezkoder.spring.hibernate.manytomany.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
  List<Question> findQuestionsByMediasId(Long mediaId);
  Question findByName(String name);


}
