package dental.serviceCategory.mapper;


import dental.serviceCategory.dto.ServiceRequestDto;
import dental.serviceCategory.dto.ServiceRespondDto;
import dental.serviceCategory.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface ServiceMapper {

    ServiceEntity toEntity(ServiceRequestDto dto);

    ServiceRespondDto toServiceRespondDto(ServiceEntity service);


    ServiceEntity update (@MappingTarget ServiceEntity service , ServiceRequestDto dto);


}
