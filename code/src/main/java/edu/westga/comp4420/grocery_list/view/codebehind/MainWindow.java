package edu.westga.comp4420.grocery_list.view.codebehind;

import java.io.IOException;

import edu.westga.comp4420.grocery_list.LawSchool;
import edu.westga.comp4420.grocery_list.LawSchoolRepository;
import edu.westga.comp4420.grocery_list.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
	@FXML
	private ListView<LawSchool> lawSchools;
	@FXML
	private AnchorPane guiPane;
	@FXML
	private TextField searchField;

	private ObservableList<LawSchool> allLawSchools;

	@FXML
	void initialize() {
		assert this.lawSchools != null : "fx:id=\"lawSchools\" was not injected: check your FXML file.";
		assert this.guiPane != null : "fx:id=\"guiPane\" was not injected: check your FXML file.";
		assert this.searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file.";

		LawSchoolRepository repository = new LawSchoolRepository();
		this.allLawSchools = FXCollections.observableArrayList(repository.getSampleLawSchools());
		this.lawSchools.setItems(FXCollections.observableArrayList(this.allLawSchools));
	}

	@FXML
	void searchSchools() {
		String searchText = this.searchField.getText().toLowerCase();
		this.lawSchools.getItems().clear();

		for (LawSchool currentSchool : this.allLawSchools) {
			if (this.matchesSearch(currentSchool, searchText)) {
				this.lawSchools.getItems().add(currentSchool);
			}
		}
	}

	private boolean matchesSearch(LawSchool school, String searchText) {
		return school.getName().toLowerCase().contains(searchText)
				|| school.getLocation().toLowerCase().contains(searchText)
				|| school.getStatus().toLowerCase().contains(searchText);
	}

	@FXML
	void addSchool() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource(Main.ADD_ITEM_WINDOW_RESOURCE));
			loader.load();

			Parent parent = loader.getRoot();
			Scene scene = new Scene(parent);
			Stage addSchoolStage = new Stage();
			addSchoolStage.setTitle("Add Law School");
			addSchoolStage.setScene(scene);
			addSchoolStage.initModality(Modality.APPLICATION_MODAL);

			AddItemWindow controller = loader.getController();
			controller.setLawSchoolList(this.allLawSchools);

			addSchoolStage.showAndWait();
		} catch (IOException error) {
			Alert errorBox = new Alert(AlertType.ERROR);
			errorBox.setContentText("Unable to open add school window");
			errorBox.showAndWait();
		}
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