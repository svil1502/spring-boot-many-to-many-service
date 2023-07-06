package com.bezkoder.spring.hibernate.manytomany.service;

import com.bezkoder.spring.hibernate.manytomany.model.Question;

public interface QuestionService {

    void save(Question question);
    Question findByName(String name);
}
