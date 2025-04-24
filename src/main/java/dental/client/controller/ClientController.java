package dental.client.controller;


import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.utils.DefaultResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/client")
public interface ClientController {


    @GetMapping
    List<ClientResponseDto> getAll();

    @GetMapping("/{id}")
    ClientResponseDto getOne(@PathVariable Long id);

    @GetMapping("/count-client")
    ResponseEntity<Integer> countClient();

    @GetMapping("/deleted")
    ResponseEntity<List<ClientResponseDto>> findDeleted();

    @PostMapping
    DefaultResponseDto create(@RequestBody ClientRequestDto dto);
    @PutMapping("/{id}")
    ClientResponseDto update(@RequestBody ClientRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/{id}")
    DefaultResponseDto delete(@PathVariable Long id);

}
