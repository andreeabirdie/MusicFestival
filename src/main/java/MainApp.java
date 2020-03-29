import business.UserService;
import controller.LogInController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.IUserRepository;
import repository.UserDBRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private void loadProperties(Properties properties){
        try {
            properties.load(new FileReader("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        Properties properties = new Properties();
        loadProperties(properties);

        IUserRepository userRepository = new UserDBRepository(properties);
        UserService userService = new UserService(userRepository);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/loginFX.fxml"));
        BorderPane layout = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Ticketing - Music Festival");

        LogInController controller = loader.getController();
        controller.setService(userService,primaryStage,properties);
        stage.setScene(new Scene(layout));
        stage.show();
    }
}
