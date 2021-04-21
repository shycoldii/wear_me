package client.controller;

import client.JavaFXApplication;
import client.api.MyAPI;
import client.exception.NoStoreProductException;
import client.utils.AlertInfo;
import client.utils.MyLogger;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.web.server.ResponseStatusException;

/**
 * Контроллер для возвратов
 */
public class ReturnController {
    private Stage stage;
    private JavaFXApplication mainApp;
    private MyAPI API;
    /**
     * Устанавливает значение сцены
     * @param dialogStage - сцена
     */
    public void setStage(Stage dialogStage) {
        this.stage = dialogStage;
    }
    /**
     * Устанавливает значение API
     * @param API - апи
     */
    public void setAPI(MyAPI API) {
        this.API = API;
    }
    /**
     * Устанавливает значение главному приложению
     * @param mainApp - главное приложение
     */
    public void setMainApp(JavaFXApplication mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
    }
    @FXML private TextField textCheckId;

    /**
     * Ищет чек по идентификатору при нажатии кнпоки
     */
    @FXML private void findCheckId(){
        if (textCheckId.getText().equals("")){
            Alert alert = AlertInfo.getWarningAlert(mainApp);
            alert.setHeaderText("No check id selected");
            alert.setContentText("Please write the check id");
            alert.showAndWait();
            MyLogger.logger.error("Не введено в поле ничего");
        }
        else{
            try{
                Long checkId = Long.parseLong(textCheckId.getText());
                this.API.setCheckId(checkId);
                this.API.getProductsByCheckId(checkId);
                this.stage.close();
                this.mainApp.initReturnInfo();

            }
            catch (NumberFormatException e) {
                MyLogger.logger.error("Введен не id");
                Alert alert = AlertInfo.getWarningAlert(mainApp);
                alert.setHeaderText("This isn't an check id!");
                alert.setContentText("Please write the number");
                alert.showAndWait();
            }
            catch (NoStoreProductException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Return hasn't been started");
                a.setContentText("Check with current id wasn't found or it's impossible");
                a.show();
            }
            catch (ResponseStatusException e){
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Server not responding");
                a.setContentText("Return hasn't been started");
                a.show();
                this.stage.close();
            }
            catch (Exception e){
                MyLogger.logger.error(e.getMessage());
                e.printStackTrace();
                Alert a = AlertInfo.getWarningAlert(mainApp);
                a.setHeaderText("Client error");
                a.setContentText(e.getMessage());
                a.show();
                this.stage.close();
            }

        }

    }

}
