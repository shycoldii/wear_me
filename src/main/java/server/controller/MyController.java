package server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.model.*;
import server.repository.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("shop/login")
public class MyController {
    private final PositionRepository PosRepository;
    private final AddressRepository addressRepository;
    private final OfficeRepository officeRepository;
    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;
    private final StoreProductRepository storeProductRepository;
    private final SupplyHistoryRepository supplyHistoryRepository;
    private final ClientRepository clientRepository;
    private final ClientHistoryRepository clientHistoryRepository;
    private final CheckRepository checkRepository;
    private final PromocodeRepository promocodeRepository;
    private final EmployeeRepository EmployeeRepository;

    @Autowired
    public MyController(PositionRepository posRepository,
                        EmployeeRepository employeeRepository,
                        AddressRepository addressRepository,
                        OfficeRepository officeRepository,
                        SupplierRepository supplierRepository,
                        ProductRepository productRepository,
                        StoreProductRepository storeProductRepository,
                        SupplyHistoryRepository supplyHistoryRepository,
                        ClientRepository clientRepository, ClientHistoryRepository clientHistoryRepository,
                        CheckRepository checkRepository, PromocodeRepository promocodeRepository) {
        this.PosRepository = posRepository;
        this.EmployeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.officeRepository = officeRepository;
        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
        this.storeProductRepository = storeProductRepository;
        this.supplyHistoryRepository =  supplyHistoryRepository;
        this.clientRepository = clientRepository;
        this.clientHistoryRepository = clientHistoryRepository;
        this.checkRepository = checkRepository;
        this.promocodeRepository = promocodeRepository;
    }
    @FXML
    private Label weatherLabel;

    @GetMapping("/clients")
    public void loadWeatherForecast() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Position pos = new Position("Cashier");
        EmployeeRepository.deleteAll();
        //java.sql.SQLIntegrityConstraintViolationException
        //PosRepository.save(pos);
        Employee emp = new Employee("Dasha","Alexandrova","Igorevna","alex@mail.ru","666","8980544444","4444 444444",
                LocalDateTime.now(), LocalDate.parse("2001-12-03",formatter));
        emp.setOfficeId(officeRepository.findByAddressId_City("Moscow"));
        emp.setPosition(PosRepository.findPositionByName("Cashier"));

        EmployeeRepository.save(emp);

    }
    @GetMapping("/{email}")
    boolean login(@PathVariable String email){
        Employee res = EmployeeRepository.findEmployeeByEmail(email);
        return res != null;
    }

    @GetMapping("/{email}/{password}")
    Long login(@PathVariable String email,@PathVariable String password){
        Employee res = EmployeeRepository.findEmployeeByEmailAndPassword(email,password);
        if(res != null){
            return res.getId();
        }
       return null;
        }






}
