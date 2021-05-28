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
import wearme.server.model.Check;
import wearme.server.repository.CheckRepository;
import wearme.server.repository.EmployeeRepository;
import wearme.server.repository.OfficeRepository;
import wearme.server.repository.PositionRepository;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CheckControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public CheckRepository checkRepository;
    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public OfficeRepository officeRepository;
    @Autowired
    public PositionRepository positionRepository;


    /**
     * Проверка get-запроса на чек по id
     */
    @Test
    public void getCheck() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/checks?id="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(checkRepository.findCheckById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(checkRepository.findCheckById(id).getDiscount(), jsonObject.getInt("discount"));
                        assertEquals(checkRepository.findCheckById(id).getTotal(), jsonObject.getInt("total"));
                        assertEquals(checkRepository.findCheckById(id).getEmployee().getId(),
                                jsonObject.getJSONObject("employee").getLong("id"));


                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка post-запроса на чек
     */
    @Test
    public void newCheck() {
        try {
            Check check = new Check(LocalDateTime.now(),1999,20);
            check.setEmployee(employeeRepository.findEmployeeById(1L));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dateTime",check.getDateTime());
            jsonObject.put("total",check.getTotal());
            jsonObject.put("discount",check.getDiscount());
            JSONObject jsonEmployee = new JSONObject();
            jsonEmployee.put("id",check.getEmployee().getId());
            jsonEmployee.put("birthday",check.getEmployee().getBirthday());
            jsonEmployee.put("email",check.getEmployee().getEmail());
            jsonEmployee.put("firstName",check.getEmployee().getFirstName());
            jsonEmployee.put("passport",check.getEmployee().getPassport());
            jsonEmployee.put("password",check.getEmployee().getPassword());
            jsonEmployee.put("patronymic",check.getEmployee().getPatronymic());
            jsonEmployee.put("phoneNumber",check.getEmployee().getPhoneNumber());
            jsonEmployee.put("regDay",check.getEmployee().getRegDay());
            jsonEmployee.put("secondName",check.getEmployee().getSecondName());
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonAddress = new JSONObject();
            JSONObject jsonPosition = new JSONObject();
            jsonPosition.put("id",check.getEmployee().getPositionId().getId());
            jsonPosition.put("name",check.getEmployee().getPositionId().getName());
            jsonAddress.put("id",check.getEmployee().getOfficeId().getAddressId().getId());
            jsonAddress.put("apartment",check.getEmployee().getOfficeId().getAddressId().getApartment());
            jsonAddress.put("city",check.getEmployee().getOfficeId().getAddressId().getCity());
            jsonAddress.put("floor",check.getEmployee().getOfficeId().getAddressId().getFloor());
            jsonAddress.put("porch",check.getEmployee().getOfficeId().getAddressId().getPorch());
            jsonAddress.put("street",check.getEmployee().getOfficeId().getAddressId().getStreet());
            jsonAddress.put("streetCode",check.getEmployee().getOfficeId().getAddressId().getStreetCode());
            jsonOffice.put("id",check.getEmployee().getOfficeId().getId());
            jsonOffice.put("name",check.getEmployee().getOfficeId().getName());
            jsonOffice.put("phoneNumber",check.getEmployee().getOfficeId().getPhoneNumber());
            jsonOffice.put("addressId",jsonAddress);
            jsonEmployee.put("officeId",jsonOffice);
            jsonEmployee.put("positionId",jsonPosition);
            jsonObject.put("employee",jsonEmployee);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/checks")
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