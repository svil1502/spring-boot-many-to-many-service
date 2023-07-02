package com.bezkoder.spring.hibernate.manytomany.controller;

import java.util.List;

import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.QuestionRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AnswerController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @GetMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<Answer>> getAllAnswersByQuestionId(@PathVariable(value = "questionId") Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Not found Question with id = " + questionId);
        }

        List<Answer> answers = answerRepository.findByQuestionId(questionId);
        return new ResponseEntity<>(answers, HttpStatus.OK);
    }

    @GetMapping("/answers/{id}")
    public ResponseEntity<Answer> getAnswersByQuestionId(@PathVariable(value = "id") Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Answer with id = " + id));

        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    @PostMapping("/questions/{questionId}/answers")
    public ResponseEntity<Answer> createAnswer(@PathVariable(value = "questionId") Long questionId,
                                                 @RequestBody Answer answerRequest) {
        Answer answer = questionRepository.findById(questionId).map(question -> {
            answerRequest.setQuestion(question);
            return answerRepository.save(answerRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + questionId));

        return new ResponseEntity<>(answer, HttpStatus.CREATED);
    }

    @PutMapping("/answers/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable("id") long id, @RequestBody Answer answerRequest) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnswerId " + id + "not found"));

        answer.setContent(answerRequest.getContent());

        return new ResponseEntity<>(answerRepository.save(answer), HttpStatus.OK);
    }

    @DeleteMapping("/answers/{id}")
    public ResponseEntity<HttpStatus> deleteAnswer(@PathVariable("id") long id) {
        answerRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/questions/{questionId}/answers")
    public ResponseEntity<List<Answer>> deleteAllanswerssOfQuestion(@PathVariable(value = "questionId") Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Not found Question with id = " + questionId);
        }

        answerRepository.deleteByQuestionId(questionId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}