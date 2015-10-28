package edu.bsu.cs222.twitterbot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TwitterBotUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private String apiKey;
	private String apiSecret;
	private OAuth oAuth;

	private GridPane apiGrid = new GridPane();
	private TextField apiKeyInputField = new TextField();
	private TextField apiSecretInputField = new TextField();
	private Label apiKeyLabel = new Label("API Key");
	private Label apiSecretLabel = new Label("API Secret");
	private Button apiNextButton = new Button("Next");
	private Button readApiValuesButton = new Button("Get Saved API Values");
	private Button writeApiValuesButton = new Button("Save API Values");
	private Scene apiScene = new Scene(apiGrid);

	private GridPane tweetPostGrid = new GridPane();
	private TextField authorizationUrlOutputField = new TextField();
	private TextField tokenVerifierInputField = new TextField();
	private TextArea tweetTextInputField = new TextArea();
	private Button postTweetButton = new Button("Post Tweet");
	private Button backToApiButton = new Button("Previous");
	private Button getAuthorizationUrlButton = new Button("Get Authorization URL");
	private Label tokenVerifierLabel = new Label("Token Verifier Code");
	private Label tweetTextLabel = new Label("Tweet Text Content");
	private Scene tweetPostScene = new Scene(tweetPostGrid);

	@Override
	public void start(Stage primaryStage) {
		setGrid(apiGrid);
		addtoApiGrid();
		setGrid(tweetPostGrid);
		addtoTweetPostGrid();
		configureTextFields();
		setButtonActions(primaryStage);
		setStage(primaryStage);

	}

	private void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
	}

	private void addtoApiGrid() {
		apiGrid.add(apiKeyInputField, 1, 0);
		apiGrid.add(apiSecretInputField, 1, 1);
		apiGrid.add(apiKeyLabel, 0, 0);
		apiGrid.add(apiSecretLabel, 0, 1);
		apiGrid.add(readApiValuesButton, 0, 2);
		apiGrid.add(writeApiValuesButton, 1, 2);
		apiGrid.add(apiNextButton, 0, 3);
	}

	private void addtoTweetPostGrid() {
		tweetPostGrid.add(apiKeyInputField, 1, 0);
		tweetPostGrid.add(apiSecretInputField, 1, 1);
		tweetPostGrid.add(authorizationUrlOutputField, 1, 2);
		tweetPostGrid.add(tokenVerifierInputField, 1, 3);
		tweetPostGrid.add(tweetTextInputField, 1, 4);
		tweetPostGrid.add(postTweetButton, 1, 5);
		tweetPostGrid.add(backToApiButton, 0, 5);
		tweetPostGrid.add(getAuthorizationUrlButton, 0, 2);
		tweetPostGrid.add(apiKeyLabel, 0, 0);
		tweetPostGrid.add(apiSecretLabel, 0, 1);
		tweetPostGrid.add(tokenVerifierLabel, 0, 3);
		tweetPostGrid.add(tweetTextLabel, 0, 4);
	}

	private void configureTextFields() {
		apiKeyInputField.setPromptText("Paste in API Key");
		apiSecretInputField.setPromptText("Paste in API Secret");
		authorizationUrlOutputField.setPromptText("Goto for Authorization Code");
		authorizationUrlOutputField.setEditable(false);
		tokenVerifierInputField.setPromptText("Paste in Authorization Code");
		tweetTextInputField.setPromptText("Tweet limited to 140 Characters");
		tweetTextInputField.setPrefRowCount(5);
		tweetTextInputField.setPrefColumnCount(15);
		tweetTextInputField.setWrapText(true);
	}

	private void setButtonActions(Stage primaryStage) {
		setReadApiValuesButtonAction();
		setWriteApiValuesButtonAction();
		setApiNextButtonAction(primaryStage);
		setGetAuthorizationUrlButtonAction();
		setBackToApiButtonAction(primaryStage);
		setPostTweetButtonAction();
	}

	private void setStage(Stage primaryStage) {
		primaryStage.setTitle("Twitter Bot");
		primaryStage.setScene(apiScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}

	private void setApiNextButtonAction(Stage primaryStage) {
		apiNextButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				setApiValues();
				switchSceneToGrid(primaryStage);
			}
		});
	}

	private void setReadApiValuesButtonAction() {
		readApiValuesButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				getApiValuesFromFile();
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

	private void setGetAuthorizationUrlButtonAction() {
		getAuthorizationUrlButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				generateAuthorizationUrl();
			}
		});
	}

	private void setPostTweetButtonAction() {
		postTweetButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToWriteApiInputFieldsToFile();
				tryToPostTweet();
			}
		});
	}

	private void setBackToApiButtonAction(Stage primaryStage) {
		backToApiButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToApiScene(primaryStage);
			}
		});
	}

	private void switchSceneToGrid(Stage primaryStage) {
		primaryStage.setScene(tweetPostScene);
	}
	
	private void switchSceneToApiScene(Stage primaryStage) {
		primaryStage.setScene(apiScene);
	}

	private void setApiValues() {
		apiKey = apiKeyInputField.getText();
		apiSecret = apiSecretInputField.getText();
	}

	private void getApiValuesFromFile() {
		ReadAPIValuesFromFile apiValueFileReader = new ReadAPIValuesFromFile("twitter-api-values/api-values.txt");
		apiValueFileReader.tryTtoReadFromFile();
		String apiKeyFromFile = apiValueFileReader.getAPIKey();
		String apiSecretFromFile = apiValueFileReader.getAPISecret();
		apiKeyInputField.setText(apiKeyFromFile);
		apiSecretInputField.setText(apiSecretFromFile);
	}

	private void writeApiValuesToFile() {
		String apiKeyValueToWrite = apiKeyInputField.getText();
		String apiSecretValueToWrite = apiSecretInputField.getText();
		APIValueFileWriter apiValueFileWriter = new APIValueFileWriter(apiKeyValueToWrite, apiSecretValueToWrite);
		apiValueFileWriter.tryToWriteToJsonFile();
	}

	private void generateAuthorizationUrl() {
		createOAuthInstance();
		oAuth.createOAuthService();
		oAuth.createRequestToken();
		oAuth.createAuthorizationUrl();
		displayAuthorizationUrl();
	}

	private void createOAuthInstance() {
		oAuth = new OAuth(apiKey, apiSecret);
	}

	private void displayAuthorizationUrl() {
		String authorizationUrl = oAuth.getAuthorizationUrl();
		authorizationUrlOutputField.setText(authorizationUrl);

	}

	private void tryToWriteApiInputFieldsToFile() {
		try {
			writeApiInputFieldsToFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeApiInputFieldsToFile() throws IOException {
		String apiKey = apiKeyInputField.getText();
		String apiSecret = apiSecretInputField.getText();
		APIValueFileWriter apiWriter = new APIValueFileWriter(apiKey, apiSecret);
		apiWriter.writeToJsonFile();
	}

	private void tryToPostTweet() {
		try {
			postTweet();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private void postTweet() throws UnsupportedEncodingException {
		String tweetText = tweetTextInputField.getText();
		String verifierCode = tokenVerifierInputField.getText();
		oAuth.createVerifier(verifierCode);
		oAuth.createAccessToken();
		TweetPoster tweetPoster = new TweetPoster(oAuth, tweetText);
		tweetPoster.tryToPostTweet();
	}

}