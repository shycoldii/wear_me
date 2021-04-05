package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class NoOfficeException extends Exception{
    public NoOfficeException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
