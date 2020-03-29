package controller;

import business.TicketingService;
import com.sun.javafx.scene.layout.region.SliceSequenceConverter;
import domain.Show;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;


public class ViewShowsController {
    TicketingService service;
    private Stage dialogStage;
    @FXML
    TableView showsTable;
    @FXML
    TableColumn<Show,Integer> soldTicketsColumn;
    @FXML
    TableColumn<Show,Integer> remainingTicketsColumn;
    @FXML
    Text warningText;
    ObservableList<Show> shows = FXCollections.observableArrayList();


    public ViewShowsController() {
    }

    public void setService(TicketingService service,Stage dialogStage,LocalDate date) {
        this.service = service;
        this.dialogStage = dialogStage;
        shows.setAll(service.getShows(date));
        if(shows.size()==0) warningText.setText("Nu au fost gasite spectacole pentru data introdusa");
    }

    @FXML
    public void initialize(){
        showsTable.setItems(shows);
        soldTicketsColumn.setCellValueFactory(data-> {
            int i = data.getValue().getTotalTickets() - data.getValue().getRemainingTickets();
            return new ReadOnlyObjectWrapper<Integer>(i);
        });
        customiseFactory();
    }


    private void customiseFactory() {
        showsTable.setRowFactory(tv -> new TableRow<Show>() {
            @Override
            protected void updateItem(Show item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || item == null)
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



    @FXML
    public void goBack() {
        dialogStage.close();
    }
}
