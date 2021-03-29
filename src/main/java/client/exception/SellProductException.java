package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class SellProductException extends Exception{
    public SellProductException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
