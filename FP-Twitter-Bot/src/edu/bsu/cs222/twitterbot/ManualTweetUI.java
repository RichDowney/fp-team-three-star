package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import org.json.simple.parser.ParseException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ManualTweetUI {
	
	private GridPane tweetPostGrid = new GridPane();
	private HBox hbButtons = new HBox();
	private TextField giphyInputField = new TextField();
	private TextArea tweetTextInputField = new TextArea();
	private Button gifButton = new Button("Get Gif");
	private Button postTweetButton = new Button("Post Tweet");
	private Label giphyLabel = new Label("Giphy Tag");
	private Label tweetTextLabel = new Label("Tweet Text Content");
	private Scene tweetPostScene = new Scene(tweetPostGrid);
	private final int tweetLimit = 140;
	private Stage primaryStage;
	private TwitterBotUI twitterBotUI;
	
	public ManualTweetUI(Stage primaryStage, TwitterBotUI twitterBotUI) {
		this.primaryStage = primaryStage;
		this.twitterBotUI = twitterBotUI;
	}
	
	public Scene getManualTweetScene() {
		return tweetPostScene;
	}
	
	protected void setUp() {
		twitterBotUI.setGrid(tweetPostGrid);
		addtoTweetPostGrid();
		configureTextFields();
		setGifButtonAction();
		setPostTweetButtonAction();
	}
	
	private void addtoTweetPostGrid() {
		tweetPostGrid.add(giphyLabel,0,0);
		tweetPostGrid.add(giphyInputField,1,0);
		tweetPostGrid.add(tweetTextLabel, 0, 2);
		tweetPostGrid.add(tweetTextInputField, 1, 2);
		hbButtons.getChildren().addAll (gifButton, postTweetButton);
		tweetPostGrid.add(hbButtons, 1, 3, 2, 1);
	}
	
	private void configureTextFields() {
		tweetTextInputField.setPromptText("Tweet limited to 140 Characters");
		giphyInputField.setPromptText("Enter Giphy Search Term");
		tweetTextInputField.setPrefRowCount(5);
		tweetTextInputField.setPrefColumnCount(15);
		tweetTextInputField.setWrapText(true);
		hbButtons.setSpacing(10.0);
		configureTweetCharacterLimit();
	}
	
	private void configureTweetCharacterLimit() {
		tweetTextInputField.lengthProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if (newValue.intValue() > oldValue.intValue()) {
					if (tweetTextInputField.getText().length() >= tweetLimit) {
						tweetTextInputField.setText(tweetTextInputField.getText().substring(0, tweetLimit));
					}
				}
			}
		});
	}
	
	private void setGifButtonAction() {
		gifButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToGenerateGif();
			}
		});
	}

	private void setPostTweetButtonAction() {
		postTweetButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToPostTweet();
			}
		});
	}
	
	private void tryToGenerateGif() {
		try {
			generateGif();
		} catch (ParseException | IOException e) {
			twitterBotUI.alertFactory.createErrorAlert("Error getting the Giphy URL, check your internet connection");
		}
	}
	
	private void generateGif() throws ParseException, IOException {
		String giphySearchTerm = giphyInputField.getText();
		GiphyConnection giphyConnection = new  GiphyConnection(giphySearchTerm);
		URLConnection  connection = giphyConnection.connectToGiphy();
		GiphyJSONParser giphyParser = new GiphyJSONParser(connection);
		String gifURL = giphyParser.parseOutURL();
		tweetTextInputField.setText(gifURL);
	}
	
	private void tryToPostTweet() {
		try {
			postTweet();
			twitterBotUI.alertFactory.createConfirmAlert("Tweet Successfully Posted!");
		} catch (UnsupportedEncodingException e) {
			twitterBotUI.alertFactory.createErrorAlert("Error posting tweet to your Twitter account, check your internet connection and tweet content");
		}
	}

	private void postTweet() throws UnsupportedEncodingException {
		String tweetText = tweetTextInputField.getText();
		TweetPoster tweetPoster = new TweetPoster(twitterBotUI.oAuth, tweetText);
		tweetPoster.tryToPostTweet();
	}

}
