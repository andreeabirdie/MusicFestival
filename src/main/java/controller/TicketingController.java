package controller;

import business.TicketingService;
import domain.Show;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.List;

public class TicketingController {
    TicketingService service;
    Stage primaryStage;
    ObservableList<Show> shows = FXCollections.observableArrayList();
    @FXML
    TableView showsTable;
    @FXML
    Spinner<Integer> ticketSpinner;
    @FXML
    TableColumn<Show, Integer> soldTicketsColumn;
    @FXML
    TableColumn<Show, Integer> remainingTicketsColumn;
    @FXML
    TextField nameField;
    @FXML
    DatePicker dateField;
    @FXML
    Text warningTextSearch;
    @FXML
    Text warningTextBuy;

    public TicketingController() {
    }

    public void setService(TicketingService service, Stage primaryStage) {
        this.service = service;
        this.primaryStage = primaryStage;
        initModel();
    }

    @FXML
    public void initialize() {
        soldTicketsColumn.setCellValueFactory(data -> {
            int i = data.getValue().getTotalTickets() - data.getValue().getRemainingTickets();
            return new ReadOnlyObjectWrapper<Integer>(i);
        });
        showsTable.setItems(shows);
        showsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                ticketSpinner.setEditable(false);
            } else {
                Show s = (Show) newValue;
                if (s.getRemainingTickets() == 0) {
                    ticketSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 0));
                    ticketSpinner.setEditable(false);
                } else {
                    ticketSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, s.getRemainingTickets()));
                    ticketSpinner.setEditable(true);
                }
            }
        });
        customiseFactory();
    }


    private void customiseFactory() {
        showsTable.setRowFactory(tv -> new TableRow<Show>() {
            @Override
            protected void updateItem(Show item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null)
                    setStyle("");
                else if (item.getRemainingTickets() == 0)
                    setStyle("-fx-background-color: #A53A3B;");
//                else if (item.getRemainingTickets()>0)
//                    setStyle("-fx-background-color: #5398D9;");
                else
                    setStyle("");
            }
        });
    }

    public void initModel(){
        shows.setAll(service.getAllShows());
    }

    @FXML
    public void buyTickets() {
        String text = "";
        if (nameField.getText().equals("")) text += "Nu ati introdus numele";
        else {
            if (showsTable.getSelectionModel().getSelectedItem() == null) text += "Nu ati selectat spectacolul";
            else {
                Show s = (Show) showsTable.getSelectionModel().getSelectedItem();
                if (s.getRemainingTickets() == 0)
                    text += "Nu se mai pot cumpara bilete!\n";
                else {
                    service.buyTickets(s.getId(),
                            ticketSpinner.getValue(), nameField.getText());
                    initModel();
                }
            }
        }
        warningTextBuy.setText(text);
    }

    @FXML
    public void searchByDate() throws IOException {
        if (dateField.getValue() == null) {
            warningTextSearch.setText("Nu ati selectat data!");
        } else {
            warningTextSearch.setText("");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ShowsByDate.fxml"));

            BorderPane root = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezultat search");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initStyle(StageStyle.TRANSPARENT);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            ViewShowsController viewGradesController = loader.getController();
            viewGradesController.setService(service, dialogStage, dateField.getValue());

            dialogStage.show();
        }
    }

    @FXML
    public void logOut() {
        primaryStage.close();
    }
}
