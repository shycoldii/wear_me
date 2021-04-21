package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при отсутствии товара
 */
public class NoStoreProductException extends Exception{
    public NoStoreProductException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
