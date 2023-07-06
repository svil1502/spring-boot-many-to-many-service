package com.bezkoder.spring.hibernate.manytomany.service;

import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.model.Media;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AnswerService {

    void save(Answer answer);
    List<Answer> findByQuestionId(Long questionId);

    void deleteByQuestionId(long questionId);
}
