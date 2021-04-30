package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

/**
 * Исключение при отсутствии клиента
 */
public class NoClientException extends Exception{
    /**
     * Инициализирует исключение
     * @param mainApp - главное приложение
     * @param errorMessage - сообщение
     */
    public NoClientException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
