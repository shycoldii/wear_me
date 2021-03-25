package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.ResponceStatusException;
import client.utils.MyLogger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddingToCheckController {
    @FXML TextField textArticul;
    @FXML ComboBox<String> combobox;
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    ObservableList<String> options =
            FXCollections.observableArrayList(
                    "XS",
                    "S",
                    "M", "L","XL"
            );
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }
    public void setCombobox(){
        this.combobox.setItems(options);
    }

    @FXML
    public void initialize() {}
    @FXML
    public void add(){
        if(textArticul.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(this.mainApp.getPrimaryStage());
            alert.setTitle("wear me");
            alert.setHeaderText("No articul selected");
            alert.setContentText("Please write the articul");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поле ничего");
        }
        else{
            try{
                Long articul = Long.parseLong(textArticul.getText());
                String text = combobox.getValue();
                if (text!=null){
                    this.API.getStoreProduct(articul,text);
                    this.mainApp.getRootController().updateCheck();
                    MyLogger.logger.info("Добавлен товар");
                    this.stage.close();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(this.mainApp.getPrimaryStage());
                    alert.setTitle("wear me");
                    alert.setHeaderText("No size selected");
                    alert.setContentText("Please select the size");
                    alert.showAndWait();
                    MyLogger.logger.error("Не введено в поле ничего");
                }

                //тащим данные из АПИ (Store Product) - храним в checkTable
                //находим - добавляем в список (при подтверждении чека меняем товарам статусы и сбрасываем список) + закрываем окно
                //не находим - алерт и сброс полей
            }

            catch(NumberFormatException e){
                MyLogger.logger.error("Введен не артикул");
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(this.mainApp.getPrimaryStage());
                alert.setTitle("wear me");
                alert.setHeaderText("This is not an articul");
                alert.setContentText("Please write the number");
                alert.showAndWait();
            }
            catch(ResponceStatusException e){
                Platform.runLater(mainApp::connectionError);
                this.stage.close();
            }
            catch(Exception e){
                MyLogger.logger.error("Ошибка при добавлении товара");
                e.printStackTrace();
                this.stage.close();
            }
        }

    }
}
