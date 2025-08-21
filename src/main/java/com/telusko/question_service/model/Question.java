package com.telusko.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // works with SERIAL in Postgres
	private Integer id;

	private String questionTitle;

	private String option1;

	private String option2;

	private String option3;

	private String option4;

	private String rightAnswer;

	private String difficultyLevel;

	private String category;

}
