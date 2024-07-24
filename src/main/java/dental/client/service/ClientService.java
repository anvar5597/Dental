package dental.client.service;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.entity.Client;
import dental.utils.DefaultResponseDto;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> findAll();

    ClientResponseDto getByID(Long id);

    Client getClientByID(Long id);

    ClientResponseDto create(ClientRequestDto dto);
    ClientResponseDto update(ClientRequestDto dto , Long id);

    DefaultResponseDto delete(Long id);

    ClientResponseDto toDto(Client client);


}
