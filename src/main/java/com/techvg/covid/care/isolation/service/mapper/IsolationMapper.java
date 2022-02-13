package com.techvg.covid.care.isolation.service.mapper;

import com.techvg.covid.care.isolation.domain.Isolation;
import com.techvg.covid.care.isolation.service.dto.IsolationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Isolation} and its DTO {@link IsolationDTO}.
 */
@Mapper(componentModel = "spring", uses = { IsolationDetailsMapper.class })
public interface IsolationMapper extends EntityMapper<IsolationDTO, Isolation> {
    @Mapping(target = "isolationDetails", source = "isolationDetails", qualifiedByName = "id")
    IsolationDTO toDto(Isolation s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IsolationDTO toDtoId(Isolation isolation);
}
