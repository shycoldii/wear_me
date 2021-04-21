package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при отсутствии офиса
 */
public class NoOfficeException extends Exception{
    public NoOfficeException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
