package wearme.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Supplier;

/**
 * Интерфейс JpaRepository для работы с поставщиками
 */
@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
    /**
     * Получает поставщика по критериям
     * @param name - имя
     * @param email - почта
     * @param phone - номер телефона
     * @return Supplier
     */
    Supplier findSupplierByNameAndEmailAndPhoneNumber(String name,String email,String phone);
}
