package wearme.server.controller;

import org.json.JSONArray;
import org.junit.Test;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wearme.server.repository.*;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class OfficeControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public OfficeRepository officeRepository;

    /**
     * Проверка получения офиса по id
     */
    @Test
    public void getOffice() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/office?id="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(officeRepository.findOfficeById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(officeRepository.findOfficeById(id).getPhoneNumber(), jsonObject.getString("phoneNumber"));
                        assertEquals(officeRepository.findOfficeById(id).getName(), jsonObject.getString("name"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Проверка получения всех офисов
     */
    @Test
    public void getAllOffices() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/offices"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        assertEquals(jsonArray.length(), this.officeRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}