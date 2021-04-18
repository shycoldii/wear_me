package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoOfficeException;
import client.exception.NoStoreProductException;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import client.utils.ProductStructure;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Optional;

public class StockController {
    private Stage stage;
    @FXML private TableView<ProductStructure> productTable;
    @FXML ComboBox<String> combobox;
    @FXML private TextField textField;
    @FXML private TableColumn<ProductStructure, Long> id;
    @FXML private TableColumn<ProductStructure, Integer> articul;
    @FXML private TableColumn<ProductStructure,String> color;
    @FXML private TableColumn<ProductStructure,String> description;
    @FXML private TableColumn<ProductStructure,String> name;
    @FXML private TableColumn<ProductStructure,Integer> retailPrice;
    @FXML private TableColumn<ProductStructure,String> size;
    @FXML private TableColumn<ProductStructure,Integer> status;
    @FXML private TableColumn<ProductStructure,Integer> tradePrice;
    @FXML private TableColumn<ProductStructure,String> type;
    @FXML private TableColumn<ProductStructure,String> checkId;
    @FXML private TableColumn<ProductStructure,Long> officeId;
    ObservableList<String> observableList =  FXCollections.observableArrayList();
    @FXML private TableColumn<ProductStructure,Long> supplierId;
    @FXML private Label earned;
    @FXML private Label spent;


    private JavaFXApplication mainApp;

