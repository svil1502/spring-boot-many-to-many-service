package com.bezkoder.spring.hibernate.manytomany.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.bezkoder.spring.hibernate.manytomany.converter.MapStructMapper;
import com.bezkoder.spring.hibernate.manytomany.dto.AnswerRequestDto;
import com.bezkoder.spring.hibernate.manytomany.dto.QuestionRequestDto;
import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.model.Question;
import com.bezkoder.spring.hibernate.manytomany.repository.AnswerRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.hibernate.manytomany.exception.ResourceNotFoundException;
import com.bezkoder.spring.hibernate.manytomany.model.Question;
import com.bezkoder.spring.hibernate.manytomany.model.Media;
import com.bezkoder.spring.hibernate.manytomany.repository.QuestionRepository;
import com.bezkoder.spring.hibernate.manytomany.repository.MediaRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class QuestionController {

    @Autowired
    private MapStructMapper mapstructMapper;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private QuestionRepository questionRepository;


    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    public QuestionController(MediaRepository mediaRepository, QuestionRepository questionRepository) {
        // this.mapstructMapper = mapstructMapper;
        this.mediaRepository = mediaRepository;
        this.questionRepository = questionRepository;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = new ArrayList<Question>();

        questionRepository.findAll().forEach(questions::add);

        if (questions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/medias/{mediaId}/questions")
    public ResponseEntity<List<Question>> getAllQuestionsByMediaId(@PathVariable(value = "mediaId") Long mediaId) {
        if (!mediaRepository.existsById(mediaId)) {
            throw new ResourceNotFoundException("Not found Media with id = " + mediaId);
        }

        List<Question> questions = questionRepository.findQuestionsByMediasId(mediaId);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestionsById(@PathVariable(value = "id") Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tag with id = " + id));

        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    @GetMapping("/questions/{questionId}/medias")
    public ResponseEntity<List<Media>> getAllMediasByQuestionId(@PathVariable(value = "questionId") Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Not found Question  with id = " + questionId);
        }

        List<Media> medias = mediaRepository.findMediasByQuestionsId(questionId);
        return new ResponseEntity<>(medias, HttpStatus.OK);
    }

    //https://ru.stackoverflow.com/questions/1114066/%D0%9A%D0%B0%D0%BA-%D0%BF%D1%80%D0%B8%D0%BD%D1%8F%D1%82%D1%8C-json-%D0%BE%D0%B1%D1%8A%D0%B5%D0%BA%D1%82-%D0%B2-rest-controller-%D0%B2-spring-boot
    @PostMapping("/medias/{mediaId}/questions")
    public ResponseEntity<Question> addQuestion(@PathVariable(value = "mediaId") Long mediaId, @RequestBody Question questionRequest) {
        Question question = mediaRepository.findById(mediaId).map(media -> {
            long questionId = questionRequest.getId();

            // tag is existed
            if (questionId != 0L) {
                Question _question = questionRepository.findById(questionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Not found Question with id = " + questionId));
                media.addQuestion(_question);
                mediaRepository.save(media);

                return _question;
            }

            // add and create new Tag
            media.addQuestion(questionRequest);
            return questionRepository.save(questionRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + mediaId));

        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @PostMapping("/medias/{mediaId}/questions/answers")
    public void addJsonQuestionAnwer(@PathVariable(value = "mediaId") Long mediaId, @RequestBody QuestionRequestDto body) throws IOException {

        if (body != null) {
            QuestionRequestDto json = body;

            System.out.println(json.getAnswers());
            //  System.out.println(json.getAnswers().content);
            questionRepository.save(mapstructMapper.questionRequestDtoToQuestion(json));
            // long questionId = questionRepository.findByName(body.getName()).getId();
            // List <AnswerRequestDto> newlist = json.getAnswers();
            //json.getAnswers().forEach(newAnswer->answerRepository.save(mapstructMapper.answerRequestDtoToAnswer(newAnswer.getContent())));
           Question newQuestion = questionRepository.findByName(body.getName());
            mediaRepository.findById(mediaId).map(media -> {
                media.addQuestion(newQuestion);

                return mediaRepository.save(media);
            }).orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + mediaId));



            for (AnswerRequestDto answer : json.getAnswers()) {
                //  AnswerRequestDto answer = new ObjectMapper().readValue((JsonParser) json.getAnswers(), AnswerRequestDto.class);
                System.out.println(answer.getContent());
                Answer newanswer = mapstructMapper.answerRequestDtoToAnswer(answer);
                newanswer.setQuestion(newQuestion);
                System.out.println(newanswer);
                answerRepository.save(newanswer);
            }


        }
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateTag(@PathVariable("id") long id, @RequestBody Question questionRequest) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QuestionId " + id + "not found"));

        question.setName(question.getName());

        return new ResponseEntity<>(questionRepository.save(question), HttpStatus.OK);
    }

    @DeleteMapping("/medias/{mediaId}/question/{questionId}")
    public ResponseEntity<HttpStatus> deleteQuestionFromMedia(@PathVariable(value = "mediaId") Long mediaId, @PathVariable(value = "questionId") Long questionId) {
        Media media = mediaRepository.findById(mediaId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Media with id = " + mediaId));

        media.removeQuestion(questionId);
        mediaRepository.save(media);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<HttpStatus> deleteQuestion(@PathVariable("id") long id) {
        questionRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
