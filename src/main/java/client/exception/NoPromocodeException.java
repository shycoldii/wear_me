package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class NoPromocodeException extends Exception{
    public NoPromocodeException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
