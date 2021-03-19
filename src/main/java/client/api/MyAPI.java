package client.api;

import client.JavaFXApplication;
import client.exception.ResponceStatusException;
import client.utils.HTTPRequest;
import client.utils.MyLogger;

import java.net.URLEncoder;

public class MyAPI {
    private JavaFXApplication mainApp;
    private boolean isAuthenticated=false;
    private Long employeeId;

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public MyAPI(JavaFXApplication mainApp){
        this.mainApp = mainApp;
        MyLogger.logger.info("Инициализирован MyAPI");
    }
    //блок SIGN IN
    public Boolean is_email(String email) throws ResponceStatusException {
        String url = "http://localhost:8080/shop/login/"+ URLEncoder.encode(email);
        String response = HTTPRequest.Get(url);
        if(response!=null){
            return Boolean.parseBoolean(response);
        }
        this.isAuthenticated = false;
        throw  new ResponceStatusException(this.mainApp, "Проверка на email - пустой ответ от сервера");
    }
    public  Long is_password(String email,String password) throws ResponceStatusException {
        String url = "http://localhost:8080/shop/login/"+URLEncoder.encode(email)+"/"+password.hashCode();
        String response = HTTPRequest.Get(url);
        if(response!=null){
            if(response.equals("")){
                return null;
            }
            this.isAuthenticated = true;
            return Long.parseLong(response);
        }
        this.isAuthenticated = false;
        throw new ResponceStatusException(this.mainApp, "Проверка на password - пустой ответ от сервера");
    }


}
