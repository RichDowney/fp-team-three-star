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
	private Scene tweetTypeScene = new Scene(typeOfTweetGrid);
	private Stage primaryStage;
	private TwitterBotUI twitterBotUI;
	
	public TypeOfTweetUI(Stage primaryStage, TwitterBotUI twitterBotUI) {
		this.primaryStage = primaryStage;
		this.twitterBotUI = twitterBotUI;
	}
	
	public Scene getTweetTypeTweetScene() {
		return tweetTypeScene;
	}
	
	protected void setUp() {
		twitterBotUI.setGrid(typeOfTweetGrid);
		addToTypeOfTweetGrid();
		setManualButton(primaryStage);
		setAutomaticButton(primaryStage);
	}
	
	private void addToTypeOfTweetGrid() {
		typeOfTweetGrid.add(manualButton,0,0);
		typeOfTweetGrid.add(automaticButton,1,0);
	}
	
	private void setManualButton(Stage primaryStage) {
		manualButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				twitterBotUI.switchSceneToTweetScene(primaryStage);
			}
		});
	}
	
	private void setAutomaticButton(Stage primaryStage) {
		automaticButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				twitterBotUI.switchSceneToAutomaticTweetScene(primaryStage);
			}
		});
	}
}
