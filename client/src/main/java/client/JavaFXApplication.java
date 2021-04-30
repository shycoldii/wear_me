package client;

import client.api.MyAPI;
import client.controller.*;
import client.exception.NoStoreProductException;
import client.utils.AlertInfo;
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

/**
 * Java FX Application
 */
public class JavaFXApplication extends Application {
    private Stage primaryStage;
    private RootController rootController;
    private MyAPI API;

    /**
     * Устанавливает значение API
     * @param API - экземпляр класса MyAPI
     */
    public void setAPI(MyAPI API) {
        this.API = API;
    }

    /**
     * Получает значение API
     * @return MyAPI - значение API
     */
    public MyAPI getAPI() {
        return API;
    }

    /**
     * Получает главную сцену
     * @return Stage primaryStage
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Запускает приложение и основную сцену,запускает окно авторизации
     * @param primaryStage - основная сцена
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("wear me");
        this.primaryStage.getIcons().add(new Image("client/images/icon.png"));
        initSignIn();
    }

    /**
     * Инициализирует сцену авторизации сотрудников
     */
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

    /**
     * Возвращает значение rootController
     * @return RootController rootController
     */
    public RootController getRootController() {
        return rootController;
    }

    /**
     * Инициализирует корневую часть приложения
     */
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

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке основной сцены");
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует сцену при отсутствии соединения с сервером
     */
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

    /**
     * Инициализирует сцену при отсутствии данных при подключении к серверу
     */
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

    /**
     * Инициализирует сцену добавления товаров в чек
     */
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
    /**
     * Точка входа в программу
     * @param args - аргументы
     */
    public static void main(String[] args) {
        MyLogger log = new MyLogger();
        if (log.deploy())
            launch(args);
    }

    /**
     * Инициализирует сцену добавления промокода
     */
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

    /**
     * Инициализирует сцену добавления карты лояльности
     */
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

    /**
     * Инициализирует сцену чека
     */
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

