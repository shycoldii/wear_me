package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Office;

@Repository
public interface OfficeRepository extends JpaRepository<Office,Long> {
   Office findByAddressId_City(String city);
   Office findOfficeById(Long id);
}
