package dental.teeth.controller;

import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import dental.teeth.service.TeethService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeethController implements TeethApi {

    private final TeethService service;

    @Override
    public List<TeethResponseDto> getAll() {
        return service.findAll();
    }

    @Override
    public TeethResponseDto getOne(Long id) {
        return service.getById(id);
    }

    @Override
    public Long create(TeethRequestDto dto) {
        return service.create(dto);
    }

    @Override
    public void update(TeethRequestDto dto, Long id) {
        service.update(dto, id );
    }

    @Override
    public void delete(Long id) {
        service.delete(id);
    }

    @Override
    public ResponseEntity<String> activeDelete(Long id) {
        return ResponseEntity.ok(service.activeDelete(id));
    }
}
