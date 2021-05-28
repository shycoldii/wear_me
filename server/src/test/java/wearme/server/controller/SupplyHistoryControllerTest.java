package wearme.server.controller;

import org.junit.Test;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import wearme.server.model.Employee;
import wearme.server.model.StoreProduct;
import wearme.server.model.Supplier;
import wearme.server.repository.*;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupplyHistoryControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public SupplyHistoryRepository supplyHistoryRepository;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public StoreProductRepository storeProductRepository;
    @Autowired
    public SupplierRepository supplierRepository;

    /**
     * Проверка post-запроса истории поставки
     */
    @Test
   public  void supplyHistory() {
        try {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", LocalDate.now());
            Employee employee = employeeRepository.findEmployeeByEmail("a@mail.ru");
            JSONObject jsonEmployee = new JSONObject();
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonSupplier = new JSONObject();
            JSONObject jsonAddress = new JSONObject();
            JSONObject jsonPosition = new JSONObject();
            JSONObject jsonStoreProduct = new JSONObject();
            Supplier supplier = supplierRepository.findSupplierByNameAndEmailAndPhoneNumber("CalF","CalF@mail.ru",
                    "78007775553");
            jsonSupplier.put("id",supplier.getId());
            jsonSupplier.put("name",supplier.getName());
            jsonSupplier.put("phoneNumber",supplier.getPhoneNumber());
            jsonSupplier.put("email",supplier.getEmail());
            jsonEmployee.put("id",employee.getId());
            jsonEmployee.put("birthday",employee.getBirthday());
            jsonEmployee.put("email",employee.getEmail());
            jsonEmployee.put("firstName",employee.getFirstName());
            jsonEmployee.put("passport",employee.getPassport());
            jsonEmployee.put("password",employee.getPassword());
            jsonEmployee.put("patronymic",employee.getPatronymic());
            jsonEmployee.put("phoneNumber",employee.getPhoneNumber());
            jsonEmployee.put("regDay",employee.getRegDay());
            jsonEmployee.put("secondName",employee.getSecondName());
            jsonPosition.put("id",employee.getPositionId().getId());
            jsonPosition.put("name",employee.getPositionId().getName());
            jsonAddress.put("id",employee.getOfficeId().getAddressId().getId());
            jsonAddress.put("apartment",employee.getOfficeId().getAddressId().getApartment());
            jsonAddress.put("city",employee.getOfficeId().getAddressId().getCity());
            jsonAddress.put("floor",employee.getOfficeId().getAddressId().getFloor());
            jsonAddress.put("porch",employee.getOfficeId().getAddressId().getPorch());
            jsonAddress.put("street",employee.getOfficeId().getAddressId().getStreet());
            jsonAddress.put("streetCode",employee.getOfficeId().getAddressId().getStreetCode());
            jsonOffice.put("id",employee.getOfficeId().getId());
            jsonOffice.put("name",employee.getOfficeId().getName());
            jsonOffice.put("phoneNumber",employee.getOfficeId().getPhoneNumber());
            jsonOffice.put("addressId",jsonAddress);
            jsonEmployee.put("officeId",jsonOffice);
            jsonEmployee.put("positionId",jsonPosition);

            StoreProduct storeProduct = storeProductRepository.findStoreProductById(2L);
            jsonStoreProduct.put("id",storeProduct.getId());
            jsonStoreProduct.put("articul",storeProduct.getArticul());
            jsonStoreProduct.put("color",storeProduct.getColor());
            jsonStoreProduct.put("description",storeProduct.getDescription());
            jsonStoreProduct.put("name",storeProduct.getName());
            jsonStoreProduct.put("retailPrice",storeProduct.getRetailPrice());
            jsonStoreProduct.put("size",storeProduct.getSize());
            jsonStoreProduct.put("status",storeProduct.getStatus());
            jsonStoreProduct.put("tradePrice",storeProduct.getTradePrice());
            jsonStoreProduct.put("type",storeProduct.getType());
            jsonStoreProduct.put("supplierId",jsonSupplier);
            jsonStoreProduct.put("office",jsonOffice);

            jsonObject.put("employee",jsonEmployee);
            jsonObject.put("storeProduct", jsonStoreProduct);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/supplyHistory")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonObject.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}