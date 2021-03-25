package server.controller;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.*;
import server.repository.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<Long> login(@RequestParam String email,
                                         @RequestParam(required = false)
                                                 Integer password){
        Employee res = EmployeeRepository.findEmployeeByEmail(email);
        if(password != null){
            return res != null && res.getPassword().hashCode() == password ? new ResponseEntity<>(res.getId(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.OK);
        }
        else{
            return res != null ? new ResponseEntity<>(res.getId(), HttpStatus.OK) :
                    new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/employee")
    @ResponseBody
    public ResponseEntity<Employee> getEmployee(@RequestParam Long id){
        Employee res = EmployeeRepository.findEmployeeById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/office")
    @ResponseBody
    public ResponseEntity<Office> getOffice(@RequestParam Long id){
        Office res = officeRepository.findOfficeById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/address")
    @ResponseBody
    public ResponseEntity<Address> getAddress(@RequestParam Long id){
        Address res = addressRepository.findAddressById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/position")
    @ResponseBody
    public ResponseEntity<String> getPosition(@RequestParam Long id){
        Position res = PosRepository.findPositionById(id);
        if(res!=null){
            return new ResponseEntity<>(res.getName(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/test")
    public void test(){

        Address adr = new Address("Москва","Семеновская","1","2",554433,null);
        addressRepository.save(adr);
        Office of = new Office("Wear me","80007775555");
        of.setAddress_id(addressRepository.findAddressByStreetCode(554433));
        officeRepository.save(of);
        Position pos = new Position("Программист");
        PosRepository.save(pos);
        Employee emp = new Employee("Дарья","Александрова","Игоревна","a@mail.ru","1","8800775543","222113 3333",
                LocalDateTime.now(), LocalDate.of(2001,12,3));
        emp.setOfficeId(officeRepository.findByAddressId_City("Москва"));
        emp.setPosition(PosRepository.findPositionByName("Программист"));
        EmployeeRepository.save(emp);




        Supplier sup = new Supplier("CalF","88007775553","CalF@mail.ru");
        supplierRepository.save(sup);
        Product pr = new Product(1,"California Jeans",300,1999,"Jeans","M","Blue",null);
        pr.setSupplier(supplierRepository.findSupplierByName("CalF"));
        productRepository.save(pr);


        StoreProduct st = new StoreProduct();
        st.setProduct(productRepository.findProductByName("California Jeans"));
        st.setOffice(officeRepository.findOfficeById(1L));
        System.out.println(st.toString());
        storeProductRepository.save(st);




    }
    @GetMapping("/storeProduct")
    @ResponseBody
    public ResponseEntity<StoreProduct> getStoreProduct(@RequestParam Integer articul,@RequestParam Long officeId,@RequestParam String productSize){
        List<StoreProduct> res = storeProductRepository.findStoreProductByProduct_ArticulAndOffice_IdAndProduct_Size(articul,officeId,productSize);
        if(res!=null){
            return new ResponseEntity<>(res.get(0),HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
