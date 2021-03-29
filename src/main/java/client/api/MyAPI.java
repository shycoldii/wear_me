package client.api;

import client.JavaFXApplication;
import client.exception.*;
import client.utils.CheckStructure;

import client.utils.HTTPRequest;
import client.utils.MyLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

public class MyAPI {
    private JavaFXApplication mainApp;
    private boolean isAuthenticated=false;
    private ObservableList<CheckStructure> checkData = FXCollections.observableArrayList();
    private HashMap<Long,ArrayList<String>> ListOfProducts = new HashMap<>();
    private Long employeeId;
    private Long officeId;
    private Long addressId;
    private Long clientId;
    private Integer currentBonuses = 0;
    private Integer bonuses = 0;
    private HashMap<String,Integer> promocode = new HashMap<>();
    private List<String> listOfColors = new ArrayList<>();
    public JSONObject jsonAddress;
    public JSONObject jsonEmployee;
    public JSONObject jsonOffice;
    private String position;
    private JSONObject jsonClient;
    private JSONObject jsonPromocode;
    private JSONArray jsonStoreProducts = new JSONArray();
    private JSONObject updatedCheck;
    public void deleteAllProducts(){
        this.checkData.clear();
        this.ListOfProducts.clear();
        this.promocode.clear();
        this.listOfColors.clear();
        this.jsonClient = new JSONObject();
        this.jsonPromocode = new JSONObject();
        this.jsonStoreProducts = new JSONArray();
        this.updatedCheck = new JSONObject();
        this.bonuses = 0;
        this.currentBonuses = 0;
        this.clientId = null;
    }

    public Integer getCurrentBonuses() {
        return currentBonuses;
    }

    public HashMap<String, Integer> getPromocode() {
        return promocode;
    }

    public void setCurrentBonuses(Integer currentBonuses) {
        this.currentBonuses = currentBonuses;
    }

    public Integer getBonuses() {
        return bonuses;
    }

