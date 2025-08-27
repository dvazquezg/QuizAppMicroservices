package com.telusko.quiz_service.controller;

import com.telusko.quiz_service.dto.QuestionWrapperDTO;
import com.telusko.quiz_service.dto.QuizDTO;
import com.telusko.quiz_service.dto.ResponseDTO;
import com.telusko.quiz_service.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

	private final QuizService quizService;

	@Autowired
	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDTO quizDTO) {
		return quizService.createQuiz(quizDTO);
	}

	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapperDTO>> getQuizQuestions(@PathVariable Integer id) {
		return quizService.getQuizQuestions(id);
	}

	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<ResponseDTO> responses) {
		return quizService.submitQuiz(id, responses);
	}

}
