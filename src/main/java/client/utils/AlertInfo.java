package client.utils;

import client.JavaFXApplication;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Modality;

public class AlertInfo {
    public static Alert getWarningAlert(JavaFXApplication mainApp){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                AlertInfo.class.getResource("../styles/ConnectionError.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
    public static Alert getOkAlert(JavaFXApplication mainApp) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                AlertInfo.class.getResource("../styles/ConnectionError.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }
    public static Alert getConfirmationAlert(JavaFXApplication mainApp) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle("wear me");
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
                AlertInfo.class.getResource("../styles/ConnectionError.css").toExternalForm());
        dialogPane.getStyleClass().add("myDialog");
        return alert;
    }

}
