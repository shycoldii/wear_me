package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.ResponceStatusException;
import client.utils.MyLogger;
import client.validator.CustomValidator;
import client.validator.ValidationManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import com.jfoenix.validation.RequiredFieldValidator;

/**
 * Контроллер авторизации
 */
public class SignInController {
    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField passwordField;
    private RequiredFieldValidator requiredValidator;
    private CustomValidator emailValidator;
    private CustomValidator passwordValidator;
    private JavaFXApplication mainApp;
    private MyAPI API;

    /**
     * Инициализация объектов для авторизации
     */
    @FXML
    public void initialize() {
        this.requiredValidator = new RequiredFieldValidator();
        this.emailValidator    = new CustomValidator("Account doesn't exist");
        this.passwordValidator = new CustomValidator("Incorrect password");
        ValidationManager.addValidator(true, requiredValidator, emailField, passwordField);
        ValidationManager.addValidator(true, emailValidator, emailField);
        ValidationManager.addValidator(true, passwordValidator, passwordField);
    }

    /**
     * Авторизовывает сотрудника при нажатии кнопки
     */
    @FXML
    public void handleSignInButtonAction() {
        this.API = new MyAPI(this.mainApp);
        MyLogger.logger.info("Нажатие на кнопку авторизации работника");
        emailField.validate();
        passwordField.validate();
        if (!requiredValidator.getHasErrors()) {
            if(!passwordField.getText().isEmpty() && !emailField.getText().isEmpty()){
                    try{
                        if(API.isEmail(emailField.getText())){
                            Long res =this.API.isPassword(emailField.getText(),passwordField.getText());
                            if(res == null){
                                MyLogger.logger.info("Неправильный пароль при авторизации");
                                passwordValidator.showMessage();
                            }
                            else{
                                MyLogger.logger.info("Успешная авторизация");
                                this.API.setEmployeeId(res);
                                resetSignInView();
                                this.mainApp.setAPI(this.API);
                                this.mainApp.initRoot();
                            }
                        }
                        else{
                            MyLogger.logger.info("Не найден пользователь с такой почтой");
                            emailValidator.showMessage();
                        }
                    }
                    catch (ResponceStatusException e){
                        Platform.runLater(mainApp::connectionError);
                    }
            }
        }
    }
    /**
     * Устанавливает значение главному приложению
     * @param mainApp - главное приложение
     */
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Сбрасывает поля пароля и почты
     */
    private void resetSignInView() {
        emailField.setText(null);
        passwordField.setText(null);
    };

}
