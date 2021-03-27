package client.api;

import client.JavaFXApplication;
import client.exception.NoAppDataException;
import client.exception.NoColorException;
import client.exception.NoStoreProductException;
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
import java.util.*;

public class MyAPI {
    private JavaFXApplication mainApp;
    private boolean isAuthenticated=false;
    private ObservableList<CheckStructure> checkData = FXCollections.observableArrayList();
    //private ArrayList<Long> ListOfProducts = new ArrayList<>();
    private HashMap<Long,ArrayList<String>> ListOfProducts = new HashMap<>();
    private Long employeeId;
    private Long officeId;
    private Long addressId;
    private List<String> listOfColors = new ArrayList<>();
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

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }
    public String getEmployeeInfo() throws JSONException {
        return position+" "+jsonEmployee.get("secondName").toString()+" "+jsonEmployee.get("firstName").toString();
    }
    public int getTotalPrice(){
        int z = 0;
        for(CheckStructure i: checkData){
            z+=i.getTotal();
        }
        return z;
    }
    public void deleteProduct(Integer articul,String color){
        for(CheckStructure i: checkData){
           if(i.getArticul() == articul & i.getColor().equals(color)){
               if(i.getAmount() == 1){
                   checkData.remove(i);
               }
               else{

                   i.setAmount(i.getAmount()-1);
                   i.setTotal(i.getAmount()*i.getPrice());
               }
               for(Map.Entry<Long, ArrayList<String>> a: ListOfProducts.entrySet()){
                   if(a.getValue().get(0).equals(articul.toString()) & a.getValue().get(1).equals(color)){
                       this.ListOfProducts.remove(a.getKey(),a.getValue());
                       break;
                   }
               }
               break;
           }
        }
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

    public void deleteAllProducts(){
        this.checkData.clear();
        this.ListOfProducts.clear();
    }

    //блок SIGN IN
    public Boolean isEmail(String email) throws ResponceStatusException {
        String url = "http://localhost:8181/shop/login?email="+ URLEncoder.encode(email);
        String response = HTTPRequest.Get(url);
        if(response!=null){
            return !response.equals("");

        }
        this.isAuthenticated = false;
        throw  new ResponceStatusException(this.mainApp, "Проверка на email - пустой ответ от сервера");
    }
    public  Long isPassword(String email, String password) throws ResponceStatusException {
        String url = "http://localhost:8181/shop/login?email="+URLEncoder.encode(email)+"&password="+password.hashCode();
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
        String url = "http://localhost:8181/shop/employee?id="+URLEncoder.encode(String.valueOf(employeeId));
        String result = HTTPRequest.Get(url);
        this.jsonEmployee = new JSONObject(result);
        JSONObject office = (JSONObject) jsonEmployee.get("officeId");
        this.setOfficeId(Long.valueOf(office.get("id").toString()));
        url = "http://localhost:8181/shop/office?id="+URLEncoder.encode(String.valueOf(this.officeId));
        result = HTTPRequest.Get(url);
        this.jsonOffice = new JSONObject(result);
        office = (JSONObject) jsonOffice.get("addressId");
        this.addressId = Long.valueOf(office.get("id").toString());
        url = "http://localhost:8181/shop/address?id="+URLEncoder.encode(String.valueOf(this.addressId));
        result = HTTPRequest.Get(url);
        this.jsonAddress = new JSONObject(result);
        JSONObject pos = (JSONObject) jsonEmployee.get("positionId");
        Long positionId = Long.valueOf(pos.get("id").toString());
        url = "http://localhost:8181/shop/position?id="+URLEncoder.encode(String.valueOf(positionId));
        this.position = HTTPRequest.Get(url);
        if (this.position==null || this.position.equals("") | this.getEmployeeInfo().equals("") | this.getAddress().equals("")){
            throw new NoAppDataException(mainApp,"Данные не были загружены");
        }
    }
    public boolean isInCheckData(Integer articul,String name,Integer retailPrice,String color){
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
        for(Long i: ListOfProducts.keySet()){
            if (i.equals(id)){
                return true;
            }

        }
        return false;
    }
    public void getStoreProduct(Integer articul,String size,String color) throws ResponceStatusException, JSONException, NoStoreProductException, NoColorException {
        this.listOfColors.clear();

        String url = "http://localhost:8181/shop/storeProduct?articul="+URLEncoder.encode(String.valueOf(articul))+
                "&productSize="+size+"&officeId="+ officeId;
        String result = HTTPRequest.Get(url);
        int checker = 0;
        if(result!=null){
            if (!result.equals("")){
                JSONArray jsonStoreProducts = new JSONArray(result);
                for (int i=0;i< jsonStoreProducts.length();i++){
                    Long id = Long.valueOf(jsonStoreProducts.getJSONObject(i).get("id").toString());
                    if(this.isInProducts(id) | jsonStoreProducts.getJSONObject(i).has("checkId")){
                        checker+=1;
                    }
                    else {
                        String color1 = jsonStoreProducts.getJSONObject(i).get("color").toString();
                        if(color1.equals(color)){
                            String name = jsonStoreProducts.getJSONObject(i).get("name").toString();
                            ArrayList<String> s = new ArrayList<>();
                            s.add(articul.toString());
                            s.add(color);
                            Integer retailPrice =  Integer.parseInt(jsonStoreProducts.getJSONObject(i).get("retailPrice").toString());
                            this.ListOfProducts.put(id,s);
                            if (this.isInCheckData(articul, name, retailPrice,color)) {
                                for (CheckStructure ch : this.checkData) {
                                    if (ch.getArticul() == articul) {
                                        ch.setAmount(ch.getAmount() + 1);
                                        ch.setTotal(ch.getPrice() * ch.getAmount());
                                    }
                                    break;
                                }
                            } else {
                                this.checkData.add(new CheckStructure(articul, name, color,1, retailPrice, retailPrice));
                            }
                            break;
                        }
                        else{
                            this.listOfColors.add(color1);
                        }

                    }
                }
                if(checker==jsonStoreProducts.length()){
                    throw new NoStoreProductException(this.mainApp,"Не найден товар");
                }
                if(!listOfColors.isEmpty()){
                    throw new NoColorException(this.mainApp,"Не найден товар заданного цвета");
                }
            }
            else{
                throw new NoStoreProductException(this.mainApp,"Не найден товар");
            }


        }
        else{
            throw new ResponceStatusException(this.mainApp, "Получение товара- нет ответа от сервера");
        }

    }

    public List<String> getListOfColors() {
        return listOfColors;
    }
}
