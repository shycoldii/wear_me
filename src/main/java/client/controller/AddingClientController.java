package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoClientException;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.ClientStructure;
import client.utils.MyLogger;
import client.validator.NumberValidator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.text.DateFormatter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddingClientController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private ClientController clientController;

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
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
    public void initialize() {}

    @FXML
    TextField secondNameField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField emailField;
    @FXML
    TextField phoneField;
    @FXML
    TextField patronymicField;
    @FXML
    TextField birthdayField;
    @FXML
    private void create() throws JSONException, NoClientException {
        if(!secondNameField.getText().equals("") &
                !firstNameField.getText().equals("") &
                !emailField.getText().equals("") &
                !phoneField.getText().equals("")
                ){
          if ( !firstNameField.getText().matches("[а-яА-Я]+") ||
                  !secondNameField.getText().matches("[а-яА-Я]+")){
              Alert alert = AlertInfo.getWarningAlert(mainApp);
              alert.setHeaderText("Check the fields for correctness");
              alert.setContentText("Check first name, last name");
              alert.showAndWait();
              MyLogger.logger.error("Неправильный ввод данных");
            }
          else{
              if (!patronymicField.getText().equals("")) {
                  if(!patronymicField.getText().matches("[а-яА-Я]+")){
                      Alert alert = AlertInfo.getWarningAlert(mainApp);
                      alert.setHeaderText("Check the fields for correctness");
                      alert.setContentText("Check patronymic");
                      alert.showAndWait();
                      MyLogger.logger.error("Неправильный ввод данных");
                  }
              }
              }
              if(NumberValidator.isNumber(phoneField.getText())){
                 for(ClientStructure c: this.API.getClients()){
                     if(c.getPhoneNumber().startsWith("+")){
                         if(c.getPhoneNumber().charAt(1) == '7' | c.getPhoneNumber().charAt(1) == '8'){
                             if(phoneField.getText().startsWith("+")){
                                 if(phoneField.getText().charAt(1) == '7' |phoneField.getText().charAt(1) == '8'){
                                     if(phoneField.getText().substring(1).equals(c.getPhoneNumber().substring(1))){
                                         Alert alert = AlertInfo.getWarningAlert(mainApp);
                                         alert.setHeaderText("It isn't the phone number");
                                         alert.showAndWait();
                                         MyLogger.logger.error("Неправильный ввод номера телефона");
                                     }
                                 }
                             }
                             if(phoneField.getText().charAt(1) == '7' |phoneField.getText().charAt(1) == '8'){
                                 if(phoneField.getText().substring(0).equals(c.getPhoneNumber().substring(1))){
                                     Alert alert = AlertInfo.getWarningAlert(mainApp);
                                     alert.setHeaderText("It isn't the phone number");
                                     alert.showAndWait();
                                     MyLogger.logger.error("Неправильный ввод номера телефона");
                                 }
                             }
                         }
                     }
                     if(c.getPhoneNumber().charAt(0) == '7' | c.getPhoneNumber().charAt(0) == '8'){
                         if(phoneField.getText().startsWith("+")){
                             if(phoneField.getText().charAt(1) == '7' |phoneField.getText().charAt(1) == '8'){
                                 if(phoneField.getText().substring(1).equals(c.getPhoneNumber())){
                                     Alert alert = AlertInfo.getWarningAlert(mainApp);
                                     alert.setHeaderText("It isn't the phone number");
                                     alert.showAndWait();
                                     MyLogger.logger.error("Неправильный ввод номера телефона");
                                 }
                             }
                         }
                         if(phoneField.getText().charAt(1) == '7' |phoneField.getText().charAt(1) == '8'){
                             if(phoneField.getText().equals(c.getPhoneNumber())){
                                 Alert alert = AlertInfo.getWarningAlert(mainApp);
                                 alert.setHeaderText("It isn't the phone number");
                                 alert.showAndWait();
                                 MyLogger.logger.error("Неправильный ввод номера телефона");
                             }
                         }
                     }
                 }
                 if(EmailValidator.getInstance().isValid(emailField.getText())){

                         try{
                             JSONObject jsonClient = new JSONObject();
                             jsonClient.put("firstName",firstNameField.getText());
                             jsonClient.put("secondName",secondNameField.getText());
                             jsonClient.put("patronymic",patronymicField.getText());
                             jsonClient.put("email",emailField.getText());
                             jsonClient.put("phoneNumber",phoneField.getText());
                             jsonClient.put("regDay", LocalDate.now());
                             jsonClient.put("numberOfBonuses",0);
                             DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                             jsonClient.put("birthday",DATE_TIME_FORMATTER.parse(birthdayField.getText(), LocalDate::from));
                             boolean responce = this.API.createClient(jsonClient);
                             Alert a;
                             if(responce){
                                 this.stage.close();
                                 a = AlertInfo.getOkAlert(mainApp);
                                 a.setHeaderText("The client was created");
                                 this.clientController.loadData();
                                 MyLogger.logger.info("Добавлен новый клиент");
                             }
                             else{
                                 a = AlertInfo.getWarningAlert(mainApp);
                                 a.setHeaderText("The client wasn't created. Maybe it already exists?");
                                 MyLogger.logger.error("Не удалось добавить клиента");
                             }
                             a.show();
                         }
                         catch(DateTimeParseException e){
                             Alert alert = AlertInfo.getWarningAlert(mainApp);
                             alert.setHeaderText("Incorrect form of birthday");
                             alert.showAndWait();
                             MyLogger.logger.error("Неправильный ввод даты рождения");
                         }
                         catch(IOException e){
                             e.printStackTrace();
                             Alert a = AlertInfo.getWarningAlert(mainApp);
                             a.setHeaderText("Server not responding");
                             MyLogger.logger.error("Сервер не отвечает при добавлении клиента");
                             a.setContentText("Client hasn't been added");
                             a.show();
                         }
                         catch(JSONException  | NoClientException e){
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
                  alert.setHeaderText("It isn't the phone number");
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
