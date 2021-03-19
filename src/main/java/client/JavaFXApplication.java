package client;

import client.api.MyAPI;
import client.controller.SignInController;
import client.utils.MyLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;


public class JavaFXApplication extends Application {
    private Stage primaryStage;

    private MyAPI API;

    public void setAPI(MyAPI API) {
        this.API = API;
    }

    public MyAPI getAPI() {
        return API;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("store app");
        this.primaryStage.getIcons().add(new Image("client/images/icon.png"));
        initSignIn();
    }

    public void initSignIn() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/signIn.fxml"));
            VBox rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
            SignInController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены авторизации");
            e.printStackTrace();
        }
    }
    public void initRoot() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/root.fxml"));
            AnchorPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            this.primaryStage.close();
            this.primaryStage.setScene(scene);
            this.primaryStage.setMaximized(true);
            this.primaryStage.show();
            //root controller

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке основной сцены");
            e.printStackTrace();
        }
    }
    public void connectionError() {
        try {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.initOwner(this.getPrimaryStage());
            a.setTitle("store app");
            DialogPane dialogPane = a.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("styles/ConnectionError.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            a.setHeaderText("Server not responding");
            a.setContentText("Login failed");
            a.show();
        }
         catch (NullPointerException e) {
             MyLogger.logger.error("Ошибка при загрузке сцены ошибки подключения");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyLogger log = new MyLogger();
        if (log.deploy())
            launch(args);
    }
}
