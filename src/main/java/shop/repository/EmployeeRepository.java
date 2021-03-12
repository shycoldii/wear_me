package shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.model.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
    Employee findEmployeeById(Long id);
    List<Employee> findAll();

}
