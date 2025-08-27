package com.telusko.quiz_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuizDTO {

	private String categoryName;

	private Integer numQuestions;

	private String quizTitle;

}
