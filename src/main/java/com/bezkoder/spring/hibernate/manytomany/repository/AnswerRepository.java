package com.bezkoder.spring.hibernate.manytomany.repository;

import java.util.List;

import com.bezkoder.spring.hibernate.manytomany.model.Answer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);

    @Transactional
    void deleteByQuestionId(long questionId);
}
