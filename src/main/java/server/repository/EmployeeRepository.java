package server.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.model.Employee;

import java.util.List;
/**
 * Интерфейс JpaRepository для работы с сотрудниками
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeById(Long id);
    List<Employee> findAll();
    Employee findEmployeeByEmail(String email);
    Employee findEmployeeByEmailAndPassword(String email,String Password);
}
