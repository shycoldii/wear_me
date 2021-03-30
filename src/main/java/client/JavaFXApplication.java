package client;

import client.api.MyAPI;
import client.controller.*;
import client.utils.MyLogger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.JSONException;

import java.io.IOException;


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
            RootController controller = loader.getController();
            controller.setMainApp(this);
            controller.setAPI(this.API);
            controller.setView();
            this.rootController = controller;

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
            a.initModality(Modality.APPLICATION_MODAL);
            a.showAndWait();
        } catch (NullPointerException e) {
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
            a.initModality(Modality.APPLICATION_MODAL);
            a.showAndWait();
            this.primaryStage.close();
        } catch (NullPointerException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены ошибки подключения");
            e.printStackTrace();
        }
    }


    public void initAddingToCheck() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingToCheck.fxml"));
            Stage dialogStage = getLoader(loader);
            AddingToCheckController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            controller.setCombobox();
            controller.setStage(dialogStage);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            MyLogger.logger.info("Запущено окно добавления товара в чек");
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });

            dialogStage.showAndWait();

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

    public void initAddingPromocode() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingPromocode.fxml"));
            Stage dialogStage = getLoader(loader);
            AddingPromocodeController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setStage(dialogStage);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно промокода");
            dialogStage.showAndWait();

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены добавления промокода");
            e.printStackTrace();
        }
    }

    public void initAddingLoyaltyCard() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingLoyaltyCard.fxml"));
            Stage dialogStage = this.getLoader(loader);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            AddingLoyaltyCardController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно добавления карты клиента");
            dialogStage.showAndWait();

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены добавления карты клиента");
            e.printStackTrace();
        }
    }

    public void initCheck() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/check.fxml"));
            Stage dialogStage = getLoader(loader);
            CheckController controller = loader.getController();
            controller.setAPI(this.API);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                this.getRootController().createCheck();
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно запуска чека");
            controller.setItems();
            dialogStage.showAndWait();
        } catch (IOException | JSONException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены чека");
            e.printStackTrace();
        }
    }

    private Stage getLoader(FXMLLoader loader) throws IOException {
        AnchorPane rootLayout = loader.load();
        Stage dialogStage = new Stage();
        dialogStage.getIcons().add(new Image("client/images/icon.png"));
        dialogStage.setTitle("wear me");
        dialogStage.initOwner(this.primaryStage);
        dialogStage.setResizable(false);
        Scene scene = new Scene(rootLayout);
        dialogStage.setScene(scene);
        return dialogStage;
    }
}
