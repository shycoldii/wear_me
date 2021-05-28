package wearme.server.controller;

import org.json.JSONArray;
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
import wearme.server.repository.*;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SupplierControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public StoreProductRepository storeProductRepository;
    @Autowired
    public SupplierRepository supplierRepository;
    /**
     * Проверка получения всех поставщиков
     */
    @Test
    public void getAllSuppliers() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/suppliers"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        assertEquals(jsonArray.length(), this.supplierRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Проверка получения поставщика по email
     */
    @Test
    public void getSupplier() {
        try {
            String email = "mail@vidolgov.ru";
            String name = "wert";
            String phone = "79879879878";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/supplier?email="+email+"&name="+name+
                    "&phone="+phone))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(supplierRepository.findSupplierByNameAndEmailAndPhoneNumber(name,
                                email,phone).getName(), jsonObject.getString("name"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка post-запроса поставщика
     */
    @Test
    public void newSupplier() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email","TEST@mail.ru");
            jsonObject.put("name","test");
            jsonObject.put("phoneNumber", "79806667755");
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/suppliers")
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