package com.techvg.covid.care.isolation.service.mapper;

import com.techvg.covid.care.isolation.domain.Assessment;
import com.techvg.covid.care.isolation.service.dto.AssessmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assessment} and its DTO {@link AssessmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { IsolationMapper.class })
public interface AssessmentMapper extends EntityMapper<AssessmentDTO, Assessment> {
    @Mapping(target = "isolation", source = "isolation", qualifiedByName = "id")
    AssessmentDTO toDto(Assessment s);

    @Named("assessmentDate")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assessmentDate", source = "assessmentDate")
    AssessmentDTO toDtoAssessmentDate(Assessment assessment);
}
