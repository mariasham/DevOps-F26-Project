package edu.westga.comp4420.grocery_list.view.codebehind;

import edu.westga.comp4420.grocery_list.LawSchool;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

/**
 * CodeBehind to display details for a selected law school.
 *
 * @author Maria Shamasna
 * @version 2026-10-11 [Fall 2026]
 */
public class SchoolDetailsWindow {
    @FXML private Label nameLabel;
    @FXML private Label locationLabel;
    @FXML private Label deadlineLabel;
    @FXML private Label statusLabel;
    @FXML private Label linkLabel;

    private LawSchool school;

    /**
     * Sets the school displayed in the details window.
     *
     * @param school the selected law school
     */
    public void setSchool(LawSchool school) {
        this.school = school;
        this.nameLabel.setText(school.getName());
        this.locationLabel.setText("Location: " + school.getLocation());
        this.deadlineLabel.setText("Deadline: " + school.getDeadline());
        this.statusLabel.setText("Status: " + school.getStatus());
        this.linkLabel.setText("Application Link: " + school.getApplicationLink());
    }

    @FXML
    void openApplicationLink() {
        if (this.school == null) {
            return;
        }

        try {
            Desktop.getDesktop().browse(new URI(this.school.getApplicationLink()));
        } catch (IOException | URISyntaxException | UnsupportedOperationException error) {
            Alert errorBox = new Alert(AlertType.ERROR);
            errorBox.setContentText("Unable to open application link");
            errorBox.showAndWait();
        }
    }

    @FXML
    void initialize() {
        assert this.nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file.";
        assert this.locationLabel != null : "fx:id=\"locationLabel\" was not injected: check your FXML file.";
        assert this.deadlineLabel != null : "fx:id=\"deadlineLabel\" was not injected: check your FXML file.";
        assert this.statusLabel != null : "fx:id=\"statusLabel\" was not injected: check your FXML file.";
        assert this.linkLabel != null : "fx:id=\"linkLabel\" was not injected: check your FXML file.";
    }
}