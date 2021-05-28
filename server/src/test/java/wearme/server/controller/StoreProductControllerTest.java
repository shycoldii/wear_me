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
import wearme.server.model.*;
import wearme.server.repository.*;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StoreProductControllerTest {
    @Autowired
    public MockMvc mvc;
    @Autowired
    public StoreProductRepository storeProductRepository;
    @Autowired
    public SupplierRepository supplierRepository;
    @Autowired
    public OfficeRepository officeRepository;
    /**
     * Проверка получения товара
     */
    @Test
    public void getStoreProduct() {
        try {
           int id = 1;
            this.mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/shop/storeProducts?articul="+id))
                    .andDo(print())
                    .andExpect(status().is2xxSuccessful())
                    .andExpect(mvcResult -> {
                        String body = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
                        JSONArray jsonObject = new JSONArray(body);
                        assertEquals(storeProductRepository.findStoreProductByArticul(id).size(), jsonObject.length());
                    })
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Проверка put-запроса товара
     */
    @Test
    public void updateStoreProduct() {
        try{
            JSONObject jsonStoreProduct = new JSONObject();
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonAddress = new JSONObject();

            StoreProduct storeProduct = storeProductRepository.findStoreProductById(2L);
            Office office = officeRepository.findOfficeById(1L);
            jsonAddress.put("id",office.getAddressId().getId());
            jsonAddress.put("apartment",office.getAddressId().getApartment());
            jsonAddress.put("city",office.getAddressId().getCity());
            jsonAddress.put("floor",office.getAddressId().getFloor());
            jsonAddress.put("porch",office.getAddressId().getPorch());
            jsonAddress.put("street",office.getAddressId().getStreet());
            jsonAddress.put("streetCode",office.getAddressId().getStreetCode());

            jsonOffice.put("id",office.getId());
            jsonOffice.put("name",office.getName());
            jsonOffice.put("phoneNumber",office.getPhoneNumber());
            jsonOffice.put("addressId",jsonAddress);

            jsonStoreProduct.put("id",storeProduct.getId());
            jsonStoreProduct.put("articul",storeProduct.getArticul());
            jsonStoreProduct.put("color",storeProduct.getColor());
            jsonStoreProduct.put("description",storeProduct.getDescription());
            jsonStoreProduct.put("name",storeProduct.getName());
            jsonStoreProduct.put("retailPrice",1500);
            jsonStoreProduct.put("size",storeProduct.getSize());
            jsonStoreProduct.put("status",storeProduct.getStatus());
            jsonStoreProduct.put("tradePrice",storeProduct.getTradePrice());
            Supplier supplier = supplierRepository.findSupplierByNameAndEmailAndPhoneNumber("CalF","CalF@mail.ru",
                    "78007775553");
            JSONObject jsonSupplier = new JSONObject();
            jsonSupplier.put("id",supplier.getId());
            jsonSupplier.put("name",supplier.getName());
            jsonSupplier.put("phoneNumber",supplier.getPhoneNumber());
            jsonSupplier.put("email",supplier.getEmail());
            jsonStoreProduct.put("type",storeProduct.getType());
            jsonStoreProduct.put("supplierId",jsonSupplier);
            jsonStoreProduct.put("office",jsonOffice);
            this.mvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/shop/storeProducts/"+storeProduct.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonStoreProduct.toString())
                    .accept(MediaType.APPLICATION_JSON))
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Проверка post-запроса товара
     */
    @Test
    public void storeProduct() {
        try{
            JSONObject jsonStoreProduct = new JSONObject();
            JSONObject jsonOffice = new JSONObject();
            JSONObject jsonAddress = new JSONObject();

            StoreProduct storeProduct = storeProductRepository.findStoreProductById(2L);
            Office office = officeRepository.findOfficeById(1L);
            jsonAddress.put("id",office.getAddressId().getId());
            jsonAddress.put("apartment",office.getAddressId().getApartment());
            jsonAddress.put("city",office.getAddressId().getCity());
            jsonAddress.put("floor",office.getAddressId().getFloor());
            jsonAddress.put("porch",office.getAddressId().getPorch());
            jsonAddress.put("street",office.getAddressId().getStreet());
            jsonAddress.put("streetCode",office.getAddressId().getStreetCode());

            jsonOffice.put("id",office.getId());
            jsonOffice.put("name",office.getName());
            jsonOffice.put("phoneNumber",office.getPhoneNumber());
            jsonOffice.put("addressId",jsonAddress);

            jsonStoreProduct.put("id",storeProduct.getId());
            jsonStoreProduct.put("articul",storeProduct.getArticul());
            jsonStoreProduct.put("color",storeProduct.getColor());
            jsonStoreProduct.put("description",storeProduct.getDescription());
            jsonStoreProduct.put("name",storeProduct.getName());
            jsonStoreProduct.put("retailPrice",storeProduct.getRetailPrice());
            jsonStoreProduct.put("size",storeProduct.getSize());
            jsonStoreProduct.put("status",storeProduct.getStatus());
            jsonStoreProduct.put("tradePrice",storeProduct.getTradePrice());
            Supplier supplier = supplierRepository.findSupplierByNameAndEmailAndPhoneNumber("CalF","CalF@mail.ru",
                    "78007775553");
            JSONObject jsonSupplier = new JSONObject();
            jsonSupplier.put("id",supplier.getId());
            jsonSupplier.put("name",supplier.getName());
            jsonSupplier.put("phoneNumber",supplier.getPhoneNumber());
            jsonSupplier.put("email",supplier.getEmail());
            jsonStoreProduct.put("type",storeProduct.getType());
            jsonStoreProduct.put("supplierId",jsonSupplier);
            jsonStoreProduct.put("office",jsonOffice);
            this.mvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/shop/storeProducts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonStoreProduct.toString())
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