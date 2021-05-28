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
import wearme.server.model.*;
import wearme.server.repository.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReturnsHistoryControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public ReturnsHistoryRepository returnsHistoryRepository;
    @Autowired
    public CheckRepository checkRepository;
    @Autowired
    public StoreProductRepository storeProductRepository;
    @Autowired
    public SupplierRepository supplierRepository;
    @Autowired
    public EmployeeRepository employeeRepository;

    /**
     * Проверка post-запроса новой истории возврата
     */
    @Test
    public void newReturn() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonStoreProduct = new JSONObject();
            JSONObject jsonCheck = new JSONObject();
            JSONObject jsonEmployee = new JSONObject();
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonAddress = new JSONObject();
            JSONObject jsonPosition = new JSONObject();
            JSONObject jsonSupplier = new JSONObject();
            Employee employee = employeeRepository.findEmployeeByEmail("a@mail.ru");
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

            Supplier supplier = supplierRepository.findSupplierByNameAndEmailAndPhoneNumber("CalF","CalF@mail.ru",
                    "78007775553");
            jsonSupplier.put("id",supplier.getId());
            jsonSupplier.put("name",supplier.getName());
            jsonSupplier.put("phoneNumber",supplier.getPhoneNumber());
            jsonSupplier.put("email",supplier.getEmail());

            Check check = checkRepository.findCheckById(1L);
            jsonCheck.put("id",check.getId());
            jsonCheck.put("dateTime",check.getDateTime());
            jsonCheck.put("total",check.getTotal());
            jsonCheck.put("discount",check.getDiscount());
            jsonCheck.put("employee",jsonEmployee);

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
            jsonStoreProduct.put("check",jsonCheck);
            jsonStoreProduct.put("supplierId",jsonSupplier);
            jsonStoreProduct.put("office",jsonOffice);


            jsonObject.put("total",1999);
            jsonObject.put("employee",jsonEmployee);
            jsonObject.put("check",jsonCheck);
            System.out.println(jsonCheck.toString());
            jsonObject.put("storeProduct",jsonStoreProduct);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/returns")
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