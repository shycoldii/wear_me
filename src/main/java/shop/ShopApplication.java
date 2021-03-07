package shop;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import shop.shop.JavaFxApplication;


@SpringBootApplication
public class ShopApplication  {
    public static void main(String[] args) {
        Application.launch(JavaFxApplication.class, args);
    }

}

