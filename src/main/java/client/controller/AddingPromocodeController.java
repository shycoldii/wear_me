package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoPromocodeException;
import client.exception.ResponceStatusException;
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
            Alert alert = getAlert();
            alert.setHeaderText("No promocode selected");
            alert.setContentText("Please write the promocode");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поле ничего");
        }
        else {
            try{
                this.API.getPromocode(textPromocode.getText());
                this.mainApp.getRootController().updateCheck();
                this.stage.close();
            }
            catch (NoPromocodeException e){
                Alert a = getAlert();
                a.setHeaderText("Promocode not found");
                a.show();
            }
           catch (ResponceStatusException | JSONException e){
               Alert a = getAlert();
               a.setHeaderText("Server not responding");
               a.setContentText("Product hasn't been added");
               a.show();
               this.stage.close();
           }
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
}
