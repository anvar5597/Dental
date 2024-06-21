/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:11:19 AM
 */


package dental.client.Service;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.entity.Client;
import dental.client.mapper.ClientMapper;
import dental.client.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{


    private final ClientRepository repository;

    private final ClientMapper mapper;
    @Override
    public List<ClientResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public ClientResponseDto getByID(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .get();
    }

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {
        return Optional.ofNullable(dto)
                .map(mapper::toClient)
                 .map(repository::save)
                .map(mapper::toDto)
                .get();

    }

    @Override
    public Optional<Client> update(ClientRequestDto dto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
