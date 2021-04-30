package client.exception;

import client.JavaFXApplication;
import client.utils.MyLogger;

/**
 * Исключение при отсутствии данных
 */
public class NoAppDataException extends Exception{
    /**
     * Инициализирует исключение
     * @param mainApp - главное приложение
     * @param errorMessage - сообщение
     */
    public NoAppDataException(JavaFXApplication mainApp, String errorMessage){
        super(errorMessage);
        MyLogger.logger.error(errorMessage);
    }
}
