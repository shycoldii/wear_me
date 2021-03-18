package client.controller;

import client.JavaFXApplication;
import client.service.SignInService;
import client.validator.CustomValidator;
import client.validator.ValidationManager;
import client.validator.WordValidator;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import java.io.IOException;
import com.jfoenix.validation.RequiredFieldValidator;

public class SignInController {
    @FXML private JFXTextField emailField;
    @FXML private JFXPasswordField passwordField;
    private RequiredFieldValidator requiredValidator;
    private CustomValidator emailValidator;
    private CustomValidator passwordValidator;
    private Long employeeId;
    private JavaFXApplication mainApp;

    @FXML
    public void initialize() {
        this.requiredValidator = new RequiredFieldValidator();
        this.emailValidator    = new CustomValidator("Incorrect login or account doesn't exist");
        this.passwordValidator = new CustomValidator("Incorrect password");
        ValidationManager.addValidator(true, requiredValidator, emailField, passwordField);
        ValidationManager.addValidator(true, emailValidator, emailField);
        ValidationManager.addValidator(true, passwordValidator, passwordField);
    }

    @FXML
    public void handleSignInButtonAction() throws IOException {
        emailField.validate();
        passwordField.validate();
        if (!requiredValidator.getHasErrors()) {
            if(!passwordField.getText().isEmpty() && !emailField.getText().isEmpty()){
                if(!WordValidator.check(emailField.getText())){
                    emailValidator.showMessage();
                    if(!WordValidator.check(passwordField.getText())){
                        passwordValidator.showMessage();
                    }
                }
                else if(!WordValidator.check(passwordField.getText())){
                    passwordValidator.showMessage();
                }
                else if(SignInService.is_email(emailField.getText())){
                    Long res = SignInService.is_password(emailField.getText(),passwordField.getText());
                    if(res == null){
                        passwordValidator.showMessage();
                    }
                    else{
                        this.employeeId = res;
                        resetSignInView();
                        this.mainApp.initRoot();
                    }
                }
                else{
                    emailValidator.showMessage();
                }
            }
        }
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    private void resetSignInView() {
        emailField.setText(null);
        passwordField.setText(null);
    };

}
