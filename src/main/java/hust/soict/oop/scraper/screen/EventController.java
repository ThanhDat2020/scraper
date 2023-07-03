package hust.soict.oop.scraper.screen;

import java.io.IOException;

import hust.soict.oop.scraper.event.Event;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EventController {
    @FXML
    private HBox itemC;

    @FXML
    private Label eventLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label dynastyLabel;

    @FXML
    private Button detailButton;

    public void setEventDetails(Event event) {
        // Set the event details in the UI elements
        eventLabel.setText(event.getEvent());
        dateLabel.setText(event.getDate());
        ageLabel.setText(event.getAge());
        dynastyLabel.setText(event.getDynasty());

        // Set button action or any additional event handling
//        detailButton.setOnAction(event -> {
//            // Handle the button click event for the event details
//        });
    }
    
    @FXML
    private void handleDetailButtonClick(ActionEvent event) {
        try {
            // Load the FXML file for the modal content
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModalContent.fxml"));
            Parent root = loader.load();

            // Create a new stage (modal) to show the content
            Stage modalStage = new Stage();
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setTitle("Modal Title");
            modalStage.setScene(new Scene(root));

            // Show the modal and wait for it to be closed
            modalStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
