package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.ReturnsHistory;
import server.model.SupplyHistory;
/**
 * Интерфейс JpaRepository для работы с историей возврата
 */
@Repository
public interface ReturnsHistoryRepository extends JpaRepository<ReturnsHistory,Long> {
}
