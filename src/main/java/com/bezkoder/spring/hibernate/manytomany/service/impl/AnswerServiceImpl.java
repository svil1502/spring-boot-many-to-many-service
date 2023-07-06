package com.bezkoder.spring.hibernate.manytomany.service.impl;

import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.bezkoder.spring.hibernate.manytomany.service.AnswerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void save(Answer answer) {
        answerRepository.save(answer);
    }

    public List<Answer> findByQuestionId(Long questionId){
        return answerRepository.findByQuestionId(questionId);
    }

    @Transactional
    public void deleteByQuestionId(long questionId){
        answerRepository.deleteByQuestionId(questionId);

    }
}
