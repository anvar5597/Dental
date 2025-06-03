package dental.teeth.controller;

import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/teeth")
public interface TeethApi {
    @GetMapping
    List<TeethResponseDto> getAll();

    @GetMapping("/{id}")
    TeethResponseDto getOne(@PathVariable Long id);

    @PostMapping
    Long create(@RequestBody TeethRequestDto dto);

    @PutMapping("/{id}")
    void update(@RequestBody TeethRequestDto dto, @PathVariable Long id);


    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id);

    @DeleteMapping("/passive-delete/{id}")
    ResponseEntity<String> activeDelete(@PathVariable Long id);
}