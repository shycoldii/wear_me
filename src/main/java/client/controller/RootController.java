package client.controller;
import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoAppDataException;
import client.utils.CheckStructure;
import client.utils.MyLogger;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.json.JSONException;

public class RootController {
    @FXML
    private Label address;
    @FXML private TableColumn<CheckStructure, Integer> articulColumn;
    @FXML private TableColumn<CheckStructure,String> nameColumn;
    @FXML private TableColumn<CheckStructure,Integer> priceColumn;
    @FXML private TableColumn<CheckStructure,Integer> amountColumn;
    @FXML private TableColumn<CheckStructure,Integer> totalColumn;
    @FXML
    private Label employeeLabel;
    @FXML private Label addLabel;
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
        this.checkTable.setItems(this.API.getCheckData());
    }
}
