package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.utils.CheckStructure;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import org.json.JSONException;
import java.time.LocalDate;

/**
 * Контроллер для чека
 */
public class CheckController {
    @FXML private Label checkInfo;
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;

    /**
     * Устанавливает значение сцены
     * @param dialogStage - сцена
     */
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    /**
     * Устанавливает значение API
     * @param API - апи
     */
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    /**
     * Устанавливает значение главному приложению
     * @param mainApp - главное приложение
     */
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML private TableColumn<CheckStructure, Integer> articul;
    @FXML private TableColumn<CheckStructure,Integer> price;
    @FXML private TableColumn<CheckStructure,String> name;
    @FXML private TableColumn<CheckStructure,String> color;
    @FXML private TableColumn<CheckStructure,Integer> amount;
    @FXML private TableColumn<CheckStructure,Integer> total;
    @FXML private javafx.scene.control.TableView<CheckStructure> checkTable;

    /**
     * Инициализация первичных объектов
     */
    @FXML
    public void initialize() {
        articul.setCellValueFactory(cellData -> cellData.getValue().articulProperty().asObject());
        name.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        color.setCellValueFactory(cellData -> cellData.getValue().colorProperty());
        total.setCellValueFactory(cellData -> cellData.getValue().totalProperty().asObject());
        price.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        amount.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());
    }

    /**
     * Устанавливает значения в таблице при нажатии кнопки
     * @throws JSONException - при работе с JSON-объектом
     */
    public void setItems() throws JSONException {
        this.checkTable.setItems(this.API.getCheckData());
        StringBuilder text = new StringBuilder("Office: " + this.API.getAddress() + "\n");
        String[] emp = this.API.getEmployeeInfo().split(" ");
        StringBuilder res = new StringBuilder();
        for(int i=0;i<emp.length;i++){
            if (i!=0){
               res.append(emp[i]).append(" ");
            }
        }

               text.append("Cashier: ").append(res).append("\n").append("Date: ").append(LocalDate.now().toString()).append("\n");
        text.append("-".repeat(this.API.getEmployeeInfo().length()+10));

        if(this.API.getClientId() != null){
            text.append("\nLoyalty card ID:");
            text.append(this.API.getClientId());
            text.append("\nBonuses awarded: ");
            text.append(this.API.getCurrentBonuses());
            text.append("\nBalance: ");
            text.append(this.API.getBonuses() + this.API.getCurrentBonuses());
            text.append("\n");
            text.append("-".repeat(this.API.getEmployeeInfo().length()+10));
        }
        text.append("\nCheck ID: ");
        text.append(this.API.getUpdatedCheck().get("id"));
        text.append("\nDiscount: ").append(this.API.getPriceWithDiscount());
        text.append("\n");
        text.append("-".repeat(this.API.getEmployeeInfo().length()+10));
        text.append("\n");
        text.append("Total: ").append(this.API.getTotalPrice() - this.API.getPriceWithDiscount());
        this.checkInfo.setText(text.toString());
        MyLogger.logger.info("Выведена информация о покупке");
    }

    /**
     * Закрывает окно печати чека при нажатии кнопки
     */
    @FXML
    public void okClicked(){
        this.mainApp.getRootController().createCheck();
        MyLogger.logger.info("Закрыто окно печати чека");
        this.stage.close();
    }
}
