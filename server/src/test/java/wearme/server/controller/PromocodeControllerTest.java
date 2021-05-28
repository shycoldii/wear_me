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
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PromocodeControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public PromocodeRepository promocodeRepository;
    /**
     * Проверка get-запроса промокода по названию
     */
    @Test
    public void getPromocode() {
        try {
            String name = "БУБЛИК";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/promocode?name="+name))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(promocodeRepository.findPromocodeByName(name).getName(), jsonObject.getString("name"));
                        assertEquals(promocodeRepository.findPromocodeByName(name).getDiscount(), jsonObject.getInt("discount"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка get-запроса  всех промокодов
     */
    @Test
    public void getAllPromocodes() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/promocodes"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        assertEquals(jsonArray.length(), this.promocodeRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка post-запроса на промокод
     */
    @Test
    public void newPromocode() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name","TEST");
            jsonObject.put("discount",5);
            jsonObject.put("startDate",LocalDate.of(2021,5,28));
            jsonObject.put("endDate",LocalDate.of(2021,5,28));
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/promocodes")
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