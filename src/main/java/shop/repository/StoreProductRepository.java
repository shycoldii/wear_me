package shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Employee;
import shop.model.StoreProduct;

import java.util.List;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct,Long> {
    StoreProduct findStoreProductById(Long id);
    List<StoreProduct> findAll();
}
