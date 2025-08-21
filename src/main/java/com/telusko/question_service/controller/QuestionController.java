package com.telusko.question_service.controller;

import java.util.List;

import com.telusko.question_service.dto.QuestionDTO;
import com.telusko.question_service.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
public class QuestionController {

	private final QuestionService questionService;

	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	@GetMapping("allQuestions")
	public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
		return questionService.getAllQuestions();
	}

	@GetMapping("category/{category}")
	public ResponseEntity<List<QuestionDTO>> getQuestionsByCategory(@PathVariable String category) {
		return questionService.getQuestionsByCategory(category);
	}

	@PostMapping("add")
	public ResponseEntity<String> addQuestion(@RequestBody QuestionDTO question) {
		return questionService.addQuestion(question);
	}

	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category,
			@RequestParam Integer numQ) {
		return questionService.getQuestionsForQuiz(category, numQ);
	}

}
