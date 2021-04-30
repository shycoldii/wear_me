package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.StoreProduct;

import java.util.List;
/**
 * Интерфейс JpaRepository для работы с товарами
 */
@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct,Long> {
    /**
     * Получает товар по идентификатору
     * @param id - идентификатор
     * @return storeProduct
     */
    StoreProduct findStoreProductById(Long id);

    /**
     * Получает список товаров по критериям
     * @param articul - артикул
     * @param officeId - идентификатор офиса
     * @param size - размер
     * @param status - статус
     * @return list of storeProducts
     */
    List<StoreProduct> findStoreProductByArticulAndOffice_IdAndSizeAndStatus(Integer articul, Long officeId,String size,
                                                                             Integer status);

    /**
     * Получает список товаров по номеру чека
     * @param checkId - идентификатор чека
     * @return list of storeProducts
     */
    List<StoreProduct> findStoreProductByCheck_Id(Long checkId);

    /**
     * Получает список товаров по артикулу
     * @param articul - артикул
     * @return list of storeProducts
     */
    List<StoreProduct> findStoreProductByArticul(Integer articul);

}
