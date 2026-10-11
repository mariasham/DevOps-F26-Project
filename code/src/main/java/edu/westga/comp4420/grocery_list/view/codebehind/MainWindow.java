package edu.westga.comp4420.grocery_list.view.codebehind;

import java.io.IOException;

import edu.westga.comp4420.grocery_list.LawSchool;
import edu.westga.comp4420.grocery_list.LawSchoolRepository;
import edu.westga.comp4420.grocery_list.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * CodeBehind to handle processing for the main law school list window.
 *
 * @author Maria Shamasna
 * @version 2026-10-11 [Fall 2026]
 */
public class MainWindow {
    @FXML private ListView<LawSchool> lawSchools;
    @FXML private AnchorPane guiPane;

    @FXML
    void initialize() {
        assert this.lawSchools != null : "fx:id=\"lawSchools\" was not injected: check your FXML file.";
        assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file.";

        LawSchoolRepository repository = new LawSchoolRepository();
        this.lawSchools.getItems().setAll(repository.getSampleLawSchools());
    }

    @FXML
    void openSelectedSchool(MouseEvent event) {
        if (event.getClickCount() == 2) {
            LawSchool selectedSchool = this.lawSchools.getSelectionModel().getSelectedItem();
            if (selectedSchool != null) {
                this.openDetailsWindow(selectedSchool);
            }
        }
    }

    private void openDetailsWindow(LawSchool selectedSchool) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(Main.SCHOOL_DETAILS_WINDOW_RESOURCE));
            loader.load();

            Parent parent = loader.getRoot();
            Scene scene = new Scene(parent);
            Stage detailsStage = new Stage();
            detailsStage.setTitle("Law School Details");
            detailsStage.setScene(scene);
            detailsStage.initModality(Modality.APPLICATION_MODAL);

            SchoolDetailsWindow controller = loader.getController();
            controller.setSchool(selectedSchool);

            detailsStage.showAndWait();
        } catch (IOException error) {
            Alert errorBox = new Alert(AlertType.ERROR);
            errorBox.setContentText("Unable to open school details");
            errorBox.showAndWait();
        }
    }
}