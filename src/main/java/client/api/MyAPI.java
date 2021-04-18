package client.api;

import client.JavaFXApplication;
import client.exception.*;
import client.utils.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

public class MyAPI {
    private JavaFXApplication mainApp;
    private boolean isAuthenticated=false;
    private ObservableList<CheckStructure> checkData = FXCollections.observableArrayList();
    private HashMap<Long,ArrayList<String>> ListOfProducts = new HashMap<>();
    private Long employeeId;
    private Long officeId;
    private Long addressId;
    private Long clientId;
    private Long checkId;
    private Integer currentBonuses = 0;
    private Integer bonuses = 0;
    private List<String> listOfColors = new ArrayList<>();
    public JSONObject jsonAddress= new JSONObject();
    public JSONObject jsonEmployee= new JSONObject();
    public JSONObject jsonOffice= new JSONObject();
    private String position;
    private JSONObject jsonClient= new JSONObject();
    private JSONObject jsonPromocode = new JSONObject();
    private JSONArray jsonStoreProducts = new JSONArray();
    private JSONObject updatedCheck = new JSONObject();
    private ObservableList<ProductStructure> productData = FXCollections.observableArrayList();
    private JSONArray jsonProductData = new JSONArray();
    private String returnResult;
    private String totalResult;
    private JSONArray jsonClients = new JSONArray();
    private JSONArray jsonPromocodes = new JSONArray();
    private JSONArray jsonOffices = new JSONArray();
    private JSONArray jsonProducts = new JSONArray();
    private JSONArray articulProducts = new JSONArray();
    private JSONArray jsonSuppliers = new JSONArray();

    public void deleteAllProducts(){
        this.checkData.clear();
        this.ListOfProducts.clear();
        this.listOfColors.clear();
        this.jsonClient = new JSONObject();
        this.jsonPromocode = new JSONObject();
        this.jsonStoreProducts = new JSONArray();
        this.updatedCheck = new JSONObject();
        this.bonuses = 0;
        this.currentBonuses = 0;
        this.clientId = null;
    }

    public JSONObject getJsonOffice() {
        return jsonOffice;
    }

    public JSONArray getJsonSuppliers() {
        return jsonSuppliers;
    }

