package edu.bsu.cs222.twitterbot;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ApiUI {
	
	private GridPane apiGrid = new GridPane();
	protected TextField userNameInputField = new TextField();
	private TextField apiKeyInputField = new TextField();
	private TextField apiSecretInputField = new TextField();
	private Label userNameLabel = new Label("Username");
	private Label apiKeyLabel = new Label("API Key");
	private Label apiSecretLabel = new Label("API Secret");
	private Button apiBackButton = new Button("Previous");
	private Button apiNextButton = new Button("Next");
	private Button readApiValuesButton = new Button("Get Saved API Values");
	private Button writeApiValuesButton = new Button("Save API Values");
	private Scene apiScene = new Scene(apiGrid);
	private Stage primaryStage;
	private UIController controller;
	
	public ApiUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}
	
	public Scene getAPIScene() {
		return apiScene;
	}
	
	protected void setUp() {
		controller.setGrid(apiGrid);
		addtoApiGrid();
		configureTextFields();
		setApiSceneStyle();
	}
	
	private void setApiSceneStyle(){
		apiScene.getStylesheets().add("uiStyle.css");
	}
	
	private void addtoApiGrid() {
		apiGrid.add(userNameInputField, 1, 0);
		apiGrid.add(apiKeyInputField, 1, 1);
		apiGrid.add(apiSecretInputField, 1, 2);
		apiGrid.add(userNameLabel, 0, 0);
		apiGrid.add(apiKeyLabel, 0, 1);
		apiGrid.add(apiSecretLabel, 0, 2);
		apiGrid.add(readApiValuesButton, 0, 3);
		apiGrid.add(writeApiValuesButton, 1, 3);
		apiGrid.add(apiBackButton, 0, 4);
		apiGrid.add(apiNextButton, 1, 4);
	}
	
	private void configureTextFields() {
		apiKeyInputField.setPromptText("Paste in API Key");
		apiSecretInputField.setPromptText("Paste in API Secret");
		setReadApiValuesButtonAction();
		setWriteApiValuesButtonAction();
		setApiBackButtonAction(primaryStage);
		setApiNextButtonAction(primaryStage);
	}
	
	private void setReadApiValuesButtonAction() {
		readApiValuesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.getApiValuesFromFile();
				setApiValues();
			}
		});
	}
	
	private void setWriteApiValuesButtonAction() {
		writeApiValuesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				writeApiValuesToFile();
				setApiValues();
			}
		});
	}
	
	private void setApiBackButtonAction(Stage primaryStage) {
		apiBackButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToStartScene(primaryStage);
			}
		});
	}

	private void setApiNextButtonAction(Stage primaryStage) {
		apiNextButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				setApiValues();
				controller.switchSceneToVerifyScene(primaryStage);
			}
		});
	}
	
	private void setApiValues() {
		String apiKey = controller.apiKey;
		String apiSecret = controller.apiSecret;
		apiKeyInputField.setText(apiKey);
		apiSecretInputField.setText(apiSecret);
	}
	
	private void writeApiValuesToFile() {
		String apiKeyValueToWrite = apiKeyInputField.getText();
		String apiSecretValueToWrite = apiSecretInputField.getText();
		APIValueFileWriter apiValueFileWriter = new APIValueFileWriter(apiKeyValueToWrite, apiSecretValueToWrite);
		apiValueFileWriter.tryToWriteToJsonFile();
	}
	
}
