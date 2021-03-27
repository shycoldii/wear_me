package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class NoClientException extends Exception{
    public NoClientException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
