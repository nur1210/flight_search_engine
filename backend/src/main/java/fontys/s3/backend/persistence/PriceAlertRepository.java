package fontys.s3.backend.persistence;

import fontys.s3.backend.persistence.entity.PriceAlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceAlertRepository extends JpaRepository<PriceAlertEntity, Long> {
}
