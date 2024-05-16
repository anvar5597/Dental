package dental.epms.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import dental.epms.entity.EmployeeEntity;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeeApi {

    @Override
    public List<?> getAll() {
        return null;
    }

    @Override
    public void create(EmployeeEntity entity) {

    }
}
