package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при недействительных промокодах
 */
public class TimeOutPromocodeException extends Exception{
    public TimeOutPromocodeException (JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
