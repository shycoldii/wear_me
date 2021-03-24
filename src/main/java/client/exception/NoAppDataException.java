package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class NoAppDataException extends Exception{
    public NoAppDataException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