    public Integer getCurrentBonuses() {
        return currentBonuses;
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
    public int getPriceWithDiscount() throws JSONException {
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
    public void removeLoyaltyCard(){
        this.bonuses = 0;
        this.clientId = null;
    }


    public String getLocalHost(){
        return "http://localhost:8283/shop/";
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
        String url = this.getLocalHost()+"employees?id="+URLEncoder.encode(String.valueOf(employeeId));
        String result = HTTPRequest.Get(url);
        this.jsonEmployee = new JSONObject(result);
        JSONObject office = (JSONObject) jsonEmployee.get("officeId");
        this.setOfficeId(Long.valueOf(office.get("id").toString()));
        url = this.getLocalHost()+"office?id="+URLEncoder.encode(String.valueOf(this.officeId));
        result = HTTPRequest.Get(url);
        this.jsonOffice = new JSONObject(result);
        office = (JSONObject) jsonOffice.get("addressId");
        this.addressId = Long.valueOf(office.get("id").toString());
        url = this.getLocalHost()+"addresses?id="+URLEncoder.encode(String.valueOf(this.addressId));
        result = HTTPRequest.Get(url);
        this.jsonAddress = new JSONObject(result);
        JSONObject pos = (JSONObject) jsonEmployee.get("positionId");
        Long positionId = Long.valueOf(pos.get("id").toString());
        url = this.getLocalHost()+"positions?id="+URLEncoder.encode(String.valueOf(positionId));
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

    public Long getOfficeId() {
        return officeId;
    }

    public void getStoreProduct(Integer articul, String size, String color) throws ResponceStatusException, JSONException, NoStoreProductException, NoColorException {
        this.listOfColors.clear();
        String url =this.getLocalHost()+"storeProducts?articul="+URLEncoder.encode(String.valueOf(articul))+
                "&productSize="+size+"&officeId="+ officeId;
        String result = HTTPRequest.Get(url);
        int checker = 0;
        if(result!=null){
            if (!result.equals("")){
                JSONArray jsonStoreProducts = new JSONArray(result);
                for (int i=0;i< jsonStoreProducts.length();i++){
                    Long id = Long.valueOf(jsonStoreProducts.getJSONObject(i).get("id").toString());
                    if(this.isInProducts(id)){
                        checker+=1;
                    }
                    else {
                        String color1 = jsonStoreProducts.getJSONObject(i).get("color").toString();
                        if(color1.toLowerCase().equals(color.toLowerCase())){
                            String name = jsonStoreProducts.getJSONObject(i).get("name").toString();
                            ArrayList<String> s = new ArrayList<>();
                            s.add(articul.toString());
                            s.add(color1);
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

    public void getPromocode(String name) throws ResponceStatusException, JSONException, NoPromocodeException, TimeOutPromocodeException {
        String url = this.getLocalHost()+"promocode?name="+URLEncoder.encode(name);
        String result = HTTPRequest.Get(url);
        if(result!=null){
            if(!result.equals("")){
                JSONObject jsonPromocode = new JSONObject(result);
                LocalDate now = LocalDate.now();
                if(now.isBefore(LocalDate.parse(jsonPromocode.getString("startDate"))) |
                now.isAfter(LocalDate.parse(jsonPromocode.getString("endDate")))){
                    throw new TimeOutPromocodeException(this.mainApp,"Этот промокод недействителен");
                }
                this.jsonPromocode = jsonPromocode;
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

    public JSONObject getJsonPromocode() {
        return jsonPromocode;
    }
    public void removePromocode(){
        this.jsonPromocode = new JSONObject();
    }
    public boolean postPromocode(JSONObject jsonPr) throws NoPromocodeException, JSONException, IOException, ResponceStatusException {
        String url = this.getLocalHost()+"promocode?name="+URLEncoder.encode(jsonPr.getString("name"));
        String promocode = HTTPRequest.Get(url);
        if (promocode!= null) {
            if(promocode.equals("")){
                url = this.getLocalHost() +"promocodes";
                promocode = HTTPRequest.Post(url,jsonPr);
                return !promocode.equals("");
                }
            throw new NoPromocodeException(this.mainApp,"Промокод с таким именем уже существует");
            }
        throw new ResponceStatusException(this.mainApp,"Сервер не отвечает");
        }

    public Integer getPromocodeDiscount() throws JSONException {
        if(!(this.jsonPromocode.length() == 0)) {
            return this.jsonPromocode.getInt("discount");
        }
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
    public void getProductsByCheckId(Long checkId) throws NoStoreProductException, ResponceStatusException, JSONException {
        String url = this.getLocalHost()+"checks?id="+URLEncoder.encode(String.valueOf(checkId));
        String result = HTTPRequest.Get(url);
        if(result!=null){
            if (!result.equals("")){
                JSONObject jsonCheck = new JSONObject(result);
                LocalDateTime checkDate = LocalDateTime.parse(jsonCheck.getString("dateTime"));
                long daysBetween = DAYS.between(checkDate, LocalDateTime.now());
                if (daysBetween >30){
                    throw new NoStoreProductException(this.mainApp,"Возврат по дате невозможен");
                }
            }
            else{
                throw new NoStoreProductException(this.mainApp,"Не найден check id");
            }
        }
        else{
            throw new ResponceStatusException(this.mainApp, "Получение чека- нет ответа от сервера");
        }
        url =this.getLocalHost()+"storeProducts?checkId="+URLEncoder.encode(String.valueOf(checkId));
        result = HTTPRequest.Get(url);
        if(result!=null){
            if (!result.equals("[]")){
                JSONArray jsonProducts = new JSONArray(result);
                this.jsonProductData = jsonProducts;
                for(int i=0;i<jsonProducts.length();i++){
                    this.productData.add(
                            new ProductStructure(jsonProducts.getJSONObject(i).getLong("id"),
                                    jsonProducts.getJSONObject(i).getInt("articul"),
                                    jsonProducts.getJSONObject(i).get("name").toString(),
                                    jsonProducts.getJSONObject(i).get("color").toString(),
                                    jsonProducts.getJSONObject(i).get("size").toString(),
                                    jsonProducts.getJSONObject(i).getInt("retailPrice"),
                                    jsonProducts.getJSONObject(i).getString("description"),
                                    jsonProducts.getJSONObject(i).getInt("tradePrice"),
                                    jsonProducts.getJSONObject(i).getInt("status"),
                                    jsonProducts.getJSONObject(i).getString("type"),
                                    jsonProducts.getJSONObject(i).get("office"),
                                    jsonProducts.getJSONObject(i).get("supplierId"),
                                    jsonProducts.getJSONObject(i).get("check")));

                }
            }
            else{
                throw new NoStoreProductException(this.mainApp,"Не найдены товары по check id");
            }
        }
        else{
            throw new ResponceStatusException(this.mainApp, "Получение товара- нет ответа от сервера");
        }

    }
    public void returnProduct(Long check_id,Long id) throws JSONException, IOException, SellProductException {
        Integer price = 0;
        JSONObject jsonProduct = new JSONObject();
        String url =this.getLocalHost()+"storeProducts/"+URLEncoder.encode(String.valueOf(id));
        for(int i=0;i<this.jsonProductData.length();i++){
            if (this.jsonProductData.getJSONObject(i).getLong("id") == id){
                jsonProduct = this.jsonProductData.getJSONObject(i);
                jsonProduct.put("status",2);
                jsonProduct.put("check",null);
                break;
            }
        }
        String updatedProduct = HTTPRequest.Put(url,jsonProduct);
        if(!updatedProduct.equals("")){
            url = this.getLocalHost()+"checks?id="+URLEncoder.encode(String.valueOf(check_id));
            String check = HTTPRequest.Get(url);
            JSONObject jsonUpd = new JSONObject(updatedProduct);
            if(check != null){
                if(!check.equals("")){
                    JSONObject jsonCheck  = new JSONObject(check);
                    JSONObject jsonReturn = new JSONObject();
                    jsonReturn.put("check",jsonCheck);
                    jsonReturn.put("storeProduct",new JSONObject(updatedProduct));
                    jsonReturn.put("employee",this.jsonEmployee);
                    jsonReturn.put("total",jsonProduct.getInt("retailPrice")-(jsonCheck.getInt("discount")*jsonProduct.getInt("retailPrice")/100));
                    url = this.getLocalHost()+"returns";
                    String returns = HTTPRequest.Post(url,jsonReturn);
                    try{
                        JSONObject jsonClient = jsonCheck.getJSONObject("client");
                        int newValue = -jsonUpd.getInt("retailPrice")/100;
                        jsonClient.put("numberOfBonuses",newValue);
                        url = this.getLocalHost()+"clients/"+jsonClient.getLong("id");
                        HTTPRequest.Put(url,jsonClient);
                        MyLogger.logger.info("Бонусы сняты с карты");
                    }
                    catch (JSONException ignored){

                    }


                    if(!returns.equals("")){
                        this.returnResult="Check ID: "+check_id+"\n";
                        this.returnResult+="Store product ID: "+jsonUpd.getLong("id")+"\n";
                        this.returnResult+="Store product color: "+jsonUpd.getString("color")+"\n";
                        this.returnResult+="Store product name: "+jsonUpd.getString("name")+"\n";
                        this.returnResult+="Employee ID: "+this.employeeId+"\n";
                        this.totalResult ="Total to return: "+(jsonProduct.getInt("retailPrice")-(jsonCheck.getInt("discount")*jsonProduct.getInt("retailPrice")/100))+"\n";
                    }
                    else{
                        MyLogger.logger.error("Не удалось сохранить информацию о возврате");
                        throw new SellProductException(this.mainApp,"Ошибка при отправке возврата товара: "+jsonProduct.get("id").toString());
                    }
                }
                else{
                    throw new SellProductException(this.mainApp,"Ошибка при отправке возврата товара: "+jsonProduct.get("id").toString());
                }
            }
            else{
                throw new SellProductException(this.mainApp,"Ошибка при отправке возврата товара: "+jsonProduct.get("id").toString());
            }


        }
        else{
            throw new SellProductException(this.mainApp,"Ошибка при отправке возврата товара: "+jsonProduct.get("id").toString());
        }



    }

    public ObservableList<ProductStructure> getProductData() {
        return productData;
    }

    public void sellProducts() throws JSONException, IOException, SellProductException {
            JSONObject jsonCheck = new JSONObject();
            jsonCheck.put("dateTime",LocalDateTime.now());
            jsonCheck.put("discount",0);
            if(clientId !=null){
                jsonClient.put("numberOfBonuses",this.currentBonuses);
                String url = this.getLocalHost()+"clients/"+Long.parseLong(jsonClient.get("id").toString());
                String updatedClient = HTTPRequest.Put(url,jsonClient);
                if(!updatedClient.equals("")){
                    jsonCheck.put("client", new JSONObject(updatedClient));
                    jsonCheck.put("discount",this.getLoyaltyDiscount());
                }
                else{
                    throw new SellProductException(this.mainApp,"Ошибка при отправке данных клиента: "+jsonClient.get("id").toString());
                }

            }

            else if(jsonPromocode.length() !=0){
                System.out.println(jsonPromocode);
                jsonCheck.put("promocode",this.jsonPromocode);
                jsonCheck.put("discount",this.getPromocodeDiscount());
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

    public String getReturnResult() {
        return returnResult;
    }

    public void setReturnResult(String returnResult) {
        this.returnResult = "";
        this.jsonProductData = new JSONArray();
        this.productData = FXCollections.observableArrayList();
        this.checkId = null;
        this.totalResult = "";
    }

    public String getTotalResult() {
        return totalResult;
    }

    public JSONObject getUpdatedCheck() {
        return updatedCheck;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getCheckId() {
        return checkId;
    }

    public String getPosition() {
        return position;
    }
    public void deleteClient(Long id) throws IOException {
        String url =this.getLocalHost()+"client/"+ id;
        HTTPRequest.Delete(url);
    }
    public boolean createClient(JSONObject jsonClient) throws IOException, JSONException {
        String url = this.getLocalHost()+"client?phone="+URLEncoder.encode(jsonClient.getString("phoneNumber"));
        String client = HTTPRequest.Get(url);
        if (client!= null) {
            if(client.equals("")){
                url =this.getLocalHost() +"client?email="+URLEncoder.encode(jsonClient.getString("email"));
                client = HTTPRequest.Get(url);

                if (client!= null) {
                    if(client.equals("")){
                        url =this.getLocalHost()+"clients";
                        client = HTTPRequest.Post(url,jsonClient);
                        JSONObject jsonClientHistory = new JSONObject();
                        jsonClientHistory.put("date",LocalDate.now());
                        jsonClientHistory.put("employee",this.jsonEmployee);
                        jsonClientHistory.put("client",new JSONObject(client));
                        url = this.getLocalHost() +"clients_history";
                        try{
                            String ch = HTTPRequest.Post(url,jsonClientHistory);
                            if(!ch.equals("")){
                                MyLogger.logger.info("Успешное сохранение данных о клиенте");
                            }
                        }
                       catch (IOException e){
                           MyLogger.logger.error("Неудачное сохранение данных о клиенте");
                       }

                        return !client.equals("");
                    }
                    return false;
                }
                return false;

            }
            return false;
        }
        return false;

    }

    public ObservableList<ClientStructure> getClients() throws NoClientException, JSONException {
        String url =this.getLocalHost()+"clients";
        String clients = HTTPRequest.Get(url);
        if (clients != null) {
            this.jsonClients = new JSONArray(clients);
            ObservableList<ClientStructure> clientData = FXCollections.observableArrayList();
            for(int i=0;i<this.jsonClients.length();i++){
                clientData.add(new ClientStructure(
                        this.jsonClients.getJSONObject(i).getLong("id"),
                        this.jsonClients.getJSONObject(i).get("birthday").toString(),
                        this.jsonClients.getJSONObject(i).getString("email"),
                        this.jsonClients.getJSONObject(i).getString("firstName"),
                        this.jsonClients.getJSONObject(i).getInt("numberOfBonuses"),
                        this.jsonClients.getJSONObject(i).getString("patronymic"),
                        this.jsonClients.getJSONObject(i).getString("phoneNumber"),
                        this.jsonClients.getJSONObject(i).get("regDay").toString(),
                        this.jsonClients.getJSONObject(i).getString("secondName")
                ));
            }
            return clientData;

        }
        else{
            throw new NoClientException(this.mainApp,"Не удалось загрузить клиентов");
        }

    }
    public ObservableList<SupplierStructure> getSuppliers() throws NoSupplierException, JSONException {
        String url =this.getLocalHost()+"suppliers";
        String suppliers = HTTPRequest.Get(url);
        if (suppliers != null) {
            this.jsonSuppliers = new JSONArray(suppliers);
            ObservableList<SupplierStructure> supplierData = FXCollections.observableArrayList();
            for(int i=0;i<this.jsonSuppliers.length();i++){
                supplierData.add(new SupplierStructure(
                        this.jsonSuppliers.getJSONObject(i).getLong("id"),
                        this.jsonSuppliers.getJSONObject(i).getString("name")
                ));
            }
            return supplierData;
        }
        else{
            throw new NoSupplierException(this.mainApp,"Не удалось загрузить поставщиков");
        }

    }
    public ObservableList<PromocodeStructure> getPromocodes() throws NoPromocodeException, JSONException {
        String url =this.getLocalHost()+"promocodes";
        String promocodes = HTTPRequest.Get(url);
        if (promocodes != null) {
            this.jsonPromocodes = new JSONArray(promocodes);
            ObservableList<PromocodeStructure> promocodeData = FXCollections.observableArrayList();
            for(int i = 0; i<this.jsonPromocodes.length(); i++){
                promocodeData.add(new PromocodeStructure(
                        this.jsonPromocodes.getJSONObject(i).getString("name"),
                        this.jsonPromocodes.getJSONObject(i).getInt("discount"),
                        this.jsonPromocodes.getJSONObject(i).getString("startDate"),
                        this.jsonPromocodes.getJSONObject(i).getString("endDate")
                ));
            }
            return promocodeData;

        }
        else{
            throw new NoPromocodeException(this.mainApp,"Не удалось загрузить промокоды");
        }

    }

    public ObservableList<OfficeStructure> getOffices() throws JSONException, NoOfficeException {
        String url =this.getLocalHost()+"offices";
        String offices = HTTPRequest.Get(url);
        if (offices != null) {
            this.jsonOffices = new JSONArray(offices);
            ObservableList<OfficeStructure> OfficeData = FXCollections.observableArrayList();
            for(int i = 0; i<this.jsonOffices.length(); i++){
                JSONObject address = this.jsonOffices.getJSONObject(i).getJSONObject("addressId");
                OfficeData.add(new OfficeStructure(
                        this.jsonOffices.getJSONObject(i).getLong("id"),
                        this.jsonOffices.getJSONObject(i).getString("name"),
                        this.jsonOffices.getJSONObject(i).getString("phoneNumber"),
                        address.getString("city")+" - "+address.getString("street")
                ));
            }
            return OfficeData;

        }
        else{
            throw new NoOfficeException(this.mainApp,"Не удалось загрузить офисы");
        }
    }

    public JSONArray getJsonProducts() {
        return jsonProducts;
    }

    public ObservableList<ProductStructure> getProducts() throws JSONException, NoStoreProductException{
        String url =this.getLocalHost()+"storeProducts";
        String products = HTTPRequest.Get(url);
        if (products != null) {
            this.jsonProducts = new JSONArray(products);
            ObservableList<ProductStructure> productData = FXCollections.observableArrayList();
            for(int i = 0; i<this.jsonProducts.length(); i++){
                JSONObject check;
                try{
                    check = this.jsonProducts.getJSONObject(i).getJSONObject("check");
                }
                catch (JSONException e){
                    check = null;
                }
                productData.add(new ProductStructure(
                        this.jsonProducts.getJSONObject(i).getLong("id"),
                        this.jsonProducts.getJSONObject(i).getInt("articul"),
                        this.jsonProducts.getJSONObject(i).getString("name"),
                        this.jsonProducts.getJSONObject(i).getString("color"),
                        this.jsonProducts.getJSONObject(i).getString("size"),
                        this.jsonProducts.getJSONObject(i).getInt("tradePrice"),
                        this.jsonProducts.getJSONObject(i).getString("description"),
                        this.jsonProducts.getJSONObject(i).getInt("retailPrice"),
                        this.jsonProducts.getJSONObject(i).getInt("status"),
                        this.jsonProducts.getJSONObject(i).getString("type"),
                        this.jsonProducts.getJSONObject(i).getJSONObject("office"),
                        this.jsonProducts.getJSONObject(i).getJSONObject("supplierId"),
                        check

                ));
            }
            return productData;

        }
        else{
            throw new NoStoreProductException(this.mainApp,"Не удалось загрузить продукты");
        }
    }

    public void changeStatusForProduct(JSONObject jsonObject,Long id) throws IOException, NoStoreProductException {
        String url =this.getLocalHost()+"storeProducts/"+URLEncoder.encode(String.valueOf(id));
        String updatedProduct = HTTPRequest.Put(url,jsonObject);
        if(updatedProduct.equals("")){
            throw new NoStoreProductException(this.mainApp,"Не удалось изменить статус");
        }
    }

    public boolean createSupplier(JSONObject jsonSupplier) throws JSONException, IOException {
        String url = this.getLocalHost()+"supplier?phone="+URLEncoder.encode(jsonSupplier.getString("phoneNumber"))+
                "&email="+URLEncoder.encode(jsonSupplier.getString("email"))+
                "&name="+URLEncoder.encode(jsonSupplier.getString("name"));
        String supplier = HTTPRequest.Get(url);
        if (supplier!= null) {
            if(supplier.equals("")){
                        url =this.getLocalHost()+"suppliers";
                        supplier = HTTPRequest.Post(url,jsonSupplier);
                        return !supplier.equals("");

            }
            return false;
        }
        return false;
    }
    public boolean getStoreProductByArticul(int articul) throws ResponceStatusException, JSONException {
        String url =this.getLocalHost()+"storeProducts?articul="+URLEncoder.encode(String.valueOf(articul));
        String result = HTTPRequest.Get(url);
        if(result!=null){
            if(!result.equals("[]")){
                this.articulProducts = new JSONArray(result);
                return true;
            }
            return false;
        }
        else{
            throw new ResponceStatusException(this.mainApp,"Сервер не отвечает");
        }
    }


    public JSONArray getArticulProducts() {
        return articulProducts;
    }

    public boolean createProduct(JSONObject jsonProduct) throws IOException {
        String url = this.getLocalHost()+"storeProducts";
        String stP = HTTPRequest.Post(url,jsonProduct);
        url = this.getLocalHost()+"supplyHistory";
        JSONObject jsonh = new JSONObject();
        try{
            jsonh.put("storeProduct",new JSONObject(stP));
            jsonh.put("date",LocalDate.now());
            jsonh.put("employee",this.jsonEmployee);
        }
        catch (JSONException ignored){
            MyLogger.logger.error("Не удалось сохранить историю поставки");
        }
        HTTPRequest.Post(url,jsonh);
        return !stP.equals("");
    }
}

