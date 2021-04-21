package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

import java.util.List;
/**
 * Исключение при отсутствии цвета товара
 */
public class NoColorException extends Exception{
    public NoColorException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
