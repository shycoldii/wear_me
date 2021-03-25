package client.api;

import client.JavaFXApplication;
import client.exception.NoAppDataException;
import client.exception.ResponceStatusException;
import client.utils.CheckStructure;
import client.utils.HTTPRequest;
import client.utils.MyLogger;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class MyAPI {
    private JavaFXApplication mainApp;
    private boolean isAuthenticated=false;
    private ObservableList<CheckStructure> checkData = FXCollections.observableArrayList();
    private ArrayList<Long> ListOfProducts = new ArrayList<>();
    private Long employeeId;
    private Long officeId;
    private Long addressId;
    public JSONObject jsonAddress;
    public JSONObject jsonEmployee;
    public JSONObject jsonOffice;
    private String position;

    public ObservableList<CheckStructure> getCheckData() {
        return checkData;
    }
    public ObservableList<CheckStructure> getPersonData(){
        return checkData;
    }
    public void updateCheckData(CheckStructure check){
        this.checkData.add(check);
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
    public String getEmployeeInfo() throws JSONException {
        return position+" "+jsonEmployee.get("secondName").toString()+" "+jsonEmployee.get("firstName").toString();
    }

    public void setOfficeId(Long officeId) {
        this.officeId = officeId;
    }
    public String getAddress() throws JSONException {
        return jsonAddress.get("street").toString();
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public MyAPI(JavaFXApplication mainApp){
        this.mainApp = mainApp;
        MyLogger.logger.info("Инициализирован MyAPI");
    }
    //блок SIGN IN
    public Boolean isEmail(String email) throws ResponceStatusException {
        String url = "http://localhost:8080/shop/login?email="+ URLEncoder.encode(email);
        String response = HTTPRequest.Get(url);
        if(response!=null){
            return !response.equals("");

        }
        this.isAuthenticated = false;
        throw  new ResponceStatusException(this.mainApp, "Проверка на email - пустой ответ от сервера");
    }
    public  Long isPassword(String email, String password) throws ResponceStatusException {
        String url = "http://localhost:8080/shop/login?email="+URLEncoder.encode(email)+"&password="+password.hashCode();
        String response = HTTPRequest.Get(url);
        if(response!=null){
            if(response.equals("")){
                return null;
            }
            this.isAuthenticated = true;
            return Long.parseLong(response);
        }
        this.isAuthenticated = false;
        throw new ResponceStatusException(this.mainApp, "Проверка на password - пустой ответ от сервера");
    }
    public void getData() throws JSONException, NoAppDataException {
        String url = "http://localhost:8080/shop/employee?id="+URLEncoder.encode(String.valueOf(employeeId));
        String result = HTTPRequest.Get(url);
        this.jsonEmployee = new JSONObject(result);
        JSONObject office = (JSONObject) jsonEmployee.get("officeId");
        this.setOfficeId(Long.valueOf(office.get("id").toString()));
        url = "http://localhost:8080/shop/office?id="+URLEncoder.encode(String.valueOf(this.officeId));
        result = HTTPRequest.Get(url);
        this.jsonOffice = new JSONObject(result);
        office = (JSONObject) jsonOffice.get("addressId");
        this.addressId = Long.valueOf(office.get("id").toString());
        url = "http://localhost:8080/shop/address?id="+URLEncoder.encode(String.valueOf(this.addressId));
        result = HTTPRequest.Get(url);
        this.jsonAddress = new JSONObject(result);
        JSONObject pos = (JSONObject) jsonEmployee.get("positionId");
        Long positionId = Long.valueOf(pos.get("id").toString());
        url = "http://localhost:8080/shop/position?id="+URLEncoder.encode(String.valueOf(positionId));
        this.position = HTTPRequest.Get(url);
        if (this.position==null || this.position.equals("") | this.getEmployeeInfo().equals("") | this.getAddress().equals("")){
            throw new NoAppDataException(mainApp,"Данные не были загружены");
        }
    }
    public boolean isInCheckData(Integer articul,String name,Integer retailPrice){
        for(CheckStructure c: this.checkData){
            if(c.articulProperty().getValue().equals(articul) &&
                    c.nameProperty().getValue().equals(name) &&
                    c.getPrice() == retailPrice){
                return true;
            }
        }
        return false;

    }
    public boolean isInProducts(Long id){
        for(Long i: ListOfProducts){
            if (i.equals(id)){
                return true;
            }

        }
        return false;
    }
    public void getStoreProduct(Long id,String size) throws ResponceStatusException, JSONException {
        String url = "http://localhost:8080/shop/storeProduct?articul="+URLEncoder.encode(String.valueOf(id))+
                "&productSize="+size+"&officeId="+ officeId;
        String result = HTTPRequest.Get(url);
        if(result!=null){
            JSONObject jsonStoreProduct = new JSONObject(result);
            Long id1 = Long.valueOf(jsonStoreProduct.get("id").toString());
            JSONObject product = (JSONObject) jsonStoreProduct.get("product");
            Integer articul = Integer.parseInt(product.get("articul").toString());
            String name = product.get("name").toString();
            Integer retailPrice =  Integer.parseInt(product.get("retailPrice").toString());
            if(this.isInProducts(id1)){

                //бросаем что товар уже есть в списке
                //todo: сделать перебор уже тут и выбирать из массива
           }
           else {
                this.ListOfProducts.add(id1);

                if (this.isInCheckData(articul, name, retailPrice)) {
                    System.out.println("dfghj");
                    for (CheckStructure ch : this.checkData) {
                        if (ch.getArticul() == articul) {
                            ch.setAmount(ch.getAmount() + 1);
                            ch.setTotal(ch.getPrice() * ch.getAmount());
                        }
                    }
                } else {
                    this.checkData.add(new CheckStructure(articul, name, 1, retailPrice, retailPrice));
                    System.out.println(this.checkData);
                }
            }


            //TODO: мб обработать незапаршенное

        }
        else{
            throw new ResponceStatusException(this.mainApp, "Получение товара- нет ответа от сервера");
        }

    }



}
