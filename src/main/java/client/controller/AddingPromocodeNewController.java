package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoPromocodeException;
import client.exception.ResponceStatusException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;
import server.model.Promocode;

import java.io.IOException;

public class AddingPromocodeNewController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    private PromocodeController promocodeController;
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

    public void setPromocodeController(PromocodeController promocodeController) {
        this.promocodeController = promocodeController;
    }
    @FXML
    TextField name;
    @FXML
    TextField discount;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;

    @FXML void add(){
        if(name.getText().equals("") | discount.getText().equals("") |
        startDate.getValue() == null | endDate.getValue() == null ){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("Fill in all the fields except");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поля ничего");
        }
        else{
            try{
                int dis = Integer.parseInt(this.discount.getText());
                if(dis>0 & dis<=100){
                    if(endDate.getValue().isAfter(startDate.getValue())){
                        JSONObject jsonpr = new JSONObject();
                        jsonpr.put("name",name.getText());
                        jsonpr.put("discount",discount.getText());
                        jsonpr.put("startDate",startDate.getValue());
                        jsonpr.put("endDate",endDate.getValue());
                        boolean response = this.API.postPromocode(jsonpr);
                        Alert a;
                        if(response){
                            this.stage.close();
                            a = AlertInfo.getOkAlert(mainApp);
                            a.setHeaderText("The promocode was created");
                            this.promocodeController.loadData();
                            MyLogger.logger.info("Добавлен новый промокод");
                        }
                        else{
                            a = AlertInfo.getWarningAlert(mainApp);
                            a.setHeaderText("The promocode wasn't created");
                            MyLogger.logger.error("Не удалось добавить промокод");
                        }
                        a.show();
                    }
                    else{
                        Alert alert = AlertInfo.getWarningAlert(mainApp);
                        alert.setHeaderText("Dates are incorrect");
                        alert.showAndWait();
                        MyLogger.logger.error("Некорректный ввод даты");
                    }
                }
                else{
                    Alert alert = AlertInfo.getWarningAlert(mainApp);
                    alert.setHeaderText("Incorrect discount value");
                    alert.showAndWait();
                    MyLogger.logger.error("Некорректный ввод значения скидки");
                }
            }
            catch (NoPromocodeException e){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.setHeaderText("A promocode with the same name already exists");
                alert.showAndWait();
                MyLogger.logger.error("Не удалось создать промокод");
            }
            catch(ResponceStatusException e){
                e.printStackTrace();
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Server not responding");
                MyLogger.logger.error("Сервер не отвечает при добавлении промокода");
                a.setContentText("Promocode hasn't been added");
                a.show();
            }
            catch(NumberFormatException e){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.setHeaderText("Discount is incorrect");
                alert.showAndWait();
                MyLogger.logger.error("Некорректный ввод скидки");
            }
            catch(Exception e){
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                e.printStackTrace();
                alert.setHeaderText("Client Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                MyLogger.logger.error(e.getMessage());
            }
        }
    }

}
