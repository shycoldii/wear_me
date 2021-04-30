package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при недействительных промокодах
 */
public class TimeOutPromocodeException extends Exception{
    /**
     * Инициализирует исключение
     * @param mainApp - главное приложение
     * @param errorMessage - сообщение
     */
    public TimeOutPromocodeException (JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
