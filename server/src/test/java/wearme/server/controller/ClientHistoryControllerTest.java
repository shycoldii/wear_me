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
import wearme.server.model.Client;
import wearme.server.model.Employee;
import wearme.server.repository.*;
import java.time.LocalDate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientHistoryControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public ClientHistoryRepository clientHistoryRepository;
    @Autowired
    public ClientRepository clientRepository;
    @Autowired
    public EmployeeRepository employeeRepository;


    /**
     * Проверка post-запроса на историю клиента
     */
    @Test
    public void newClientsHistory() {
        try {
            JSONObject jsonObject = new JSONObject();
            JSONObject jsonClient = new JSONObject();
            JSONObject jsonEmployee = new JSONObject();
            Client client = clientRepository.findClientById(1L);
            Employee employee = employeeRepository.findEmployeeByEmail("bru@mail.ru");
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
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonAddress = new JSONObject();
            JSONObject jsonPosition = new JSONObject();
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
            jsonClient.put("birthday",client.getBirthday());
            jsonClient.put("id",client.getId());
            jsonClient.put("email",client.getEmail());
            jsonClient.put("firstName",client.getFirstName());
            jsonClient.put("secondName",client.getSecondName());
            jsonClient.put("phoneNumber",client.getPhoneNumber());
            jsonClient.put("regDay", client.getRegDay());
            jsonClient.put("numberOfBonuses",client.getNumberOfBonuses());
            jsonObject.put("date",LocalDate.now());
            jsonObject.put("employee",jsonEmployee);
            jsonObject.put("client",jsonClient);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/clients_history")
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