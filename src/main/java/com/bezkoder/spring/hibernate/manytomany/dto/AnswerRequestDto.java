package com.bezkoder.spring.hibernate.manytomany.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

//@JsonIgnoreProperties(ignoreUnknown = true)
public class AnswerRequestDto {
    @JsonProperty("content")
    String content;

    @Override
    public String toString() {
        return "AnswerRequestDto{" +
                "content='" + content + '\'' +
                '}';
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public AnswerRequestDto(String content) {
        this.content = content;
    }
}
