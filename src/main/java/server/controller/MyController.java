package server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.model.*;
import server.repository.*;

import javax.validation.Valid;
import java.net.URLDecoder;
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
    private final StoreProductRepository storeProductRepository;
    private final SupplyHistoryRepository supplyHistoryRepository;
    private final ClientRepository clientRepository;
    private final ClientHistoryRepository clientHistoryRepository;
    private final CheckRepository checkRepository;
    private final PromocodeRepository promocodeRepository;
    private final EmployeeRepository EmployeeRepository;
    private final ReturnsHistoryRepository returnsHistoryRepository;

    @Autowired
    public MyController(PositionRepository posRepository,
                        EmployeeRepository employeeRepository,
                        AddressRepository addressRepository,
                        OfficeRepository officeRepository,
                        SupplierRepository supplierRepository,
                        StoreProductRepository storeProductRepository,
                        SupplyHistoryRepository supplyHistoryRepository,
                        ClientRepository clientRepository, ClientHistoryRepository clientHistoryRepository,
                        CheckRepository checkRepository,ReturnsHistoryRepository returnsHistoryRepository, PromocodeRepository promocodeRepository) {
        this.PosRepository = posRepository;
        this.EmployeeRepository = employeeRepository;
        this.addressRepository = addressRepository;
        this.officeRepository = officeRepository;
        this.supplierRepository = supplierRepository;
        this.storeProductRepository = storeProductRepository;
        this.supplyHistoryRepository =  supplyHistoryRepository;
        this.clientRepository = clientRepository;
        this.clientHistoryRepository = clientHistoryRepository;
        this.checkRepository = checkRepository;
        this.promocodeRepository = promocodeRepository;
        this.returnsHistoryRepository = returnsHistoryRepository;
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
    @GetMapping("/employees")
    @ResponseBody
    public ResponseEntity<Employee> getEmployee(@RequestParam Long id){
        Employee res = EmployeeRepository.findEmployeeById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/offices")
    @ResponseBody
    public ResponseEntity<Office> getOffice(@RequestParam Long id){
        Office res = officeRepository.findOfficeById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/addresses")
    @ResponseBody
    public ResponseEntity<Address> getAddress(@RequestParam Long id){
        Address res = addressRepository.findAddressById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/positions")
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
        /*
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
        emp.setPositionId(PosRepository.findPositionByName("Программист"));
        EmployeeRepository.save(emp);
        Supplier sup = new Supplier("CalF","88007775553","CalF@mail.ru");
        supplierRepository.save(sup);


        Promocode pr = new Promocode("ВЕСНА",20);
        promocodeRepository.save(pr);
         Client cl = new Client("Александр","Бибик","Васильевич","89104567756","biba@rua",LocalDate.now(),
                LocalDate.of(2001,4,23),15000);
        clientRepository.save(cl);



         */
        StoreProduct st = new StoreProduct(1,"Базовые джинсы mom fit",300,2999,"джинсы",
                "M","белый","Базовые джинсы mom fit",2);
        st.setOffice(officeRepository.findOfficeById(1L));
        st.setSupplierId(supplierRepository.findSupplierByName("CalF"));
        storeProductRepository.save(st);
        //checkRepository.deleteAll();



    }
    @GetMapping("/storeProducts")
    @ResponseBody
    public ResponseEntity<List<StoreProduct>> getStoreProduct(@RequestParam(required = false) Integer articul,@RequestParam(required = false) Long officeId,
                                                              @RequestParam(required = false) String productSize,
                                                              @RequestParam(required = false) Long checkId){
        List<StoreProduct> res;
        if(articul != null){
            res = storeProductRepository.findStoreProductByArticulAndOffice_IdAndSizeAndStatus(articul, officeId, productSize, 2);
        }
        else{
            res = storeProductRepository.findStoreProductByCheck_Id(checkId);

        }
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/promocode")
    @ResponseBody
    public ResponseEntity<Promocode> getPromocode(@RequestParam String name){
        Promocode res = promocodeRepository.findPromocodeByName(URLDecoder.decode(name));
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/storeProducts/{id}")
    @ResponseBody
    public ResponseEntity<StoreProduct> updateStoreProduct(@PathVariable(value = "id") Long id,
                                                   @Valid @RequestBody StoreProduct storeProduct){

        StoreProduct storeProduct1 = storeProductRepository.findStoreProductById(id);
        if(storeProduct1 != null){
            if (storeProduct1.getStatus() ==2 & storeProduct1.getCheck() == null){
                storeProduct1.setCheck(storeProduct.getCheck());
                storeProduct1.setStatus(storeProduct.getStatus());
                return  new ResponseEntity<>(storeProductRepository.save(storeProduct1),HttpStatus.OK);
            }

        }
        return new ResponseEntity<>(storeProductRepository.save(storeProduct),HttpStatus.OK);

    }
    @PutMapping("/clients/{id}")
    @ResponseBody
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long id,
                                                           @Valid @RequestBody Client client){

        Client client1 = clientRepository.findClientById(id);
        if(client1 != null){
            client1.setNumberOfBonuses(client1.getNumberOfBonuses()+client.getNumberOfBonuses());
            return  new ResponseEntity<>(clientRepository.save(client1),HttpStatus.OK);
        }
        return new ResponseEntity<>(clientRepository.save(client),HttpStatus.OK);

    }
    @GetMapping("/checks")
    @ResponseBody
    public ResponseEntity<Check> getCheck(@RequestParam Long id){
        Check res = checkRepository.findCheckById(id);
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/checks")
    Check newCheck(@RequestBody Check newCheck){
        return checkRepository.save(newCheck);
    }

    /*
    @DeleteMapping("/employees/{id}")
    void deleteEmployee(@PathVariable Long id) {
        EmployeeRepository.deleteById(id);
    }

     */
    @PostMapping("/returns")
    ReturnsHistory newReturn(@RequestBody ReturnsHistory returns){
        return returnsHistoryRepository.save(returns);
    }

    @GetMapping("/client")
    @ResponseBody
    public ResponseEntity<Client> getClient(@RequestParam(required = false) String email,
                                            @RequestParam(required = false) String phone){
        Client res = null;
        if (email == null){
            res = clientRepository.findClientByPhoneNumber(phone);
        }
        else if(phone == null){
            res = clientRepository.findClientByEmail(email);

        }
        if (phone!=null & email !=null){
            res = clientRepository.findClientByPhoneNumberAndEmail(phone,email);
        }
        if(res!=null){
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        //не найден такой
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/clients")
    public ResponseEntity<List<Client>> getAllClients(){
        return new ResponseEntity<>(clientRepository.findAll(),HttpStatus.OK);
    }
    @GetMapping("/promocodes")
    public ResponseEntity<List<Promocode>> getAllPromocodes(){
        return new ResponseEntity<>(promocodeRepository.findAll(),HttpStatus.OK);
    }
    @DeleteMapping("/client/{id}")
    void deleteClient(@PathVariable Long id) {
        clientRepository.deleteById(id);
    }
    @PostMapping("/clients")
    Client newReturn(@RequestBody Client client){
        return clientRepository.save(client);
    }
}