    private ObservableList<ProductStructure> productData = FXCollections.observableArrayList();
    private FilteredList<ProductStructure> filteredData = new FilteredList<>(productData);
    private SortedList<ProductStructure> sortableData = new SortedList<>(filteredData);
    private MyAPI API;
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        id.setCellValueFactory(cellData -> cellData.getValue().idLabelProperty().asObject());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        articul.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        color.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
       description.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        retailPrice.setCellValueFactory(cellData -> cellData.getValue().retail_priceProperty().asObject());
        tradePrice.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        size.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        status.setCellValueFactory(cellData -> cellData.getValue().statusProperty().asObject());
        type.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        checkId.setCellValueFactory(cellData -> cellData.getValue().getCheckId());
        supplierId.setCellValueFactory(cellData -> cellData.getValue().getSupplierId().asObject());
        officeId.setCellValueFactory(cellData -> cellData.getValue().getOfficeId().asObject());
        textField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(p -> p.getName().toLowerCase().contains(textField.getText().toLowerCase()) |
                        p.articulProperty().toString().toLowerCase().contains(textField.getText().toLowerCase() )
                        )
        );
        observableList.add("ALL");
        observableList.add("SUPPLYING");
        observableList.add("ON STOCK");
        observableList.add("WRITTEN OFF");
    }

    public void loadData() throws JSONException, NoStoreProductException {
        this.productData = this.API.getProducts();
        this.filteredData = new FilteredList<>(productData);
        this.sortableData = new SortedList<>(this.filteredData);
        this.productTable.setItems(this.sortableData);
        this.sortableData.comparatorProperty().bind(this.productTable.comparatorProperty());
        this.combobox.setItems(observableList);
        this.combobox.setValue("ALL");
        int earned = 0;
        int spent = 0;
        for(ProductStructure p: this.productData){
            if (p.getStatus() == 3 & !p.getCheckId().getValue().isEmpty() &
                    p.getOfficeId().getValue().equals(this.API.getOfficeId())){
                earned+=p.getRetail_price()*(100-p.getDiscountCheck())/100;
            }
            if(p.getOfficeId().getValue().equals(this.API.getOfficeId())){
                spent+=p.getPrice();
            }

        }

        this.earned.setText("EARNED: "+earned);
        this.spent.setText("SPENT: "+spent);
    }
    @FXML
    public void sortTable(){
        if(combobox.getValue().equals("SUPPLYING")){
            filteredData.setPredicate(p -> p.getStatus() == 1);
        }
        if(combobox.getValue().equals("ALL")){
            filteredData.setPredicate(p -> (p.getStatus() == 1 |  p.getStatus() == 2 | p.getStatus() == 3));
        }
        if(combobox.getValue().equals("ON STOCK")){
            filteredData.setPredicate(p -> p.getStatus() == 2);
        }
        if(combobox.getValue().equals("WRITTEN OFF")){
            filteredData.setPredicate(p -> p.getStatus() == 3);
        }
    }
    @FXML
    public void addSupply(){
        if(this.getRole()){
            this.mainApp.initSupply1(this);
        }

    }

    @FXML public void addSupplier() throws JSONException, NoStoreProductException {
        if(getRole()){
            this.mainApp.initAddingSupplier(this);
            this.loadData();
        }
    }
    @FXML public void upStatus() throws JSONException {
        if(getSelectedIndex() && getRole()){
            int selectedIndex = this.productTable.getSelectionModel().getSelectedIndex();
            int status = this.productTable.getItems().get(selectedIndex).getStatus();
            if(status == 3){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.initOwner(this.mainApp.getPrimaryStage());
                alert.setHeaderText("Unable to change the state of the item");
                alert.setContentText("status 3: WRITTEN OFF");
                alert.show();
            }
            if(status== 1 | status == 2){
                if (getConfirm()){
                   JSONObject jsonObject;
                    JSONArray products = this.API.getJsonProducts();
                   long id = this.productTable.getItems().get(selectedIndex).getIdLabel();
                   for(int i =0;i<products.length();i++){
                       if(products.getJSONObject(i).getLong("id") == id){
                           jsonObject = products.getJSONObject(i);
                           if(status == 1){
                               jsonObject.put("status",2);
                           }
                           else{
                               jsonObject.put("status",3);
                           }
                           try{
                               this.API.changeStatusForProduct(jsonObject,id);
                               Alert a = AlertInfo.getOkAlert(mainApp);
                               if(status == 1){
                                   a.setHeaderText("Status has been changed (1 --> 2)");
                                   MyLogger.logger.info("Статус товара изменен с 1 на 2");
                               }
                              else{
                                   a.setHeaderText("Status has been changed (2 --> 3)");
                                   MyLogger.logger.info("Статус товара изменен с 2 на 3");
                               }
                               a.show();
                               this.loadData();
                           }
                           catch (IOException e){
                               Alert a = AlertInfo.getWarningAlert(mainApp);
                               a.setHeaderText("Server not responding");
                               a.setContentText("Status hasn't been changed");
                               a.show();
                           }
                           catch(NoStoreProductException e){
                               Alert a = AlertInfo.getWarningAlert(mainApp);
                               a.setHeaderText("Status hasn't been changed");
                               a.show();
                           }
                           catch(Exception e){
                               MyLogger.logger.error(e.getMessage());
                               Alert a = AlertInfo.getWarningAlert(mainApp);
                               a.setHeaderText("Client error");
                               a.setContentText(e.getMessage());
                               a.show();
                           }

                           break;
                       }
                   }
                }
            }
        }
    }
    @FXML public void downStatus() throws JSONException {
        if(getSelectedIndex() && getRole()){
            int selectedIndex = this.productTable.getSelectionModel().getSelectedIndex();
            int status = this.productTable.getItems().get(selectedIndex).getStatus();
            if(status == 1){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.initOwner(this.mainApp.getPrimaryStage());
                alert.setHeaderText("Unable to change the state of the item");
                alert.setContentText("status 1: SUPPLYING");
                alert.show();
            }
            if(status== 3 | status == 2){
                if (getConfirm()){
                    JSONObject jsonObject;
                    JSONArray products = this.API.getJsonProducts();
                    long id = this.productTable.getItems().get(selectedIndex).getIdLabel();
                    for(int i =0;i<products.length();i++){
                        if(products.getJSONObject(i).getLong("id") == id){
                            jsonObject = products.getJSONObject(i);
                            if(status == 3){
                                if (this.productTable.getItems().get(selectedIndex).getCheckId().getValue().isEmpty()){
                                    jsonObject.put("status",2);
                                }
                                else{
                                    Alert a = AlertInfo.getWarningAlert(mainApp);
                                    a.setHeaderText("Unable to change status");
                                    a.setContentText("This item was purchased");
                                    a.show();
                                    break;
                                }

                            }
                            else{
                                jsonObject.put("status",1);
                            }
                            try{
                                this.API.changeStatusForProduct(jsonObject,id);
                                Alert a = AlertInfo.getOkAlert(mainApp);
                                if(status == 2){
                                    a.setHeaderText("Status has been changed (2 --> 1)");
                                    MyLogger.logger.info("Статус товара изменен с 2 на 1");
                                }
                                else{
                                    a.setHeaderText("Status has been changed (3 --> 2)");
                                    MyLogger.logger.info("Статус товара изменен с 3 на 2");
                                }
                                a.show();
                                this.loadData();
                            }
                            catch (IOException e){
                                Alert a = AlertInfo.getWarningAlert(mainApp);
                                a.setHeaderText("Server not responding");
                                a.setContentText("Status hasn't been changed");
                                a.show();
                            }
                            catch(NoStoreProductException e){
                                Alert a = AlertInfo.getWarningAlert(mainApp);
                                a.setHeaderText("Status hasn't been changed");
                                a.show();
                            }
                            catch(Exception e){
                                MyLogger.logger.error(e.getMessage());
                                Alert a = AlertInfo.getWarningAlert(mainApp);
                                a.setHeaderText("Client error");
                                a.setContentText(e.getMessage());
                                a.show();
                            }

                            break;
                        }
                    }
                }
            }
        }
    }
    private boolean getSelectedIndex(){
        int selectedIndex = this.productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            return true;
        } else {
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No product selected");
            alert.setContentText("Please, select the line in the table");
            alert.show();
            MyLogger.logger.error("Ошибка при изменении статуса продукта. Не выделено!");
            return false;
        }
    }
    private boolean getRole(){
        if(this.API.getPosition().toLowerCase().equals("менеджер") |
                this.API.getPosition().toLowerCase().equals("программист")){
            return true;
        }
        Alert alert = AlertInfo.getWarningAlert(mainApp);
        alert.setHeaderText("You don't have permission for this operation");
        MyLogger.logger.error("Попытка изменить статус продукта без доступа");
        alert.show();
        MyLogger.logger.error("Ошибка при изменении статуса продукта. Невозможно!");
        return false;
    }
    private boolean getConfirm(){
        Alert alert = AlertInfo.getConfirmationAlert(mainApp);
        alert.setHeaderText("Update status");
        alert.setContentText("Are you ready to complete the operation?");
        Optional<ButtonType> option = alert.showAndWait();
        return option.get() == ButtonType.OK;
    }
}
