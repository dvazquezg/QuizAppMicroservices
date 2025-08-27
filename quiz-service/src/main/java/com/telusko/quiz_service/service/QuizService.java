package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dto.QuestionWrapperDTO;
import com.telusko.quiz_service.dto.QuizDTO;
import com.telusko.quiz_service.dto.ResponseDTO;
import com.telusko.quiz_service.model.Question;
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

	@Autowired
	public QuizService(QuizDAO quizDAO, QuizMapper quizMapper) {
		this.quizDAO = quizDAO;
		this.quizMapper = quizMapper;
	}

	public ResponseEntity<String> createQuiz(QuizDTO quizDTO) {
		try {
			List<Integer> quizQuestions = new ArrayList<>();

			// TODO: call to question service to get questions

			// create quiz
			List<Question> questionList = new ArrayList<>();
			Quiz newQuiz = new Quiz();
			newQuiz.setQuizTitle(quizDTO.getQuizTitle());
			newQuiz.setQuestions(questionList);
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
			List<Question> questions;
			List<QuestionWrapperDTO> quizQuestions = new ArrayList<>();
			// TODO: get questions from questions service

			if (quiz.isPresent()) {
				questions = quiz.get().getQuestions();
			}
			else {
				throw new IllegalArgumentException("Quiz id does not exist");
			}

			return new ResponseEntity<>(quizQuestions, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to get quiz questions", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Integer> submitQuiz(Integer id, List<ResponseDTO> responses) {
		try {
			Integer score = 0; // TODO: get score from question service

			return new ResponseEntity<>(score, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to submit quiz responses", e);
		}

		return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	}

}
