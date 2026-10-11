package edu.westga.comp4420.grocery_list.view.codebehind;

import edu.westga.comp4420.grocery_list.LawSchool;
import edu.westga.comp4420.grocery_list.LawSchoolRepository;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

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
}