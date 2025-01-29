package dental.payment.repository;

import dental.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EnableJpaRepositories
public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    List<PaymentEntity> findAllByPaidDateBetween(LocalDate from, LocalDate to);

    List<PaymentEntity> findAllByPaidDate(LocalDate date);

    List<PaymentEntity> findAllByPatientHistoryEntityClient_Id(Long id);

    List<PaymentEntity> findAllByPatientHistoryEntityEmployees_Id(Long id);
}
