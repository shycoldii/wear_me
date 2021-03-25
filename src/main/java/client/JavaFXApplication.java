package client;

import client.api.MyAPI;
import client.controller.AddingToCheckController;
import client.controller.RootController;
import client.controller.SignInController;
import client.utils.CheckStructure;
import client.utils.MyLogger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.json.JSONException;
import server.model.Check;

import java.io.IOException;
import java.util.ArrayList;


public class JavaFXApplication extends Application {
    private Stage primaryStage;
    private RootController rootController;
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
        this.primaryStage.setTitle("wear me");
        this.primaryStage.getIcons().add(new Image("client/images/icon.png"));
        initSignIn();
    }

    public void initSignIn() {
        try {
            this.primaryStage.setMaximized(false);
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

    public RootController getRootController() {
        return rootController;
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
            this.primaryStage.setResizable(false);
            MyLogger.logger.info("Запущено главное окно");
            this.primaryStage.show();
            //for(int i =0;i<50;i++){
             //   this.updateCheckData(new CheckStructure(1L,"dfgh",120,200,300));
            //}

            RootController controller = loader.getController();
            controller.setMainApp(this);
            controller.setAPI(this.API);
            controller.setView();
            this.rootController = controller;
            //root controller

        } catch (IOException | JSONException e) {
            MyLogger.logger.error("Ошибка при загрузке основной сцены");
            e.printStackTrace();
        }
    }
    public void connectionError() {
        try {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.initOwner(this.getPrimaryStage());
            a.setTitle("wear me");
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
    public void noDataError() {
        try {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.initOwner(this.getPrimaryStage());
            a.setTitle("store app");
            DialogPane dialogPane = a.getDialogPane();
            dialogPane.getStylesheets().add(
                    getClass().getResource("styles/ConnectionError.css").toExternalForm());
            dialogPane.getStyleClass().add("myDialog");
            a.setHeaderText("Server not responding");
            a.setContentText("No data for app");
            a.show();
            this.primaryStage.close();
        }
        catch (NullPointerException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены ошибки подключения");
            e.printStackTrace();
        }
    }


    public void initAddingToCheck(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingToCheck.fxml"));
            AnchorPane rootLayout = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("wear me");
            dialogStage.initOwner(this.primaryStage);
            dialogStage.setResizable(false);
            Scene scene = new Scene(rootLayout);
            dialogStage.setScene(scene);
            AddingToCheckController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            controller.setCombobox();
            controller.setStage(dialogStage);
            MyLogger.logger.info("Запущено окно добавления товара в чек");
            dialogStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    MyLogger.logger.info("Окно закрыто");
                    dialogStage.close();
                }
            });
            dialogStage.show();

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены добавления товара в чек");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyLogger log = new MyLogger();
        if (log.deploy())
            launch(args);
    }
}
