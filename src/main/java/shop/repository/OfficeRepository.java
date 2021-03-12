package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {
   Office findByAddressId_City(String city);
}
