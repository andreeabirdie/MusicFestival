package controller;

import business.TicketingService;
import business.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import repository.IShowRepository;
import repository.ITicketRepository;
import repository.ShowDBRepository;
import repository.TicketDBRepository;

import java.io.IOException;
import java.util.Properties;


public class LogInController {
    UserService service;
    Properties properties;
    Stage primaryStage;

    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Text warningText;

    public LogInController() {
    }

    public void setService(UserService service, Stage primarystage, Properties properties) {
        this.service = service;
        this.properties = properties;
    }

    @FXML
    public void initialize(){
        usernameField.setText("admin");
        passwordField.setText("admin");
        warningText.wrappingWidthProperty();
    }

    @FXML
    public void attemptLogIn(ActionEvent ae) throws IOException {
        warningText.setText("");
        if(service.validateUser(usernameField.getText(),passwordField.getText())){
            ITicketRepository ticketRepository = new TicketDBRepository(properties);
            IShowRepository showRepository = new ShowDBRepository(properties);
            TicketingService ticketingService = new TicketingService(ticketRepository,showRepository);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/Ticketing.fxml"));
            BorderPane layout = loader.load();

            Stage primaryStage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            primaryStage.setTitle("Ticketing - " + usernameField.getText());

            TicketingController controller = loader.getController();
            controller.setService(ticketingService,primaryStage);
            primaryStage.setScene(new Scene(layout));
            primaryStage.show();
        }
        else {
            usernameField.clear();
            passwordField.clear();
            warningText.setText("Username-ul sau parola au fost introduse incorect");
        }
    }
}
