package client.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс логирования приложения
 */
public class MyLogger  {
    public static Logger logger;

    /**
     * Инициализирует логер
     * @return состояние инициализации
     */
    public boolean deploy() {
        String s = File.separator;
        String log4jConfigFile = String.format(System.getProperty("user.dir") + "%ssrc%smain%sresources%sclient%sxml%slog4j2.xml", s,s,  s, s,s, s);
        try {
            ConfigurationSource source = new ConfigurationSource(new FileInputStream(log4jConfigFile));
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
