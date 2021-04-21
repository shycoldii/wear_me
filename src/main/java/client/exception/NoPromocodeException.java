package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при отсутствии промокода
 */
public class NoPromocodeException extends Exception{
    public NoPromocodeException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
