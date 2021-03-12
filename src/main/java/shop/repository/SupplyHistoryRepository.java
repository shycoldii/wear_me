package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.SupplyHistory;

@Repository
public interface SupplyHistoryRepository extends JpaRepository<SupplyHistory,Long> {
}
