package dental.client.mapper;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ClientMapper {
    Client toClient(ClientRequestDto dto);

    ClientResponseDto toDto(Client client);

    Client update (@MappingTarget Client client, ClientRequestDto dto);
}
