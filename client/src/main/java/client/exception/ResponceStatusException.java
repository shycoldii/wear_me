package client.exception;


import client.JavaFXApplication;
import client.utils.MyLogger;
import javafx.application.Platform;
/**
 * Исключение при отсутствии подключения к серверу
 */
public class ResponceStatusException extends Exception{
    /**
     * Инициализирует исключение
     * @param mainApp - главное приложение
     * @param errorMessage - сообщение
     */
         public ResponceStatusException(JavaFXApplication mainApp, String errorMessage){
             super(errorMessage);
             MyLogger.logger.error(errorMessage);
         }
}
