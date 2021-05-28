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
import wearme.server.repository.*;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public EmployeeRepository employeeRepository;

    /**
     * Проверка получения сотрудника по id
     */
    @Test
    public void getEmployee() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/employees?id="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(employeeRepository.findEmployeeById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(employeeRepository.findEmployeeById(id).getEmail(), jsonObject.getString("email"));
                        assertEquals(employeeRepository.findEmployeeById(id).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка get-запроса сотрудника по почте/паролю
     */
    @Test
    public void login() {
        try {
            String login = "a@mail.ru";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/login?email="+login))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        assertEquals(employeeRepository.findEmployeeByEmail(login).getId(), Long.parseLong(body));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка post-запроса сотрудника
     */
    @Test
    public void newEmployee() {
        try{
            JSONObject jsonEmployee = new JSONObject();
            Employee employee = employeeRepository.findEmployeeByEmail("bru@mail.ru");
            jsonEmployee.put("birthday",employee.getBirthday());
            jsonEmployee.put("email","test@mail.ru");
            jsonEmployee.put("firstName",employee.getFirstName());
            jsonEmployee.put("passport",employee.getPassport());
            jsonEmployee.put("password",employee.getPassword());
            jsonEmployee.put("patronymic",employee.getPatronymic());
            jsonEmployee.put("phoneNumber","79804443322");
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
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/employees")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonEmployee.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }
}