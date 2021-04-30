package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;
/**
 * Исключение при неудачной продаже
 */
public class SellProductException extends Exception{
    /**
     * Инициализирует исключение
     * @param mainApp - главное приложение
     * @param errorMessage - сообщение
     */
    public SellProductException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
