package dental.client.repository;

import dental.client.entity.Client;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);
    @NotNull Optional<Client> findById(@NotNull Long id);

    List<Client> findByDeletedTrue();
}
