package client.utils;

import client.JavaFXApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Класс логирования приложения
 */
public class MyLogger {
    /**
     * логер
     */
    public static Logger logger;

    /**
     * Инициализирует логер
     *
     * @return состояние инициализации
     */
    public boolean deploy() {
        String s = File.separator;
        //String log4jConfigFile = String.format(System.getProperty("user.dir") + "%ssrc%smain%sresources%sclient%sxml%slog4j2.xml", s,s,  s, s,s, s);
        //System.out.println(log4jConfigFile);
        InputStream log4jConfigFile = JavaFXApplication.class.getClassLoader().getResourceAsStream("client/xml/log4j2.xml");
        try {
            ConfigurationSource source = new ConfigurationSource(log4jConfigFile);
            Configurator.initialize(null, source);
            logger = LogManager.getRootLogger();
            logger.info("Логгер инициализирован");
            return true;
        } catch (IOException e) {
            System.out.println("Не удалось загрузить логгер");
            e.printStackTrace();
            return false;
        }
    }
}
