package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dto.QuestionWrapperDTO;
import com.telusko.quiz_service.dto.QuizDTO;
import com.telusko.quiz_service.dto.ResponseDTO;
import com.telusko.quiz_service.feign.QuizInterface;
import com.telusko.quiz_service.model.Quiz;
import com.telusko.quiz_service.repository.QuizDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

	private static final Logger logger = LoggerFactory.getLogger(QuizService.class);

	private final QuizDAO quizDAO;

	private final QuizMapper quizMapper;

	QuizInterface quizInterface;

	@Autowired
	public QuizService(QuizDAO quizDAO, QuizMapper quizMapper, QuizInterface quizInterface) {
		this.quizDAO = quizDAO;
		this.quizMapper = quizMapper;
		this.quizInterface = quizInterface;
	}

	public ResponseEntity<String> createQuiz(QuizDTO quizDTO) {
		try {
			String category = quizDTO.getCategoryName();
			int numQ = quizDTO.getNumQuestions();
			String title = quizDTO.getQuizTitle();

			// Get questions from question microservice (via Feign + Eureka registry)
			List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();

			// save quiz in DB
			Quiz newQuiz = new Quiz();
			newQuiz.setQuizTitle(title);
			newQuiz.setQuestionIds(questionIds);
			quizDAO.save(newQuiz);
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to create new quiz.", e);
		}

		return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<QuestionWrapperDTO>> getQuizQuestions(Integer id) {
		try {
			Optional<Quiz> quiz = quizDAO.findById(id);
			List<Integer> questionIds;

			if (quiz.isPresent()) {
				questionIds = quiz.get().getQuestionIds();
			}
			else {
				throw new IllegalArgumentException("Quiz id does not exist");
			}

			List<QuestionWrapperDTO> quizQuestions = quizInterface.getQuestionsFromIds(questionIds).getBody();

			return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to get quiz questions", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Integer> submitQuiz(Integer id, List<ResponseDTO> responses) {
		try {
			logger.info("Scoring quiz responses...");
			Integer score = quizInterface.getScore(responses).getBody();
			return new ResponseEntity<>(score, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to submit quiz responses", e);
		}

		return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	}

}
