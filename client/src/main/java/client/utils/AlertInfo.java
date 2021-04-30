package client.utils;

import client.JavaFXApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;

/**
 * Вспомогательный класс для отображения алертов
 */
public class AlertInfo {
    /**
     * Создает алерт-предупреждение
     * @param mainApp - экземпляр главного приложения
     * @return alert
     */
    public static Alert getWarningAlert(JavaFXApplication mainApp){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                String.valueOf(JavaFXApplication.class.getResource("styles/ConnectionError.css").toExternalForm()));
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
    /**
     * Создает успешный алерт
     * @param mainApp - экземпляр главного приложения
     * @return alert
     */
    public static Alert getOkAlert(JavaFXApplication mainApp) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("wear me");
        alert.initOwner(mainApp.getPrimaryStage());
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                String.valueOf(JavaFXApplication.class.getResource("styles/ConnectionError.css").toExternalForm()));
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
    /**
     * Создает алерт-подтверждение
     * @param mainApp - экземпляр главного приложения
     * @return alert
     */
    public static Alert getConfirmationAlert(JavaFXApplication mainApp) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                String.valueOf(JavaFXApplication.class.getResource("styles/ConnectionError.css").toExternalForm()));
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }

}