    /**
     * Инициализирует сцену возврата
     */
    public void initReturn() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/returnProduct.fxml"));
            Stage dialogStage = getLoader(loader);
            ReturnController controller = loader.getController();
            controller.setAPI(this.API);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно запуска возврата");
            dialogStage.show();
        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены возврата");
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует сцену информации о возврате
     */
    public void initReturnInfo() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/returnCheckInfo.fxml"));
            Stage dialogStage = getLoader(loader);
            ReturnInfoController controller = loader.getController();
            controller.setAPI(this.API);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                this.API.setReturnResult("");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно запуска возврата таблицы");
            controller.setTable(this.API.getProductData());
            dialogStage.showAndWait();

        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены возврата таблицы");
            e.printStackTrace();
        }
    }

    /**
     * Инициализирует сцену успешного возврата
     */
    public void initSucReturn() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/successReturn.fxml"));
            Stage dialogStage = getLoader(loader);
            ReturnInfoController controller = loader.getController();
            controller.setAPI(this.API);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            controller.getSuccessfullReturn();
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                this.API.setReturnResult("");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно успешного возврата");
            dialogStage.showAndWait();
        } catch (IOException e) {
            MyLogger.logger.error("Ошибка при загрузке сцены успешного возврата");
            e.printStackTrace();
        }
    }

    /**
     * Создает новую диалоговую сцену с предзагрузками
     * @param loader - лоадер
     * @return dialogStage - диалоговая сцена с предзагрузками
     * @throws IOException - может возникнуть исключение
     */
    public Stage getLoader(FXMLLoader loader) throws IOException {
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

    /**
     * Инициализирует сцену клиентов
     */
    public void initClients() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/client.fxml"));
            Stage dialogStage = getLoader(loader);
            ClientController controller = loader.getController();
            controller.setAPI(this.API);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            controller.loadData();
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно клиентов");
            dialogStage.show();
        }
        catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No client info");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену добавления клиента
     * @param clientController - контроллер клиента
     */
    public void initAddingClient(ClientController clientController) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingClient.fxml"));
            Stage dialogStage = getLoader(loader);
            AddingClientController controller = loader.getController();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setClientController(clientController);
            controller.setStage(dialogStage);
            controller.setAPI(this.API);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно клиентов");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("Can't load adding client");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену промокодов
     */
    public void initPromocodes() {
        try {
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(JavaFXApplication.class.getResource("views/promocode.fxml"));
            Stage dialogStage = getLoader(loader);
            PromocodeController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            controller.loadData();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно промокодов");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No promocodes info");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену добавления промокода
     * @param promocodeController - контроллер промокодов
     */
    public void initAddingPromocodeNew(PromocodeController promocodeController) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingPromocodeNew.fxml"));
            Stage dialogStage = getLoader(loader);
            AddingPromocodeNewController controller = loader.getController();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setPromocodeController(promocodeController);
            controller.setStage(dialogStage);
            controller.setAPI(this.API);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно добавления промокодов");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("Can't load adding promocodes");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену офисов
     */
    public void initOffices() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/office.fxml"));
            Stage dialogStage = getLoader(loader);
            OfficeController controller = loader.getController();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setAPI(this.API);
            controller.setMainApp(this);
            controller.setStage(dialogStage);
            controller.loadData();
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно офисов");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No offices info");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену складов
     */
    public void initStock() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/stock.fxml"));
            Stage dialogStage = getLoader(loader);
            StockController controller = loader.getController();
            controller.setAPI(this.API);
            controller.setMainApp(this);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setStage(dialogStage);
            controller.loadData();
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно склада");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No stock info");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует сцену добавления поставщика
     * @param stockController - контроллер склада
     */
    public void initAddingSupplier(StockController stockController) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingSupplier.fxml"));
            Stage dialogStage = getLoader(loader);
            AddingSupplierController controller = loader.getController();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setStockController(stockController);
            controller.setStage(dialogStage);
            controller.setAPI(this.API);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно поставщиков");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("Can't load adding supplier");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует первую сцену поставки
     * @param stockController - контроллер склада
     */
    public void initSupply1(StockController stockController){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingSupply1.fxml"));
            Stage dialogStage = this.getLoader(loader);
            AddingSupply1Controller controller = loader.getController();
            controller.setMainApp(this);
            controller.setStockController(stockController);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setStage(dialogStage);
            controller.setAPI(this.API);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно 1 стадии поставки");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No info about supply-1");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует вотрую сцену  поставки
     * @param addingSupplyController - контроллер первой стадии поставки
     */
    public void initSupply2(AddingSupply1Controller addingSupplyController) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/addingSupply2.fxml"));
            Stage dialogStage = this.getLoader(loader);
            AddingSupply2Controller controller = loader.getController();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            controller.setMainApp(this);
            controller.setSupply1Controller(addingSupplyController);
            controller.setStage(dialogStage);
            controller.setAPI(this.API);
            controller.loadData();
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно 2 стадии поставки");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No info about supply-2");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует информацию для сотрудников
     */
    public void initInfo() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/info.fxml"));
            Stage dialogStage = this.getLoader(loader);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно информации");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No info about information");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует окно статистики
     */
    public void initStat() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/stats.fxml"));
            Stage dialogStage = this.getLoader(loader);
            StatController controller = loader.getController();

            controller.setAPI(this.API);
            controller.setStage(dialogStage);
            controller.setMainApp(this);
            controller.loadData();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно статистики");
            dialogStage.show();
        }
        catch(NoStoreProductException e){
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No info about stats");
            a.setContentText("Server not responding");
            e.printStackTrace();
            a.show();
        }
        catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("Client Error");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }

    /**
     * Инициализирует окно информации об авторе
     */
    public void initAboutMe() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(JavaFXApplication.class.getResource("views/aboutMe.fxml"));
            Stage dialogStage = this.getLoader(loader);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setOnCloseRequest(we -> {
                MyLogger.logger.info("Окно закрыто");
                dialogStage.close();
            });
            MyLogger.logger.info("Открыто окно об авторе");
            dialogStage.show();
        } catch (Exception e) {
            MyLogger.logger.error(e.getMessage());
            Alert a = AlertInfo.getWarningAlert(this);
            a.setHeaderText("No info about author");
            a.setContentText(e.getMessage());
            e.printStackTrace();
            a.show();
        }
    }
}
