package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при неудачной продаже
 */
public class SellProductException extends Exception{
    public SellProductException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
