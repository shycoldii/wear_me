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
import wearme.server.model.Client;
import wearme.server.repository.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public ClientRepository clientRepository;

    /**
     * Проверка put-запроса на клиента
     */
    @Test
    public void updateClient() {
        try {
            long id = 1L;
            JSONObject jsonObject = new JSONObject();
            Client client = clientRepository.findClientById(id);
            jsonObject.put("birthday",client.getBirthday());
            jsonObject.put("id",client.getId());
            jsonObject.put("email",client.getEmail());
            jsonObject.put("firstName",client.getFirstName());
            jsonObject.put("secondName",client.getSecondName());
            jsonObject.put("phoneNumber",client.getPhoneNumber());
            jsonObject.put("regDay", client.getRegDay());
            jsonObject.put("numberOfBonuses",35000);
            this.mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/shop/clients/"+id)
                    .content(jsonObject.toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка get-запроса на клиента по email
     */
    @Test
    public void getClient() {
        try {
            String email = "bru@mail.ru";
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/client?email="+email))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(body);
                        assertEquals(clientRepository.findClientByEmail(email).getId(), jsonObject.getLong("id"));
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка get-запроса всех клиентов
     */
    @Test
    public void getAllClients() {
        try {
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/clients"))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonArray = new JSONArray(body);
                        assertEquals(jsonArray.length(), this.clientRepository.findAll().size());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Проверка post-запроса на клиента
     */
    @Test
    public void newReturn() {
        try {
            Client client = clientRepository.findClientByEmail("bru@mail.ru");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("birthday",client.getBirthday());
            jsonObject.put("email","test@mail.ru");
            jsonObject.put("firstName",client.getFirstName());
            jsonObject.put("secondName",client.getSecondName());
            jsonObject.put("phoneNumber","79805206655");
            jsonObject.put("regDay", LocalDate.now());
            jsonObject.put("numberOfBonuses",0);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/clients")
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