package dental.client.Service;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientResponseDto> findAll();

    ClientResponseDto getByID(Long id);

    ClientResponseDto create(ClientRequestDto dto);
    Optional<Client> update(ClientRequestDto dto , Long id);

    void delete(Long id);


}
