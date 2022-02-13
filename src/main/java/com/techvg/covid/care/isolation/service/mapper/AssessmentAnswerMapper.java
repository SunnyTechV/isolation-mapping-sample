package com.techvg.covid.care.isolation.service.mapper;

import com.techvg.covid.care.isolation.domain.AssessmentAnswer;
import com.techvg.covid.care.isolation.service.dto.AssessmentAnswerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssessmentAnswer} and its DTO {@link AssessmentAnswerDTO}.
 */
@Mapper(componentModel = "spring", uses = { AssessmentMapper.class, QuestionMapper.class })
public interface AssessmentAnswerMapper extends EntityMapper<AssessmentAnswerDTO, AssessmentAnswer> {
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentDate")
    @Mapping(target = "question", source = "question", qualifiedByName = "id")
    AssessmentAnswerDTO toDto(AssessmentAnswer s);
}
