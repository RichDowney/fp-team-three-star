package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.util.Timer;

import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AutoTweetUI {
	private GridPane automaticTweetGrid = new GridPane();
	private ComboBox<String> timerSelector = new ComboBox<>();
	private Label timerSelectorLabel = new Label("Select Time Interval To Tweet");
	private TextField autoGiphyInputField = new TextField();
	private Label autoGiphyLabel = new Label("Giphy Tag");
	private Button backToTweetTypeButton = new Button("Back");
	private Button startAutomaticButton = new Button("Begin Auto Tweeting");
	private Scene automaticTweetScene = new Scene(automaticTweetGrid);
	private Stage primaryStage;
	private UIController controller;
	
	public AutoTweetUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}

	public Scene getAutomaticTweetScene() {
		return automaticTweetScene;
	}

	protected void setUp() {
		controller.setGrid(automaticTweetGrid);
		configureTimerComboBox();
		addToAutomaticTweetGrid();
		setAutomaticButtonAction();
		setBackToTweetTypeButtonAction(primaryStage);
		setAutomaticTweetSceneStyle();
	}
	
	private void setAutomaticTweetSceneStyle(){
		automaticTweetScene.getStylesheets().add("uiStyle.css");
	}
	
	private void addToAutomaticTweetGrid() {
		automaticTweetGrid.add(timerSelectorLabel,0,0);
		automaticTweetGrid.add(timerSelector,1,0);
		automaticTweetGrid.add(autoGiphyLabel,0,1);
		automaticTweetGrid.add(autoGiphyInputField,1,1);
		automaticTweetGrid.add(backToTweetTypeButton,0,2);
		automaticTweetGrid.add(startAutomaticButton,1,2);
	}
	
	private void configureTimerComboBox() {
		timerSelector.getItems().add("1 Minute");
		timerSelector.getItems().add("5 Minutes");
		timerSelector.getItems().add("10 Minutes");
		timerSelector.getItems().add("15 Minutes");
		timerSelector.getItems().add("20 Minutes");
		timerSelector.getItems().add("25 Minutes");
		timerSelector.getItems().add("30 Minutes");
		timerSelector.getItems().add("45 Minutes");
		timerSelector.getItems().add("1 Hour");
		timerSelector.setValue("10 Minutes");
	}
	
	private void setBackToTweetTypeButtonAction(Stage primaryStage) {
		backToTweetTypeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToTweetTypeScene(primaryStage);
			}
		});
	}
	
	private void setAutomaticButtonAction() {
		startAutomaticButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToAutoPostTweets();
			}
		});
	}
	
	private void tryToAutoPostTweets() {
		try {
			autoPostTweet();
		} catch (Exception e) {
			controller.alertFactory.createErrorAlert("Something went wrong, check your Internet Connection");
		}
	}
	
	private void autoPostTweet() throws ParseException, IOException {
		String giphySearchTerm = autoGiphyInputField.getText();
		int timeInterval = timeSelectedToMilSeconds();
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(new TweetTimer(controller.oAuth, giphySearchTerm), 0, timeInterval);
	}
	
	private int timeSelectedToMilSeconds() {
		String timeSelected = this.timerSelector.getValue();
		int timeInterval;
		switch (timeSelected) {
		case "1 Minute":
			timeInterval = 1 * 60 * 1000;
			break;
		case "5 Minutes":
			timeInterval = 5 * 60 * 1000;
			break;
		case "10 Minutes":
			timeInterval = 10 * 60 * 1000;
			break;
		case "15 Minutes":
			timeInterval = 15 * 60 * 1000;
			break;
		case "20 Minutes":
			timeInterval = 20 * 60 * 1000;
			break;
		case "25 Minutes":
			timeInterval = 25 * 60 * 1000;
			break;
		case "30 Minutes":
			timeInterval = 30 * 60 * 1000;
			break;
		case "45 Minutes":
			timeInterval = 45 * 60 * 1000;
			break;
		case "1 Hour":
			timeInterval = 60 * 60 * 1000;
			break;
		default:
			timeInterval = 10 * 60 * 1000;	
			break;
		}
		return timeInterval;
	}
}
