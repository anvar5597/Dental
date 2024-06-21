package dental.epms.repository;

import dental.epms.dto.ApiType;
import dental.epms.entity.Employees;
import dental.epms.entity.JwtTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface JwtTokenRepo extends JpaRepository<JwtTokenEntity, Long> {
    Optional<JwtTokenEntity> findByToken(String token);

    @Modifying
    void deleteByUser(Employees user);

    @Modifying
    void deleteByApiTypeAndUser(ApiType apiType, Employees employees);
}
