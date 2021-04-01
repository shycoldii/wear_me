package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.ReturnsHistory;
import server.model.SupplyHistory;

@Repository
public interface ReturnsHistoryRepository extends JpaRepository<ReturnsHistory,Long> {
}
