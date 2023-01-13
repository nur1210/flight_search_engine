package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriceAlertRepository extends JpaRepository<PriceAlertEntity, Long> {
    @Query("SELECT pa FROM PriceAlert pa JOIN pa.users u WHERE u.id = :userId")
    List<PriceAlertEntity> findByUserId(long userId);
}
