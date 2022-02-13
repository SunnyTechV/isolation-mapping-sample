package com.techvg.covid.care.isolation.service.mapper;

import com.techvg.covid.care.isolation.domain.IsolationDetails;
import com.techvg.covid.care.isolation.service.dto.IsolationDetailsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link IsolationDetails} and its DTO {@link IsolationDetailsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface IsolationDetailsMapper extends EntityMapper<IsolationDetailsDTO, IsolationDetails> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IsolationDetailsDTO toDtoId(IsolationDetails isolationDetails);
}
