package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoClientException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Контроллер для добавления клиента
 */
public class AddingClientController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private ClientController clientController;

    /**
     * Устанавливает значение контроллеру клиента
     * @param clientController - контроллер
     */
    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
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

    /**
     * Создает клиента при нажатии кнопки create
     */
    @FXML
    private void create() {
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
              if(NumberValidator.isNumber(phoneField.getText()) & NumberValidator.isRussianNumber(phoneField.getText())){
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
