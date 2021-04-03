package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoPromocodeException;
import client.exception.ResponceStatusException;
import client.exception.TimeOutPromocodeException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

public class AddingPromocodeController {
    @FXML
    TextField textPromocode;
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
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
    public void initialize() {}

    @FXML public void addPr() {
        if(textPromocode.getText().equals("")){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No promocode selected");
            alert.setContentText("Please write the promocode");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поле ничего");
        }
        else {
            try{
                this.API.getPromocode(textPromocode.getText());
                this.mainApp.getRootController().updateCheck();
                Alert alert = AlertInfo.getOkAlert(mainApp);
                alert.setHeaderText("Successfully");
                alert.setContentText("Promocode has been added");
                alert.show();
                MyLogger.logger.info("Добавлен промокод");
                this.stage.close();
            }
            catch (NoPromocodeException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                MyLogger.logger.error("Промокод не найден");
                a.setHeaderText("Promocode not found");
                a.show();
            }
           catch (ResponceStatusException | JSONException e){
               Alert a = AlertInfo.getWarningAlert(mainApp);
               a.setHeaderText("Server not responding");
               MyLogger.logger.error("Промокод не найден");
               a.setContentText("Product hasn't been added");
               a.show();
               this.stage.close();
           } catch (TimeOutPromocodeException e) {
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("This promocode is invalid");
                MyLogger.logger.error("Промокод истек или не наступил");
                a.setContentText("Promocode hasn't been added");
                a.show();
            }
        }
    }

}
