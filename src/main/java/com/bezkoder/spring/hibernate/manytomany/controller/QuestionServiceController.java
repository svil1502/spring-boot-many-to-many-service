package com.bezkoder.spring.hibernate.manytomany.controller;

import com.bezkoder.spring.hibernate.manytomany.converter.MapStructMapper;
import com.bezkoder.spring.hibernate.manytomany.dto.AnswerRequestDto;
import com.bezkoder.spring.hibernate.manytomany.dto.QuestionRequestDto;
import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.model.Media;
import com.bezkoder.spring.hibernate.manytomany.model.Question;
import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.MediaRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.QuestionRepository;
import com.bezkoder.spring.hibernate.manytomany.service.AnswerService;
import com.bezkoder.spring.hibernate.manytomany.service.MediaService;
import com.bezkoder.spring.hibernate.manytomany.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QuestionServiceController {

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private QuestionService questionService;


    @Autowired
    private AnswerService answerService;



    @PostMapping("/medias2/{mediaId}/questions2/answers")
    public void addJsonQuestionAnswer(@PathVariable(value = "mediaId") Long mediaId, @RequestBody QuestionRequestDto body) throws IOException {

        if (body != null) {
            QuestionRequestDto json = body;

            System.out.println(json.getAnswers());

            questionService.save(mapstructMapper.questionRequestDtoToQuestion(json));
             Question newQuestion = questionService.findByName(body.getName());
            mediaService.findById(mediaId).map(media -> {
                media.addQuestion(newQuestion);
                mediaService.save(media);
                return newQuestion;


            }).orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + mediaId));

            for (AnswerRequestDto answer : json.getAnswers()) {
                Answer newanswer = mapstructMapper.answerRequestDtoToAnswer(answer);
                newanswer.setQuestion(newQuestion);
                System.out.println(newanswer);
                answerService.save(newanswer);
            }


        }
    }



    @DeleteMapping("/medias2/{mediaId}/question2/{questionId}")
    public ResponseEntity<HttpStatus> deleteQuestionFromMedia(@PathVariable(value = "mediaId") Long mediaId, @PathVariable(value = "questionId") Long questionId) {
        Media media = mediaService.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + mediaId));

        media.removeQuestion(questionId);
        mediaService.save(media);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
