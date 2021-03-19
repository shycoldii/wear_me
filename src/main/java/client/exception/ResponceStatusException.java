package client.exception;


import client.JavaFXApplication;
import client.utils.MyLogger;
import javafx.application.Platform;

public class ResponceStatusException extends Exception{
         public ResponceStatusException(JavaFXApplication mainApp, String errorMessage){
             super(errorMessage);
             MyLogger.logger.error(errorMessage);
         }
}
