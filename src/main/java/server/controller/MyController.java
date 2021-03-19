package server.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("shop")
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

    @GetMapping("/login/{email}")
    public ResponseEntity<Boolean> login(@PathVariable String email){
        Employee res = EmployeeRepository.findEmployeeByEmail(email);
        return res != null ? new ResponseEntity<>(true, HttpStatus.OK) :
                new ResponseEntity<>(false,HttpStatus.OK);
    }

    @GetMapping("/login/{email}/{password}")
    public ResponseEntity<Long> login(@PathVariable String email,@PathVariable int password) {
        Employee res = EmployeeRepository.findEmployeeByEmail(email);
        return res != null && res.getPassword().hashCode() == password ? new ResponseEntity<>(res.getId(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.OK);

    }




}
