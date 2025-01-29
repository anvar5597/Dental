package dental.epms.repository;

import dental.epms.entity.ERole;
import dental.epms.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long> {

    Optional<Employees> findByLogin(String username);

    Optional<Employees> findByEmail(String email);

    Boolean existsByLogin(String username);

    List<Employees> findAllByRole(ERole role);

    Boolean existsByEmail(String email);

    Optional<Employees> findByPhoneNumber(String phone);

//    List<Employees> findAllByRoleOrderByRegisteredAtDesc(ERole role);
//    List<Employees> findAllByRoleOrderByRegisteredAtDesc(ERole role, Pageable pageable);

}
