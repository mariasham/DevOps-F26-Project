package edu.westga.comp4420.grocery_list.view.codebehind;

import edu.westga.comp4420.grocery_list.LawSchool;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * CodeBehind to handle adding a new law school listing.
 *
 * @author Maria Shamasna
 * @version 2026-10-11 [Fall 2026]
 */
public class AddItemWindow {
    @FXML private AnchorPane guiPane;
    @FXML private TextField nameField;
    @FXML private TextField locationField;
    @FXML private TextField deadlineField;
    @FXML private TextField statusField;
    @FXML private TextField linkField;

    private List<LawSchool> lawSchools;

    /**
     * Sets the law school list that new listings should be added to.
     *
     * @param lawSchools the list of law schools
     */
    public void setLawSchoolList(List<LawSchool> lawSchools) {
        this.lawSchools = lawSchools;
    }

    @FXML
    void addLawSchool() {
        LawSchool school = new LawSchool(this.nameField.getText(), this.locationField.getText(),
                this.deadlineField.getText(), this.statusField.getText(), this.linkField.getText());

        this.lawSchools.add(school);
        this.closeWindow();
    }

    @FXML
    void cancel() {
        this.closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) this.guiPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    void initialize() {
        assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file.";
        assert this.nameField != null : "fx:id=\"nameField\" was not injected: check your FXML file.";
        assert this.locationField != null : "fx:id=\"locationField\" was not injected: check your FXML file.";
        assert this.deadlineField != null : "fx:id=\"deadlineField\" was not injected: check your FXML file.";
        assert this.statusField != null : "fx:id=\"statusField\" was not injected: check your FXML file.";
        assert this.linkField != null : "fx:id=\"linkField\" was not injected: check your FXML file.";
    }
}