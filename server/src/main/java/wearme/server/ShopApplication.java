package wearme.server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Главный класс приложения
 */
@SpringBootApplication
@EnableTransactionManagement
public class ShopApplication  {
    /**
     * Точка входа в приложение
     * @param args - аргументы
     */
    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }

}

