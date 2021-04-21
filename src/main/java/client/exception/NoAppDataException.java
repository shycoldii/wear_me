package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

/**
 * Исключение при отсутствии данных
 */
public class NoAppDataException extends Exception{
    public NoAppDataException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
