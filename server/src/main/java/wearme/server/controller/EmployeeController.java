package wearme.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearme.server.model.Employee;
import wearme.server.repository.EmployeeRepository;

/**
 * Рест-контроллер для EmployeeRepository
 */
@RestController
@RequestMapping("shop")
public class EmployeeController {
    private final EmployeeRepository employeeRepository;

    /**
     * Инициализирует контроллер сотрудника
     * @param employeeRepository - репозиторий сотрудника
     */
    public EmployeeController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    /**
     * Получение сотрудника по идентификатору
     * @param id - идентификатор
     * @return сотрудник
     */
    @GetMapping("/employees")
    @ResponseBody
    public ResponseEntity<Employee> getEmployee(@RequestParam Long id){
        Employee res = employeeRepository.findEmployeeById(id);
        if(res!=null){
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Авторизует пользователя и проверяет данные пароля или почты на существование
     * @param email - электронная почта
     * @param password - пароль (хешкод)
     * @return  идентификатор пользователя при удачной авторизации
     */
    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Long> login(@RequestParam String email,
                                      @RequestParam(required = false)
                                              String password){
        Employee res = employeeRepository.findEmployeeByEmail(email);
        if(password != null){
            return res != null && res.getPassword().equals(password) ? new ResponseEntity<>(res.getId(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return res != null ? new ResponseEntity<>(res.getId(), HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.OK);
        }
    }

    /**
     * Создает нового пользователя
     * @param employee - сущность сотрудника
     * @return Employee
     */
    @PostMapping("/employees")
    Employee newEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }
}
