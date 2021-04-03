package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

public class TimeOutPromocodeException extends Exception{
    public TimeOutPromocodeException (JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
