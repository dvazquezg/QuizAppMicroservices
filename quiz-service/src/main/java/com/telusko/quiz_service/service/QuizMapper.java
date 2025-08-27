package com.telusko.quiz_service.service;

import com.telusko.quiz_service.dto.QuizDTO;
import com.telusko.quiz_service.model.Quiz;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuizMapper {

	QuizDTO toDTO(Quiz quiz);

	Quiz toEntity(QuizDTO quizDTO);

}
