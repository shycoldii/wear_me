package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.SupplyHistory;
/**
 * Интерфейс JpaRepository для работы с историей поставок
 */
@Repository
public interface SupplyHistoryRepository extends JpaRepository<SupplyHistory,Long> {
}
