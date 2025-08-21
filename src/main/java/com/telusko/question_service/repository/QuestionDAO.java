package com.telusko.question_service.repository;

import java.util.List;

import com.telusko.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {

	List<Question> findByCategory(String category);

	@Query(value = "SELECT * FROM question AS q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ",
			nativeQuery = true)
	List<Question> findRandomQuestionsByCategory(String category, Integer numQ);

	@Query(value = "SELECT q.id FROM question AS q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQ",
			nativeQuery = true)
	List<Integer> findRandomQuestionIdsByCategory(String category, Integer numQ);

}
