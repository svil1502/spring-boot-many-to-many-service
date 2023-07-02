package com.bezkoder.spring.hibernate.manytomany.converter;

import com.bezkoder.spring.hibernate.manytomany.dto.AnswerRequestDto;
import com.bezkoder.spring.hibernate.manytomany.dto.QuestionRequestDto;
import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import com.bezkoder.spring.hibernate.manytomany.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    MapStructMapper INSTANCE = Mappers.getMapper(MapStructMapper.class);
    QuestionRequestDto questionToQuestionRequestDto(Question question);
  //  AnswerRequestDto answerToAnswerRequestDto(Answer answer);
    Question questionRequestDtoToQuestion(QuestionRequestDto questionRequestDto);
    Answer answerRequestDtoToAnswer(AnswerRequestDto answerRequestDto);

}
