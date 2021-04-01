package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.SellProductException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import client.utils.ProductStructure;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.web.server.ResponseStatusException;

public class ReturnInfoController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    @FXML
    private TableView<ProductStructure> productTable;
    @FXML private Label total;
    @FXML private TableColumn<ProductStructure, Long> idColumn;
    @FXML private TableColumn<ProductStructure, Integer> articulColumn;
    @FXML private TableColumn<ProductStructure,Integer> priceColumn;
    @FXML private TableColumn<ProductStructure,String> nameColumn;
    @FXML private TableColumn<ProductStructure,String> colorColumn;
    @FXML private TableColumn<ProductStructure,String> sizeColumn;
    @FXML private Label confReturn;
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    public void setTable(
            ObservableList<ProductStructure> productTable) {
        this.productTable.setItems(productTable);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idLabelProperty().asObject());
        articulColumn.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        colorColumn.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
    }

    @FXML
    public void initialize() {
    }
    @FXML
    private void returnProduct() {
        int selectedIndex = this.productTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            try{
                Long id = this.productTable.getItems().get(selectedIndex).getIdLabel();
                this.API.returnProduct(this.API.getCheckId(),id);
                MyLogger.logger.info("Возвращен продукт из таблицы");
                this.stage.close();
                this.mainApp.initSucReturn();
            }
            catch (ResponseStatusException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Server not responding");
                a.setContentText("Return hasn't been started");
                a.show();
                this.API.setReturnResult("");
                this.stage.close();
            }
            catch(SellProductException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Failed");
                a.setContentText("Could not return this item");
                this.API.setReturnResult("");
                a.show();
            }
            catch (Exception e){
                MyLogger.logger.error(e.getMessage());
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Client error");
                a.setContentText(e.getMessage());
                this.API.setReturnResult("");
                a.show();
                this.stage.close();
            }

        } else {
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setHeaderText("No product selected");
            alert.setContentText("Please, select the line in the table");
            alert.show();
            MyLogger.logger.error("Ошибка при возврате продукта. Не выделено!");
        }
    }
    public void getSuccessfullReturn(){

        this.confReturn.setText(this.API.getReturnResult());
        this.total.setText(this.API.getTotalResult());
    }

}
