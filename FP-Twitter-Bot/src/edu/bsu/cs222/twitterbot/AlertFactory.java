package edu.bsu.cs222.twitterbot;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertFactory {
	
	private void createErrorAlert(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Something went wrong!");
		alert.setContentText(errorMessage);
		alert.showAndWait();
	}
	
	private void createInfoAlert(String infoMessage){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information");
		alert.setHeaderText("Did You Know?");
		alert.setContentText(infoMessage);
		alert.showAndWait();
	}
}
