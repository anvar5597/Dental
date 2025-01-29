package dental.service_category.mapper;


import dental.service_category.dto.ServiceRequestDto;
import dental.service_category.dto.ServiceRespondDto;
import dental.service_category.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING)
public interface ServiceMapper {

    ServiceEntity toEntity(ServiceRequestDto dto);

    ServiceRespondDto toServiceRespondDto(ServiceEntity service);


    ServiceEntity update (@MappingTarget ServiceEntity service , ServiceRequestDto dto);


}