    public Long getClientId() {
        return clientId;
    }

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
    public int getPriceWithDiscount(){
        int disc = this.getPromocodeDiscount();
        int total = 0;
        if (disc==0){
            disc = this.getLoyaltyDiscount();
            if(disc!=0){
                for(CheckStructure i: checkData){
                    total+=i.getAmount()*i.getPrice()*disc/100;
                }
            }
        }
        else{
            for(CheckStructure i: checkData){
                total+=i.getAmount()*i.getPrice()*disc/100;
            }
        }
        return total;
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

    public HashMap<Long, ArrayList<String>> getListOfProducts() {
        return ListOfProducts;
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
    public void removePromocode(){
        this.promocode.clear();
    }
    public void removeLoyaltyCard(){
        this.bonuses = 0;
        this.clientId = null;
    }


    public String getLocalHost(){
        return "http://localhost:8282/shop/";
    }

    //блок SIGN IN
    public Boolean isEmail(String email) throws ResponceStatusException {
        String url = this.getLocalHost()+"login?email="+ URLEncoder.encode(email);
        String response = HTTPRequest.Get(url);
        if(response!=null){
            return !response.equals("");

        }
        this.isAuthenticated = false;
        throw  new ResponceStatusException(this.mainApp, "Проверка на email - пустой ответ от сервера");
    }
    public  Long isPassword(String email, String password) throws ResponceStatusException {
        String url = this.getLocalHost()+"login?email="+URLEncoder.encode(email)+"&password="+password.hashCode();
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
        String url = this.getLocalHost()+"employee?id="+URLEncoder.encode(String.valueOf(employeeId));
        String result = HTTPRequest.Get(url);
        this.jsonEmployee = new JSONObject(result);
        JSONObject office = (JSONObject) jsonEmployee.get("officeId");
        this.setOfficeId(Long.valueOf(office.get("id").toString()));
        url = this.getLocalHost()+"office?id="+URLEncoder.encode(String.valueOf(this.officeId));
        result = HTTPRequest.Get(url);
        this.jsonOffice = new JSONObject(result);
        office = (JSONObject) jsonOffice.get("addressId");
        this.addressId = Long.valueOf(office.get("id").toString());
        url = this.getLocalHost()+"address?id="+URLEncoder.encode(String.valueOf(this.addressId));
        result = HTTPRequest.Get(url);
        this.jsonAddress = new JSONObject(result);
        JSONObject pos = (JSONObject) jsonEmployee.get("positionId");
        Long positionId = Long.valueOf(pos.get("id").toString());
        url = this.getLocalHost()+"position?id="+URLEncoder.encode(String.valueOf(positionId));
        this.position = HTTPRequest.Get(url);
        if (this.position==null || this.position.equals("") | this.getEmployeeInfo().equals("") | this.getAddress().equals("")){
            throw new NoAppDataException(mainApp,"Данные не были загружены");
        }
    }
    public boolean isInCheckData(Integer articul,String name,Integer retailPrice,String color){
        for(CheckStructure c: this.checkData){
            if(c.articulProperty().getValue().equals(articul) &&
                    c.nameProperty().getValue().equals(name) &&
                    c.getPrice() == retailPrice && c.getColor().equals(color)){
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

        String url =this.getLocalHost()+"storeProduct?articul="+URLEncoder.encode(String.valueOf(articul))+
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
                            this.listOfColors.clear();
                            this.jsonStoreProducts.put( jsonStoreProducts.getJSONObject(i));
                            if (this.isInCheckData(articul, name, retailPrice,color1)) {
                                for (CheckStructure ch : this.checkData) {
                                    if (ch.getArticul() == articul & ch.getColor().equals(color1)) {
                                        ch.setAmount(ch.getAmount() + 1);
                                        ch.setTotal(ch.getPrice() * ch.getAmount());
                                        break;
                                    }
                                }
                            } else {
                                this.checkData.add(new CheckStructure(articul, name, color1,1, retailPrice, retailPrice));
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

    public void getPromocode(String name) throws ResponceStatusException, JSONException, NoPromocodeException {
        String url = this.getLocalHost()+"promocode?name="+URLEncoder.encode(name);
        String result = HTTPRequest.Get(url);
        if(result!=null){
            if(!result.equals("")){
                this.promocode.clear();
                JSONObject jsonPromocode = new JSONObject(result);
                this.jsonPromocode = jsonPromocode;
                this.promocode.put(jsonPromocode.get("name").toString(),jsonPromocode.getInt("discount"));
                MyLogger.logger.info("Промокод установлен");
            }
            else{
                throw new NoPromocodeException(this.mainApp,"Получение промокода - не найден");
            }

        }
        else{
            throw new ResponceStatusException(this.mainApp, "Получение промокода- нет ответа от сервера");
        }
    }

    public Integer getPromocodeDiscount() {
        for(Integer k: this.promocode.values())
            return k;
        return 0;
    }
    public Integer getLoyaltyDiscount(){
            int discount = 0;
            int bonuses = this.getBonuses();
            if(bonuses <1000 & bonuses>=500){
                discount = 5;
            }
            else if(bonuses>=1000 & bonuses<5000){
                discount = 10;
            }
            else if(bonuses>=5000 & bonuses<10000){
                discount = 15;
            }
            else if(bonuses>=10000){
                discount = 20;
            }
            return discount;
    }

    public void getLoyaltyCard(String phoneNumber,String email) throws ResponceStatusException, NoClientException, JSONException {
        String url = "";
            if(phoneNumber==null){
                url =this.getLocalHost()+ "client?email="+URLEncoder.encode(email);
            }
            else if(email == null){
                url = this.getLocalHost()+"client?phone="+URLEncoder.encode(phoneNumber);
            }
            else{
                url = this.getLocalHost()+"client?phone="+URLEncoder.encode(phoneNumber)+"&email="+URLEncoder.encode(email);
            }
            String result = HTTPRequest.Get(url);
            if(result!=null){
                if(!result.equals("")){
                    this.bonuses = null;
                    this.clientId = null;
                    JSONObject jsonClient = new JSONObject(result);
                    this.jsonClient = jsonClient;
                    this.clientId = Long.parseLong(jsonClient.get("id").toString());
                    this.bonuses = jsonClient.getInt("numberOfBonuses");
                    MyLogger.logger.info("Клиент установлен");
                }
                else{
                    throw new NoClientException(this.mainApp,"Получение клиента - не найден");
                }

            }
            else{
                throw new ResponceStatusException(this.mainApp, "Получение клиента- нет ответа от сервера");
            }
    }
    public void sellProducts() throws JSONException, IOException, SellProductException {
            JSONObject jsonCheck = new JSONObject();
            jsonCheck.put("dateTime",LocalDateTime.now());
            if(clientId !=null){
                jsonClient.put("numberOfBonuses",this.currentBonuses);
                String url = this.getLocalHost()+"clients/"+Long.parseLong(jsonClient.get("id").toString());
                String updatedClient = HTTPRequest.Put(url,jsonClient);
                if(!updatedClient.equals("")){
                    jsonCheck.put("client", new JSONObject(updatedClient));
                }
                else{
                    throw new SellProductException(this.mainApp,"Ошибка при отправке данных клиента: "+jsonClient.get("id").toString());
                }

            }
            else if(promocode!=null){
                jsonCheck.put("promocode",this.jsonPromocode);
            }
            jsonCheck.put("employee",this.jsonEmployee);
            jsonCheck.put("storeProducts",this.jsonStoreProducts);
            jsonCheck.put("total",this.getTotalPrice()-this.getPriceWithDiscount());
            String url =this.getLocalHost()+ "checks";
            String updatedCheck = HTTPRequest.Post(url,jsonCheck);

            JSONObject jsonUpdatedCheck = new JSONObject(updatedCheck);
            this.updatedCheck =jsonUpdatedCheck;
            if(!updatedCheck.equals("")){
                for(int i=0;i<this.jsonStoreProducts.length();i++){
                    JSONObject toUpdate = this.jsonStoreProducts.getJSONObject(i);
                    toUpdate.put("status",3);
                    toUpdate.put("check",jsonUpdatedCheck);
                    url = this.getLocalHost()+"storeProducts/"+Long.parseLong(toUpdate.get("id").toString());
                    String updatedStoreProduct = HTTPRequest.Put(url,toUpdate);
                    if(updatedStoreProduct.equals("")){
                        throw new SellProductException(this.mainApp,"Ошибка при изменении статуса товара: "+toUpdate.get("id").toString());
                    }
                }
            }
            else{
                throw new SellProductException(this.mainApp,"Ошибка при формировании чека");
            }

        }

    public JSONObject getUpdatedCheck() {
        return updatedCheck;
    }
}
