package dental.teeth.teethRepository;

import dental.teeth.entity.TeethEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeethRepository extends JpaRepository<TeethEntity, Long> {
}
