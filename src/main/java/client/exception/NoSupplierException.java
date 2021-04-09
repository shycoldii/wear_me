package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class NoSupplierException extends Exception{
    public NoSupplierException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
