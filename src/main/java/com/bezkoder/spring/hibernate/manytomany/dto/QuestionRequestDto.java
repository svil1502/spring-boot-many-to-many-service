package com.bezkoder.spring.hibernate.manytomany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionRequestDto {
    @JsonProperty("name")
    String name;
    @JsonProperty("answers")
    List<AnswerRequestDto> answers;
    public QuestionRequestDto(String name, List<AnswerRequestDto> answers) {
        this.name = name;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionRequestDto{" +
                "name='" + name + '\'' +
                ", answers=" + answers +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnswerRequestDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerRequestDto> answers) {
        this.answers = answers;
    }
}
