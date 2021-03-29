package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoClientException;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import client.validator.NumberValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONException;

public class AddingLoyaltyCardController {
    @FXML
    TextField textEmail;
    @FXML
    TextField textPhoneNumber;
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

    @FXML
    public void add() {
        try{
            String phone = textPhoneNumber.getText();
            String email = textEmail.getText();
            if(phone.equals("") & email.equals("")){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.setHeaderText("Fields are empty");
                alert.setContentText("Please write the email / phone number");
                alert.show();
            }
            else{
                if(email.equals("")){
                    if( NumberValidator.isNumber(phone)){
                        this.API.getLoyaltyCard(phone.replaceAll("[^0-9]", ""),null);
                        this.mainApp.getRootController().updateCheck();
                        Alert alert = AlertInfo.getOkAlert(mainApp);
                        alert.setHeaderText("Successfully");
                        alert.setContentText("Loyalty card has been added");
                        alert.show();
                        MyLogger.logger.info("Прикреплена карта лояльности");
                        this.stage.close();
                    }
                    else{
                        Alert alert = AlertInfo.getWarningAlert(mainApp);
                        alert.setHeaderText("Number is incorrect");
                        alert.setContentText("Please write the correct number");
                        alert.show();
                    }
                }
                else if(phone.equals("")){
                    this.API.getLoyaltyCard(null,email);
                    this.mainApp.getRootController().updateCheck();
                    Alert alert = AlertInfo.getOkAlert(mainApp);
                    alert.setHeaderText("Successfully");
                    alert.setContentText("Loyalty card has been added");
                    alert.show();
                    MyLogger.logger.info("Прикреплена карта лояльности");
                    this.stage.close();
                }
                if(!phone.equals("") & !email.equals("")){
                    if( NumberValidator.isNumber(phone)){
                        this.API.getLoyaltyCard(phone.replaceAll("[^0-9]", ""),email);
                        this.mainApp.getRootController().updateCheck();
                        Alert alert = AlertInfo.getOkAlert(mainApp);
                        alert.setHeaderText("Successfully");
                        alert.setContentText("Loyalty card has been added");
                        alert.show();
                        MyLogger.logger.info("Прикреплена карта лояльности");
                        this.stage.close();
                    }
                    else{
                        Alert alert = AlertInfo.getWarningAlert(mainApp);
                        alert.setHeaderText("Number is incorrect");
                        alert.setContentText("Please write the correct number");
                        alert.show();
                    }
                }

            }

        }
        catch(ResponceStatusException | JSONException e){
            e.printStackTrace();
            Alert a = AlertInfo.getWarningAlert(mainApp);
            a.setHeaderText("Server not responding");
            a.setContentText("Client hasn't been added");
            a.show();
            this.stage.close();
        }
        catch(NoClientException e){
            Alert a = AlertInfo.getWarningAlert(mainApp);
            a.setHeaderText("Client wasn't found");
            a.setContentText("Check the email or phone number");
            a.show();
            this.stage.close();
        }

    }




}
