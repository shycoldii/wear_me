package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.ReturnsHistory;
import wearme.server.model.SupplyHistory;
/**
 * Интерфейс JpaRepository для работы с историей возврата
 */
@Repository
public interface ReturnsHistoryRepository extends JpaRepository<ReturnsHistory,Long> {
}
