package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Supplier;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    Supplier findSupplierByName(String name);
}
