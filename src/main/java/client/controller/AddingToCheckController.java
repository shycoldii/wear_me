package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoColorException;
import client.exception.NoStoreProductException;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddingToCheckController {
    @FXML TextField textArticul;
    @FXML ComboBox<String> combobox;
    @FXML TextField textColor;
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
            Alert alert = getAlert();
            alert.setHeaderText("No articul selected");
            alert.setContentText("Please write the articul");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поле ничего");
        }
        else{
            try{
                Integer articul = Integer.parseInt(textArticul.getText());
                String text = combobox.getValue();
                if (text!=null){
                    String color = textColor.getText();
                    if(!color.equals("")){
                        this.API.getStoreProduct(articul,text,color);
                        this.mainApp.getRootController().updateCheck();
                        MyLogger.logger.info("Добавлен товар");
                        this.stage.close();
                    }
                    else{
                        Alert alert = getAlert();
                        alert.setHeaderText("No color selected");
                        alert.setContentText("Please write the color");
                        alert.showAndWait();
                        MyLogger.logger.error("Не введено в поле ничего");
                    }
                }
                else{
                    Alert alert = getAlert();
                    alert.setHeaderText("No size selected");
                    alert.setContentText("Please select the size");
                    alert.showAndWait();
                    MyLogger.logger.error("Не введено в поле ничего");
                }
                //TODO: находим - добавляем в список (при подтверждении чека меняем товарам статусы и сбрасываем список) + закрываем окно
            }
            catch(NoColorException e){
                Alert alert = getAlert();
                alert.setHeaderText("Store product with current color wasn't found");
                Set<String> set = new HashSet<String>(this.API.getListOfColors());
                StringBuilder res = new StringBuilder();
                int r = 0;
                for(String s: set){
                    res.append(s);
                    if(r==set.size()-1){
                        res.append(".");
                    }
                    else{
                        res.append(", ");
                    }
                    r+=1;
                }
                alert.setContentText("Available colors: "+res.toString());
                alert.show();

            }
            catch (NoStoreProductException e){
                Alert alert = getAlert();
                alert.setHeaderText("Store product wasn't found");
                alert.setContentText("Please change options");
                alert.showAndWait();
            }

            catch(NumberFormatException e){
                MyLogger.logger.error("Введен не артикул");
                Alert alert = getAlert();
                alert.setHeaderText("This is not an articul");
                alert.setContentText("Please write the number");
                alert.showAndWait();
            }
            catch(ResponceStatusException e){
                Alert a = getAlert();
                a.setHeaderText("Server not responding");
                a.setContentText("Product hasn't been added");
                a.show();
                this.stage.close();
            }
            catch(Exception e){
                MyLogger.logger.error("Ошибка при добавлении товара");
                e.printStackTrace();
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
