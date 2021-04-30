package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoStoreProductException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import client.validator.NumberValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

/**
 * Контроллер добавления поставщика
 */
public class AddingSupplierController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private StockController stockController;
    /**
     * Устанавливает значение контроллеру склада
     * @param stockControllerr - контроллер
     */
    public void setStockController(StockController stockControllerr) {
        this.stockController = stockControllerr;
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
    /**
     * Устанавливает значение сцены
     * @param dialogStage - сцена
     */
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneNumberField;


    /**
     * Создает поставщика при нажатии кнопки
     */
    @FXML void create(){
        if(!nameField.getText().equals("") &
                !phoneNumberField.getText().equals("") &
                !emailField.getText().equals("")
        ){
            if(NumberValidator.isNumber(phoneNumberField.getText()) & NumberValidator.isRussianNumber(phoneNumberField.getText())){
                if(EmailValidator.getInstance().isValid(emailField.getText())){
                    try{
                        JSONObject jsonSupplier = new JSONObject();
                        jsonSupplier.put("name", nameField.getText());
                        jsonSupplier.put("email", emailField.getText());
                        jsonSupplier.put("phoneNumber", phoneNumberField.getText());
                        boolean responce = this.API.createSupplier(jsonSupplier);
                        Alert a;
                        if(responce){
                            this.stage.close();
                            a = AlertInfo.getOkAlert(mainApp);
                            a.setHeaderText("The supplier was created");
                            this.stockController.loadData();
                            MyLogger.logger.info("Добавлен новый поставщик");
                        }
                        else{
                            a = AlertInfo.getWarningAlert(mainApp);
                            a.setHeaderText("The supplier wasn't created. Maybe it already exists?");
                            MyLogger.logger.error("Не удалось добавить поставщика");
                        }
                        a.show();
                    }
                    catch(IOException e){
                        e.printStackTrace();
                        Alert a = AlertInfo.getWarningAlert(mainApp);
                        a.setHeaderText("Server not responding");
                        MyLogger.logger.error("Сервер не отвечает при добавлении поставщика");
                        a.setContentText("Supplier hasn't been added");
                        a.show();
                    }
                    catch(JSONException  | NoStoreProductException e){
                        e.printStackTrace();
                        Alert a = AlertInfo.getWarningAlert(mainApp);
                        a.setHeaderText("Client error");
                        MyLogger.logger.error(e.getMessage());
                        a.setContentText(e.getMessage());
                        a.show();
                        this.stage.close();
                    }
                }
                else{
                    Alert alert = AlertInfo.getWarningAlert(mainApp);
                    alert.setHeaderText("It isn't the email");
                    alert.showAndWait();
                    MyLogger.logger.error("Неправильный ввод электронной почты");
                }
            }
            else{
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.setHeaderText("It isn't the correct phone number");
                alert.showAndWait();
                MyLogger.logger.error("Неправильный ввод номера телефона");
            }

        }
        else{
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("Fill in all the fields except *");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поля ничего");
        }
    }
}
