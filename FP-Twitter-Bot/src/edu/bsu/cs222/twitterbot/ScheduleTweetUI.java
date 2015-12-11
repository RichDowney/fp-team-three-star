package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Timer;

import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ScheduleTweetUI {
	
	private GridPane scheduleGrid = new GridPane();
	private DatePicker checkInDatePicker = new DatePicker();
	private ComboBox<Integer> hourSelector = new ComboBox<>();
	private ComboBox<Integer> minuteSelector = new ComboBox<>();
	private TextField giphyInputField = new TextField();
	private Label dateLabel = new Label("Pick A Date");
	private Label hourComboLabel = new Label("Pick A Hour");
	private Label minuteComboLabel = new Label("Pick A Minute");
	private Label giphyLabel = new Label("Giphy Tag");
	private Button backToTweetTypeButton = new Button("Back");
	private Button scheduleButton = new Button("Schedule Tweet");
	private Scene scheduleTweetScene = new Scene(scheduleGrid);
	private Stage primaryStage;
	private UIController controller;
	
	
	public ScheduleTweetUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}
	
	public Scene getScheduleTweetScene() {
		return scheduleTweetScene;
	}
	
	protected void setUp() {
		controller.setGrid(scheduleGrid);;
		setTweetTypeSceneStyle();
		addToScheduleGrid();
		configureHourComboBox();
		configureMinuteComboBox();
		setBackToTweetTypeButtonAction(primaryStage);
		setscheduleButtonAction();
	}
	
	private void setTweetTypeSceneStyle(){
		scheduleTweetScene.getStylesheets().add("uiStyle.css");
	}
	
	private void addToScheduleGrid() {
		scheduleGrid.add(dateLabel,0,0);
		scheduleGrid.add(checkInDatePicker,1,0);
		scheduleGrid.add(hourComboLabel,0,1);
		scheduleGrid.add(hourSelector,1,1);
		scheduleGrid.add(minuteComboLabel,0,2);
		scheduleGrid.add(minuteSelector,1,2);
		scheduleGrid.add(giphyLabel,0,3);
		scheduleGrid.add(giphyInputField,1,3);
		scheduleGrid.add(backToTweetTypeButton,0,4);
		scheduleGrid.add(scheduleButton,1,4);
	}
	
	private void configureHourComboBox() {
		for(int i = 0; i<=23; i++) {
			hourSelector.getItems().add(i);
		}
		hourSelector.setValue(12);
	}
	
	private void configureMinuteComboBox() {
		for(int i = 0; i<=59; i++) {
			minuteSelector.getItems().add(i);
		}
		minuteSelector.setValue(30);
	}
	
	private void setBackToTweetTypeButtonAction(Stage primaryStage) {
		backToTweetTypeButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				controller.switchSceneToTweetTypeScene(primaryStage);
			}
		});
	}
	
	private void setscheduleButtonAction() {
		scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToPostTweet();
			}
		});
	}
	
	private void tryToPostTweet() {
		try {
			postTweet();
		} catch (Exception e) {
			controller.alertFactory.createErrorAlert("Error scheduling tweet, You must select a valid date in the future and have an internet connection to tweet.");
		}
	}
	
	private void postTweet() throws ParseException, IOException {
		String giphySearchTerm = giphyInputField.getText();
		long timeToTweet = calculateTime();
		Timer timer = new Timer();
		timer.schedule(new TweetTimer(controller.oAuth, giphySearchTerm), timeToTweet );
	}
	
	private long calculateTime() {
		LocalDate selectedDate = checkInDatePicker.getValue();
		ZoneId zoneId = ZoneId.systemDefault();
		long dateMill = selectedDate.atStartOfDay(zoneId).toEpochSecond();
		long hourMill = hourSelector.getValue()*60*60;
		long minuteMill = minuteSelector.getValue()*60;
		long unixTime = System.currentTimeMillis() / 1000L;
		dateMill = dateMill + hourMill + minuteMill;
		long calculatedTime = dateMill - unixTime;
		return calculatedTime * 1000;
	}

}
