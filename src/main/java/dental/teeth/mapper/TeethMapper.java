package dental.teeth.mapper;

import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import dental.teeth.entity.TeethEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TeethMapper {

    TeethEntity toEntity(TeethRequestDto dto);

    TeethResponseDto toDto(TeethEntity entity);

    TeethEntity update(@MappingTarget TeethEntity entity, TeethRequestDto dto);
}
