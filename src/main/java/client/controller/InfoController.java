package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InfoController {
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
    public void initialize() {
        this.mainInfo.setText("\"wear me\" - интернет-магазин московского бренда одежды, включающий в себя модели для девушек и женщин. \n "
                );
        this.mainInfoHeader.setText("\"wear me\" - это о комфорте, качестве и минимализме.\n");
        this.info.setText(
                "Информация кассирам:\n" +
                "\n" +
                "При подкреплении карты клиента невозможно использовать промокод, как и в обратную сторону.\n" +
                "Не забывайте предлагать клиентам создавать их учетные записи.\n" +
                "Добавлять поставку можно только менеджерам. Понижение и повышение статуса товара возможно во всех случаях, кроме присутствия чека.\nДобавление поставщика происходит перед поставкой. Добавление промокодов доступно только менеджерам.\n" +
                "\n" +
                "Информация о картах:\n" +
                "5% 500-1000\n" +
                "10% 1000-5000\n" +
                "15% 5000-10000\n" +
                "20% >10000\n" +
                "\n" +
                "Начисление бонусов происходит автоматически при покупке.");
    }

    @FXML
    Label info;
    @FXML Label mainInfo;
    @FXML Label mainInfoHeader;
}
