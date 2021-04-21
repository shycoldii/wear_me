package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

/**
 * Исключение при отсутствии клиента
 */
public class NoClientException extends Exception{
    public NoClientException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
