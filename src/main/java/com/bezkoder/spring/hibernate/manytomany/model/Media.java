package com.bezkoder.spring.hibernate.manytomany.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "media")
public class Media {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "published")
  private boolean published;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "media_question", joinColumns = { @JoinColumn(name = "media_id") }, inverseJoinColumns = {
      @JoinColumn(name = "question_id") })
  private Set<Question> questions = new HashSet<>();

  public Media() {

  }

  public Media(String title, String description, boolean published) {
    this.title = title;
    this.description = description;
    this.published = published;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isPublished() {
    return published;
  }

  public void setPublished(boolean isPublished) {
    this.published = isPublished;
  }

  public Set<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<Question> questions) {
    this.questions = questions;
  }

  public void addQuestion(Question question) {
    this.questions.add(question);
    question.getMedias().add(this);
  }

  public void removeQuestion(long questionId) {
    Question question = this.questions.stream().filter(t -> t.getId() == questionId).findFirst().orElse(null);
    if (question != null) {
      this.questions.remove(question);
      question.getMedias().remove(this);
    }
  }

  @Override
  public String toString() {
    return "Media [id=" + id + ", title=" + title + ", desc=" + description + ", published=" + published + "]";
  }

}
