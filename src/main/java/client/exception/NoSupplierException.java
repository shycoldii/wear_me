package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при отсутствии поставщика
 */
public class NoSupplierException extends Exception{
    public NoSupplierException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
