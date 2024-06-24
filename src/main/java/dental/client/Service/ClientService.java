package dental.client.Service;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;

import java.util.List;

public interface ClientService {
    List<ClientResponseDto> findAll();

    ClientResponseDto getByID(Long id);

    ClientResponseDto create(ClientRequestDto dto);
    ClientResponseDto update(ClientRequestDto dto , Long id);

    void delete(Long id);


}
