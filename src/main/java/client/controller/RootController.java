package client.controller;
import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoAppDataException;
import client.utils.CheckStructure;
import client.utils.MyLogger;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.json.JSONException;

public class RootController {
    @FXML
    private Label address;
    @FXML private TableColumn<CheckStructure, Integer> articulColumn;
    @FXML private TableColumn<CheckStructure,String> nameColumn;
    @FXML private TableColumn<CheckStructure,Integer> priceColumn;
    @FXML private TableColumn<CheckStructure,Integer> amountColumn;
    @FXML private TableColumn<CheckStructure,Integer> totalColumn;
    @FXML private TableColumn<CheckStructure,String> colorColumn;
    @FXML private Label employeeLabel;
    @FXML private Label withDiscount;
    @FXML private Label withoutDiscount;
    @FXML private Label discount;
    @FXML private Label bonuses;
    @FXML private TableView<CheckStructure> checkTable;

    private JavaFXApplication mainApp;
    private MyAPI API;

    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void setAPI(MyAPI API) {
        this.API = API;
    }

    @FXML
    public void initialize() {
        articulColumn.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amountColumn.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    }
    @FXML
    public void logout(){
        this.mainApp.getPrimaryStage().close();
        this.mainApp.initSignIn();
        MyLogger.logger.info("Выход из учетной записи");
    }

    public Label getAddress() {
        return address;
    }

    public void setView() throws JSONException {
        try{
            API.getData();
            this.address.setText(API.getAddress());
            this.employeeLabel.setText(API.getEmployeeInfo());
        }
        catch (NoAppDataException e){
            Platform.runLater(mainApp::noDataError);
        }
       catch(Exception e){
            e.printStackTrace();
           MyLogger.logger.error("Ошибка при инициализации главного слоя");
       }
    }
    @FXML
    public void addCheck(){
        this.mainApp.initAddingToCheck();
    }
    public void updateCheck(){
        MyLogger.logger.info("Обновлен чек");
        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText(String.valueOf(this.API.getTotalPrice()));
        this.withDiscount.setText(String.valueOf(this.API.getTotalPrice()-Integer.parseInt(this.discount.getText())));
    }
    @FXML
    private void deleteProduct() {

        int selectedIndex = this.checkTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            int articul = this.checkTable.getItems().get(selectedIndex).getArticul();
            String color = this.checkTable.getItems().get(selectedIndex).getColor();
            this.API.deleteProduct(articul,color);
            this.checkTable.setItems(this.API.getCheckData());
            MyLogger.logger.info("Удален продукт из таблицы");
        } else {
            Alert alert = this.getAlert();
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No product selected");
            alert.setContentText("Please, select the line in the table");
            alert.show();
            MyLogger.logger.error("Ошибка при удалении продукта. Не выделено!");
        }
    }
    private Alert getAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(this.mainApp.getPrimaryStage());
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                getClass().getResource("../styles/ConnectionError.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
    @FXML
    private void createCheck(){
        this.API.deleteAllProducts();
        this.checkTable.setItems(this.API.getCheckData());
        this.withoutDiscount.setText("0");
        this.withDiscount.setText("0");
        this.discount.setText("0");
        this.bonuses.setText("0");
        MyLogger.logger.info("Создан новый чек");
    }
}
