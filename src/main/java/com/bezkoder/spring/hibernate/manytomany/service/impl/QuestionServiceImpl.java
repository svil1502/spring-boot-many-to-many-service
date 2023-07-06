package com.bezkoder.spring.hibernate.manytomany.service.impl;

import com.bezkoder.spring.hibernate.manytomany.model.Question;
import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.QuestionRepository;
import com.bezkoder.spring.hibernate.manytomany.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findByName(String name) {
        return questionRepository.findByName(name);
    }
}
