/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:11:19 AM
 */


package dental.client.service;

import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.client.entity.Client;
import dental.client.mapper.ClientMapper;
import dental.client.repository.ClientRepository;
import dental.exception.ResourceNotFoundException;
import dental.utils.DefaultResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {


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
                .orElse(null);
    }

    @Override
    public Client getClientByID(Long id) {
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isEmpty()) {
            throw new ResourceNotFoundException("Bunday mijoz yo`q");
        }
        return optionalClient.get();

    }

    @Override
    public Client getByClientName(String name) {
        return repository.findByName(name);
    }

    @Override
    public DefaultResponseDto create(ClientRequestDto dto) {
        Optional<Client> client = Optional.ofNullable(dto)
                .map(mapper::toClient)
                .map(repository::save);

        if (client.isEmpty()) {
            return DefaultResponseDto.builder()
                    .status(400)
                    .message("Mijoz yaratishda xatolik yuz berdi").build();

        }
        return DefaultResponseDto.builder()
                .status(200)
                .message(client.get().getName() + " ismli mijoz yaratildi").build();

    }

    @Override
    public ClientResponseDto update(ClientRequestDto dto, Long id) {
        return Optional.ofNullable(id)
                .flatMap(repository::findById)
                .map(entity -> mapper.update(entity, dto))
                .map(repository::save)
                .map(mapper::toDto).orElse(null);
    }

    @Override
    public Integer countClient() {
        List<Client> clients = repository.findAll();
        return clients.size();
    }

    @Override
    public List<ClientResponseDto> findDeleted() {
        return repository.findByDeletedTrue()
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public DefaultResponseDto delete(Long id) {
        Optional<Client> optionalClient = repository.findById(id);
        if (optionalClient.isEmpty()) {
            return DefaultResponseDto.builder()
                    .status(400)
                    .message("Bunday id raqamli mijoz yo`q")
                    .build();
        }
        Client client = optionalClient.get();
        client.setDeleted(true);
        repository.save(client);
        return DefaultResponseDto.builder()
                .status(200)
                .message("Mijoz o`chirildi")
                .build();
    }

    @Override
    public ClientResponseDto toDto(Client client) {
        return mapper.toDto(client);
    }
}
