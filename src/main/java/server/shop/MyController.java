package server.shop;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.model.*;
import server.repository.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

//@Component
//@FxmlView("Main.fxml")
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
    private WeatherService weatherService;
    private final EmployeeRepository EmployeeRepository;

    @Autowired
    public MyController(PositionRepository posRepository,
                        WeatherService weatherService,
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
        this.weatherService = weatherService;
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
    //public List<Promocode> loadWeatherForecast(ActionEvent actionEvent)
    public List<Check> loadWeatherForecast() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        /*
        Position position = new Position("Cashier");

        //this.PosRepository.save(position);
        Employee employee = new Employee("dfg","dfghj","fghj","dfgh",
                "dfg","dfgh","dfghnj","dfgh", LocalDateTime.now(),
               LocalDate.parse("2005-03-12", formatter));
        employee.setOfficeId(this.officeRepository.findByAddressId_City("Moscow"));

        employee.setPosition(this.PosRepository.findPositionByName("Cashier"));
        this.EmployeeRepository.save(employee);
        Address address = new Address("Moscow","B Galushkina",
                null,null,129301,511);
        //this.addressRepository.save(address);
        Office office = new Office("Save me","88005553535");
        office.setAddress_id(addressRepository.findAddressByStreetCode(
               129301));
        //this.officeRepository.save(office);
        System.out.println(addressRepository.findAddressByStreetCode(129301));
        System.out.println(officeRepository.findByAddressId_City("Moscow"));
        List<Employee> aa = this.EmployeeRepository.findAll();
        for(Employee i: aa){
            System.out.println(i.toString());
        }

        List<Office> allt = this.officeRepository.findAll();
        for(Office i: allt){
            System.out.println(i.toString());
        }


        Supplier sup = new Supplier("Omega","696969","omega@omega.ru");
        this.supplierRepository.save(sup);
        Product product = new Product("Jeans MOMO",2000,200,"Jeans","S",null,
                null);
        product.setSupplier(this.supplierRepository.findSupplierByName("Omega"));
        this.productRepository.save(product);
        StoreProduct storeP = new StoreProduct(1,1);
        storeP.setOffice(officeRepository.findByAddressId_City("Moscow"));
        storeP.setProduct(productRepository.findProductByArticul(1L));
        storeProductRepository.save(storeP);
        SupplyHistory suphis = new SupplyHistory(LocalDate.now());
        suphis.setEmployee(EmployeeRepository.findEmployeeById(1L));
        suphis.setStoreProduct(storeProductRepository.findStoreProductById(
                storeP.getId()
        ));
        this.supplyHistoryRepository.save(suphis);
        Client client = new Client("Polina","Brusova",
                null,"88005553535","bru@mail.ru",
                LocalDate.now(),
               LocalDate.parse("2001-03-12",formatter),5000);
        System.out.println(client.toString());
        this.clientRepository.save(client);
        ClientHistory clh = new ClientHistory(LocalDate.now());
        clh.setClient(this.clientRepository.findClientById((client.getId())));
        clh.setEmployee(this.EmployeeRepository.findEmployeeById(1L));
        this.clientHistoryRepository.save(clh);
        Promocode promocode = new Promocode("BRU05",5);
        this.promocodeRepository.save(promocode);
        Check check = new Check(LocalDateTime.now());
        check.setClient(this.clientRepository.findClientById(1L));
        check.setEmployee(this.EmployeeRepository.findEmployeeById(1L));
        check.setStoreProducts(storeProductRepository.findAll());
        check.setPromocode(this.promocodeRepository.findPromocodeByName("BRU05"));
        this.checkRepository.save(check);*/
        List<Check> ch= this.checkRepository.findAll();
        for(Check i: ch){
            System.out.println(i.toString());
        }
        return ch;


        //this.weatherLabel.setText(weatherService.getWeatherForecast());
    }


}
