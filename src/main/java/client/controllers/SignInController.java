package client.controllers;

import client.validators.CustomValidator;
import client.validators.ValidationManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.jfoenix.validation.RequiredFieldValidator;
import server.service.EmployeeService;

public class SignInController {
    @FXML private JFXTextField emailTextField;
    @FXML private JFXPasswordField passwordField;
    private RequiredFieldValidator requiredValidator;
    private CustomValidator emailValidator;
    private CustomValidator passwordValidator;
    public SignInController() {
    }
    @FXML
    public void initialize() {
        requiredValidator = new RequiredFieldValidator();
        emailValidator    = new CustomValidator("This account doesn't exist.");
        passwordValidator = new CustomValidator("Incorrect password.");

        ValidationManager.addValidator(true, requiredValidator, emailTextField, passwordField);
        ValidationManager.addValidator(true, emailValidator, emailTextField);
        ValidationManager.addValidator(true, passwordValidator, passwordField);
    }
    public void login() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL("http://localhost:8080/shop/clients/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (var reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        System.out.println(result);
    }
    @FXML
    public void handleSignInButtonAction(ActionEvent actionEvent) throws IOException {

        emailTextField.validate();
        passwordField.validate();

        if (!requiredValidator.getHasErrors()) {
            emailValidator.showMessage();
            passwordValidator.showMessage();
            /*
            ЕЩЕ ПРОЧЕКАТЬ НА ПУСТЫЕ ПОЛЯ
            if (!EmployeeService.is_login(1L)) {
                emailValidator.showMessage();
                //далее типо если пароль неверный
            } else if (!EmployeeService.is_password(passwordField.getText())) {
                passwordValidator.showMessage();
            } else {
                //запоминание
                resetSignInView();
                //новая стадия
                System.out.println("все норм");
            }

             */
        }

    }
    private void resetSignInView() {
        emailTextField.setText(null);
        passwordField.setText(null);
    };

}
