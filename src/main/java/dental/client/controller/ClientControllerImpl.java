/**
 * Author: Anvar Olimov
 * User:user
 * Date:6/21/2024
 * Time:12:20 PM
 */


package dental.client.controller;

import dental.client.Service.ClientService;
import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientControllerImpl implements ClientController{

    private final ClientService service;
    @Override
    public List<ClientResponseDto> getAll() {
        return service.findAll();
    }

    @Override
    public ClientResponseDto getOne(Long id) {
        return service.getByID(id);
    }

    @Override
    public ClientResponseDto create(ClientRequestDto dto) {
        return service.create(dto);
    }

    @Override
    public ClientResponseDto update(ClientRequestDto dto, Long id) {
        return service.update(dto,id);
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }
}
