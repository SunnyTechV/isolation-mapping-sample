package com.techvg.covid.care.isolation.service.mapper;

import com.techvg.covid.care.isolation.domain.QuestionsOptions;
import com.techvg.covid.care.isolation.service.dto.QuestionsOptionsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link QuestionsOptions} and its DTO {@link QuestionsOptionsDTO}.
 */
@Mapper(componentModel = "spring", uses = { QuestionMapper.class })
public interface QuestionsOptionsMapper extends EntityMapper<QuestionsOptionsDTO, QuestionsOptions> {
    @Mapping(target = "question", source = "question", qualifiedByName = "question")
    QuestionsOptionsDTO toDto(QuestionsOptions s);
}
