package edu.bsu.cs222.twitterbot;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TypeOfTweetUI {
	private GridPane typeOfTweetGrid = new GridPane();
	private Button manualButton = new Button("Manual Tweet");
	private Button automaticButton = new Button("Automatic Tweet");
	private Button scheduleTweetButton = new Button("Schedule Tweet");
	private Button typeBackButton = new Button("Back");
	private Scene tweetTypeScene = new Scene(typeOfTweetGrid);
	private Stage primaryStage;
	private UIController controller;
	
	public TypeOfTweetUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}
	
	public Scene getTweetTypeTweetScene() {
		return tweetTypeScene;
	}
	
	protected void setUp() {
		controller.setGrid(typeOfTweetGrid);
		addToTypeOfTweetGrid();
		setManualButton(primaryStage);
		setAutomaticButton(primaryStage);
		setScheduleTweetButton(primaryStage);
		setTypeBackButtonAction(primaryStage);
		setTweetTypeSceneStyle();
	}
	
	private void setTweetTypeSceneStyle(){
		tweetTypeScene.getStylesheets().add("uiStyle.css");
	}
	
	private void addToTypeOfTweetGrid() {
		typeOfTweetGrid.add(manualButton,0,0);
		typeOfTweetGrid.add(automaticButton,1,0);
		typeOfTweetGrid.add(scheduleTweetButton,2,0);
		typeOfTweetGrid.add(typeBackButton,0,2);
	}
	
	private void setManualButton(Stage primaryStage) {
		manualButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToTweetScene(primaryStage);
			}
		});
	}
	
	private void setAutomaticButton(Stage primaryStage) {
		automaticButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToAutomaticTweetScene(primaryStage);
			}
		});
	}
	
	private void setScheduleTweetButton(Stage primaryStage) {
		scheduleTweetButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToScheduleTweetScene(primaryStage);
			}
		});
	}
	
	private void setTypeBackButtonAction(Stage primaryStage) {
		typeBackButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToStartScene(primaryStage);
			}
		});
	}
}
