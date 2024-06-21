package dental.teeth.service;

import dental.teeth.dto.TeethRequestDto;
import dental.teeth.dto.TeethResponseDto;
import dental.teeth.entity.TeethEntity;

import java.util.List;

public interface TeethService {

    List<TeethResponseDto> findAll();

    TeethResponseDto getById(Long id);

    Long create(TeethRequestDto dto);

    TeethEntity update(TeethRequestDto dto, Long id);

    void delete(Long id);
}
