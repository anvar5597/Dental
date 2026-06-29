package dental.client.controller;


import dental.client.dto.ClientRequestDto;
import dental.client.dto.ClientResponseDto;
import dental.utils.DefaultResponseDto;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/client")
public interface ClientController {


    @GetMapping
    ResponseEntity<List<ClientResponseDto>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ClientResponseDto> getOne(@PathVariable Long id);

    @GetMapping("/count-client")
    ResponseEntity<Integer> countClient();

    @GetMapping("/deleted")
    ResponseEntity<List<ClientResponseDto>> findDeleted();

    @PostMapping
    DefaultResponseDto create(@RequestBody ClientRequestDto dto);
    @PutMapping("/{id}")
    ClientResponseDto update(@RequestBody ClientRequestDto dto, @PathVariable Long id);

    @DeleteMapping("/passive-delete/{id}")
    ResponseEntity<String> passiveDelete(@PathVariable Long id);

    @Transactional
    @DeleteMapping("/{id}")
    DefaultResponseDto delete(@PathVariable Long id);

}
