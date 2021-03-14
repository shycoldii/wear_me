package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Supplier findSupplierByName(String name);
}
