package com.telusko.question_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.telusko.question_service.dto.QuestionDTO;
import com.telusko.question_service.dto.QuestionWrapperDTO;
import com.telusko.question_service.dto.ResponseDTO;
import com.telusko.question_service.model.Question;
import com.telusko.question_service.repository.QuestionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

	private final QuestionDAO questionDAO;

	private final QuestionMapper questionMapper;

	private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

	@Autowired
	public QuestionService(QuestionDAO questionDAO, QuestionMapper questionMapper) {
		this.questionDAO = questionDAO;
		this.questionMapper = questionMapper;
	}

	public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
		try {
			List<QuestionDTO> questions = questionDAO.findAll().stream().map(questionMapper::toDTO).toList();
			return new ResponseEntity<>(questions, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to get all questions", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<QuestionDTO>> getQuestionsByCategory(String category) {
		try {
			List<QuestionDTO> questions = questionDAO.findByCategory(category)
				.stream()
				.map(questionMapper::toDTO)
				.toList();
			return new ResponseEntity<>(questions, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to get questions by category", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<String> addQuestion(QuestionDTO question) {
		try {
			questionDAO.save(questionMapper.toEntity(question));
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to add new question", e);
		}

		return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
		try {
			List<Integer> questionIdList = questionDAO.findRandomQuestionIdsByCategory(category, numQ);
			return new ResponseEntity<>(questionIdList, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to generate question ids list.", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<QuestionWrapperDTO>> getQuestionsFromIds(List<Integer> questionIds) {
		try {
			List<QuestionWrapperDTO> questionList = questionDAO.findAllById(questionIds)
				.stream()
				.map(questionMapper::toQuestionWrapperDTO)
				.toList();
			return new ResponseEntity<>(questionList, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to fetch questions.", e);
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<Integer> getScore(List<ResponseDTO> responses) {
		try {
			List<Integer> questionIds = responses.stream().map(ResponseDTO::getId).toList();
			Map<Integer, String> answers = questionDAO.findAllById(questionIds)
				.stream()
				.collect(Collectors.toMap(Question::getId, Question::getRightAnswer));
			Integer score = 0;
			// grade quiz
			for (ResponseDTO responseDTO : responses) {
				Integer questionId = responseDTO.getId();
				String response = responseDTO.getResponse();
				String answer = answers.get(questionId);
				if (response.equals(answer)) {
					score++;
				}
			}

			return new ResponseEntity<>(score, HttpStatus.OK);
		}
		catch (Exception e) {
			logger.error("An error occurred while trying to grade responses.", e);
		}

		return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
	}

}
