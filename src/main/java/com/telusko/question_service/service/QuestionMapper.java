package com.telusko.question_service.service;

import com.telusko.question_service.dto.QuestionDTO;
import com.telusko.question_service.dto.QuestionWrapperDTO;
import com.telusko.question_service.model.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

	QuestionDTO toDTO(Question question);

	@Mapping(target = "rightAnswer", ignore = true)
	QuestionDTO toNoAnswerDTO(Question question);

	QuestionWrapperDTO toAnswerWrapperDTO(Question question);

	Question toEntity(QuestionDTO dto);

}
