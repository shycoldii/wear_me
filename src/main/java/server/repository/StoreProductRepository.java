package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.StoreProduct;

import java.util.List;
/**
 * Интерфейс JpaRepository для работы с товарами
 */
@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct,Long> {
    StoreProduct findStoreProductById(Long id);

    List<StoreProduct> findStoreProductByArticulAndOffice_IdAndSizeAndStatus(Integer articul, Long officeId,String size,
                                                                             Integer status);
    List<StoreProduct> findStoreProductByCheck_Id(Long checkId);
    List<StoreProduct> findStoreProductByArticul(Integer articul);

}
