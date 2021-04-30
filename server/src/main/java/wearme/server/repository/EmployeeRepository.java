package wearme.server.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import wearme.server.model.Employee;

import java.util.List;
/**
 * Интерфейс JpaRepository для работы с сотрудниками
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    /**
     * Получает работника по идентификатору
     * @param id - идентификатор
     * @return employee
     */
    Employee findEmployeeById(Long id);

    /**
     * Получает список всех сотрудников
     * @return list of employees
     */
    List<Employee> findAll();

    /**
     * Получает работника по почте
     * @param email - электронная почта
     * @return employee
     */
    Employee findEmployeeByEmail(String email);
}
