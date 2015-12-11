package edu.bsu.cs222.twitterbot;

import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LaunchUI {
	
	protected ComboBox<String> userSelector = new ComboBox<>();
	private Label userSelectorLabel = new Label("Select User To Tweet From");
	private Label addNewUserLabel = new Label("Add A New User");
	private Label startTweetingLabel = new Label("Start Tweeting Now");
	private Button addNewUserButton = new Button("Add User");
	private Button startTweetingButton = new Button("Start Tweeting");
	private GridPane startGrid = new GridPane();
	private Scene startScene = new Scene(startGrid);
	private Stage primaryStage;
	private UIController controller;
	
	public LaunchUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}
	
	public Scene getStartScene() {
		return startScene;
	}
	
	protected void setUp() {
		controller.setGrid(startGrid);
		addToStartGrid();
		configureComboBox();
		setAddNewUserButtonAction(primaryStage);
		setStartTweetingButton(primaryStage);
		setStartSceneStyle();
	}
	
	private void setStartSceneStyle(){
		startScene.getStylesheets().add("uiStyle.css");
	}
	
	private void addToStartGrid() {
		startGrid.add(userSelectorLabel, 0, 0);
		startGrid.add(userSelector, 1, 0);
		startGrid.add(addNewUserLabel, 0, 1);
		startGrid.add(addNewUserButton, 1, 1);
		startGrid.add(startTweetingLabel, 0, 2);
		startGrid.add(startTweetingButton, 1, 2);
	}
	
	private void configureComboBox() {
		userSelector.getItems().add("None");
		userSelector.setValue("None");
		addUsersFromFile();
	}
	
	private void addUsersFromFile() {
		controller.usersParser = new ParseFromJSONFile("twitter-values/users.json");
		controller.usersJSONObject = controller.usersParser.tryTtoReadFromFile();
		if(controller.usersJSONObject != null){	
			for(Iterator<?> iterator = controller.usersJSONObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				userSelector.getItems().add(key);
			}
		}
	}
	
	private void setAddNewUserButtonAction(Stage primaryStage) {
		addNewUserButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToApiScene(primaryStage);
			}
		});
	}
	
	private void setStartTweetingButton(Stage primaryStage) {
		startTweetingButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToTweetTypeScene(primaryStage);
			}
		});
	}

}
