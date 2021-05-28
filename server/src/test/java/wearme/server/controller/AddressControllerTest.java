package wearme.server.controller;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import wearme.server.repository.AddressRepository;
import java.nio.charset.StandardCharsets;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddressControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public AddressRepository addressRepository;

    /**
     * Проверка get-запроса на адрес по id
     */
    @Test
    public void getAddress() {
        try {
            long id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/addresses?id="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(addressRepository.findAddressById(id).getId(), jsonObject.getLong("id"));
                        assertEquals(addressRepository.findAddressById(id).getCity(), jsonObject.getString("city"));
                        assertEquals(addressRepository.findAddressById(id).getStreet(), jsonObject.getString("street"));
                        assertEquals(addressRepository.findAddressById(id).getStreetCode(), jsonObject.getInt("streetCode"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}