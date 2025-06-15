/**
 * Author: Anvar Olimov
 * User:user
 * Date:2/3/2025
 * Time:5:34 PM
 */


package dental.client.analys.repository;

import dental.client.analys.entity.ClientXRayEntity;
import dental.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ClientXRayRepository extends JpaRepository<ClientXRayEntity, Long> {
    List<ClientXRayEntity> findByClient(Client client);

    List<ClientXRayEntity> findByClientId(Long id);
}
