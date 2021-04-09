package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import client.utils.SupplierStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import scala.scalajs.js.JSON;

import java.io.IOException;

public class AddingSupply2Controller {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private AddingSupply1Controller addingSupply1Controller;
    @FXML private TextField colorField;
    @FXML private TextField descriptionField;
    @FXML private TextField nameField;
    @FXML private TextField retailField;
    @FXML private TextField tradeField;
    @FXML private ComboBox<String> sizeField;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> typeField;
    @FXML private ComboBox<String> supplierField;



    public void setSupply1Controller(AddingSupply1Controller addingSupply1Controller) {
        this.addingSupply1Controller = addingSupply1Controller;
    }

    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }

    @FXML
    public void initialize()  {
    }
    @FXML public void loadData() throws JSONException {
        if (this.addingSupply1Controller.getStatus()){
            this.nameField.setText(this.API.getArticulProducts().getJSONObject(0).getString("name"));
            this.nameField.setEditable(false);
            this.descriptionField.setText(this.API.getArticulProducts().getJSONObject(0).getString("description"));
            this.descriptionField.setEditable(false);
            this.retailField.setText(this.API.getArticulProducts().getJSONObject(0).getString("retailPrice"));
            this.retailField.setEditable(false);
            this.tradeField.setText(this.API.getArticulProducts().getJSONObject(0).getString("tradePrice"));
            this.tradeField.setEditable(false);
            this.descriptionField.setText(this.API.getArticulProducts().getJSONObject(0).getString("description"));
            this.descriptionField.setEditable(false);
            ObservableList<String> type = FXCollections.observableArrayList();
            type.add(this.API.getArticulProducts().getJSONObject(0).getString("type"));
            this.typeField.setItems(type);
            ObservableList<String> supp = FXCollections.observableArrayList();
            supp.add(this.API.getArticulProducts().getJSONObject(0).getJSONObject("supplierId").getLong("id")+"-"+this.API.getArticulProducts().getJSONObject(0).getJSONObject("supplierId").getString("name"));
            this.typeField.setValue(this.typeField.getItems().get(0));
            this.supplierField.setItems(supp);
            this.supplierField.setValue(this.supplierField.getItems().get(0));
        }
        else{
            this.typeField.setItems(this.getTypes());
            this.supplierField.setItems(this.getSuppliers());
        }
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.add("XS");
        observableList.add("S");
        observableList.add("M");
        observableList.add("L");
        observableList.add("XL");
        this.sizeField.setItems(observableList);

    }

    private ObservableList<String> getSuppliers() {
        try{
            ObservableList<String> observableList = FXCollections.observableArrayList();
            ObservableList<SupplierStructure> supplierStructures= this.API.getSuppliers();
            for(SupplierStructure s:supplierStructures){
                observableList.add(s.getId()+"-"+s.getName());
            }
            return observableList;

        }
      catch (Exception e){
          e.printStackTrace();
          Alert a = AlertInfo.getWarningAlert(mainApp);
          a.setHeaderText("Client error");
          a.setContentText(e.getMessage());
          MyLogger.logger.error(e.getMessage());
          a.show();
          this.stage.close();
      }

        return null;
    }

    public ObservableList<String> getTypes(){
        ObservableList<String> type = FXCollections.observableArrayList();
        type.add("джинсы");
        type.add("головной убор");
        type.add("штаны");
        type.add("куртка");
        type.add("пальто");
        type.add("тренч");
        type.add("жилет");
        type.add("платье");
        type.add("комбинезон");
        type.add("рубашка");
        type.add("топ");
        type.add("трикотаж");
        type.add("футболка");
        type.add("боди");
        type.add("толстовка");
        type.add("брюки");
        type.add("шорты");
        type.add("юбка");
        type.add("костюм");
        type.add("обувь");
        type.add("сумка");
        type.add("аксессуар");
        return type;
    }
    @FXML
    public void supply(){
        if(!colorField.getText().equals("") & sizeField.getValue() != null &
                !amountField.getText().equals("") & !nameField.getText().equals("")
        & !descriptionField.getText().equals("") & !retailField.getText().equals("")
        & !tradeField.getText().equals("") &  typeField.getValue() != null &
                supplierField.getValue() != null){
            try{
                int amount = Integer.parseInt(amountField.getText());
                int retail = Integer.parseInt(retailField.getText());
                int trade = Integer.parseInt(tradeField.getText());
                if(retail<trade){
                    Alert a = AlertInfo.getWarningAlert(mainApp);
                    a.setHeaderText("Retail price < trade price");
                    a.setContentText("Check prices!");
                    a.show();
                }
                else{
                    if(amount>0 & amount <=1000){
                        JSONObject jsonProduct = new JSONObject();
                        jsonProduct.put("articul",Integer.parseInt(this.addingSupply1Controller.getArticulField().getText()));
                        jsonProduct.put("color",this.colorField.getText());
                        jsonProduct.put("description",this.descriptionField.getText());
                        jsonProduct.put("name",this.nameField.getText());
                        jsonProduct.put("retailPrice",retail);
                        jsonProduct.put("size",this.sizeField.getValue());
                        jsonProduct.put("status",1);
                        jsonProduct.put("tradePrice",trade);
                        jsonProduct.put("type",typeField.getValue());
                        jsonProduct.put("office",this.API.getJsonOffice());
                        String supIdString = this.supplierField.getValue();
                        int index = supIdString.indexOf("-");
                        long supId= Long.parseLong(supIdString.substring(0,index));
                        this.API.getSuppliers();
                        JSONArray jsonSup = this.API.getJsonSuppliers();
                        for(int i=0;i<jsonSup.length();i++){
                            if(jsonSup.getJSONObject(i).getLong("id") == supId){
                                jsonProduct.put("supplierId",jsonSup.getJSONObject(i));
                                break;
                            }
                        }
                        for(int i=0;i<amount;i++){
                            if(this.API.createProduct(jsonProduct)){
                                MyLogger.logger.info("Товар был поставлен");
                            }
                            else{
                                MyLogger.logger.error("Товар не был поставлен");
                            }
                        }
                        this.addingSupply1Controller.getStockController().loadData();
                        this.stage.close();
                        MyLogger.logger.info("Товары были поставлены");
                        Alert a = AlertInfo.getOkAlert(mainApp);
                        a.setHeaderText("Success");
                        a.setContentText("The products have been added to the database");
                        a.show();
                    }
                    else{
                        Alert a = AlertInfo.getWarningAlert(mainApp);
                        a.setHeaderText("Amount of products");
                        a.setContentText("Amount <= 1000!");
                        MyLogger.logger.error("Введено неправильное количество");
                        a.show();
                    }

                }

            }
            catch (JSONException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Incorrect fields");
                a.setContentText("Amount - it is a number!");
                a.show();
            }
            catch(IOException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Server error");
                a.setContentText("An error has occurred");
                a.show();
            }
            catch(Exception e){
                e.printStackTrace();
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Client error");
                a.setContentText(e.getMessage());
                a.show();
            }
        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("Fill in all the fields!");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поля");
        }
    }

}
