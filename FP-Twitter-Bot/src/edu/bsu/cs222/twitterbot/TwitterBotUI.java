package edu.bsu.cs222.twitterbot;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TwitterBotUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	private String apiKey;
	private String apiSecret;
	private OAuth oAuth;
	
	private ComboBox<String> userSelector = new ComboBox<>();
	private Label userSelectorLabel = new Label("Select User To Tweet From");
	private Label addNewUserLabel = new Label("Add A New User");
	private Label startTweetingLabel = new Label("Start Tweeting Now");
	private Button addNewUserButton = new Button("Add User");
	private Button startTweetingButton = new Button("Start Tweeting");
	private GridPane startGrid = new GridPane();
	private Scene startScene = new Scene(startGrid);

	private GridPane apiGrid = new GridPane();
	private TextField apiKeyInputField = new TextField();
	private TextField apiSecretInputField = new TextField();
	private Label apiKeyLabel = new Label("API Key");
	private Label apiSecretLabel = new Label("API Secret");
	private Button apiBackButton = new Button("Previous");
	private Button apiNextButton = new Button("Next");
	private Button readApiValuesButton = new Button("Get Saved API Values");
	private Button writeApiValuesButton = new Button("Save API Values");
	private Scene apiScene = new Scene(apiGrid);

	private GridPane verifyGrid = new GridPane();
	private TextField authorizationUrlOutputField = new TextField();
	private TextField tokenVerifierInputField = new TextField();
	private TextArea tweetTextInputField = new TextArea();
	private Button backToApiButton = new Button("Previous");
	private Button getAuthorizationUrlButton = new Button("Get Authorization URL");
	private Button saveInfoButton = new Button("Save Info");
	private Label tokenVerifierLabel = new Label("Token Verifier Code");
	private Scene verifyScene = new Scene(verifyGrid);
	
	private GridPane tweetPostGrid = new GridPane();
	private HBox hbButtons = new HBox();
	private Button gifButton = new Button("Get Gif");
	private Button postTweetButton = new Button("Post Tweet");
	private Label tweetTextLabel = new Label("Tweet Text Content");
	private Scene tweetPostScene = new Scene(tweetPostGrid);
	private final int tweetLimit = 140;

	@Override
	public void start(Stage primaryStage) {
		setGrid(startGrid);
		addToStartGrid();
		setGrid(apiGrid);
		addtoApiGrid();
		setGrid(verifyGrid);
		addToVerifyGrid();
		setGrid(tweetPostGrid);
		addtoTweetPostGrid();
		configureTweetCharacterLimit();
		configureTextFields();
		configureComboBox();
		setButtonActions(primaryStage);
		setStage(primaryStage);
	}

	private void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
		hbButtons.setSpacing(10.0);
	}
	
	private void addToStartGrid() {
		startGrid.add(userSelectorLabel, 0, 0);
		startGrid.add(userSelector, 1, 0);
		startGrid.add(addNewUserLabel, 0, 1);
		startGrid.add(addNewUserButton, 1, 1);
		startGrid.add(startTweetingLabel, 0, 2);
		startGrid.add(startTweetingButton, 1, 2);
	}

	private void addtoApiGrid() {
		apiGrid.add(apiKeyInputField, 1, 0);
		apiGrid.add(apiSecretInputField, 1, 1);
		apiGrid.add(apiKeyLabel, 0, 0);
		apiGrid.add(apiSecretLabel, 0, 1);
		apiGrid.add(readApiValuesButton, 0, 2);
		apiGrid.add(writeApiValuesButton, 1, 2);
		apiGrid.add(apiBackButton, 0, 3);
		apiGrid.add(apiNextButton, 1, 3);
	}
	private void addToVerifyGrid() {
		verifyGrid.add(authorizationUrlOutputField, 1, 0);
		verifyGrid.add(tokenVerifierInputField, 1, 1);
		verifyGrid.add(backToApiButton, 0, 2);
		verifyGrid.add(saveInfoButton, 1, 2);
		verifyGrid.add(getAuthorizationUrlButton, 0, 0);
		verifyGrid.add(tokenVerifierLabel, 0, 1);
	}

	private void addtoTweetPostGrid() {
		tweetPostGrid.add(tweetTextInputField, 1, 4);
		tweetPostGrid.add(tweetTextLabel, 0, 4);
		hbButtons.getChildren().addAll (gifButton, postTweetButton);
		tweetPostGrid.add(hbButtons, 1, 5, 2, 1);
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
	
	private void configureComboBox() {
		userSelector.getItems().addAll("None");
		userSelector.setValue("None");
	}

	private void setButtonActions(Stage primaryStage) {
		setStartTweetingButtonAction(primaryStage);
		setReadApiValuesButtonAction();
		setWriteApiValuesButtonAction();
		setApiBackButtonAction(primaryStage);
		setApiNextButtonAction(primaryStage);
		setGetAuthorizationUrlButtonAction();
		setBackToApiButtonAction(primaryStage);
		setSaveInfoButtonAction();
		setGifButtonAction();
		setPostTweetButtonAction();
	}

	private void setStage(Stage primaryStage) {
		primaryStage.setTitle("Twitter Bot");
		primaryStage.setScene(startScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	private void setStartTweetingButtonAction(Stage primaryStage) {
		startTweetingButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToApiScene(primaryStage);
			}
		});
	}
	
	private void setApiBackButtonAction(Stage primaryStage) {
		apiBackButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToStartScene(primaryStage);
			}
		});
	}

	private void setApiNextButtonAction(Stage primaryStage) {
		apiNextButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				setApiValues();
				switchSceneToVerifyScene(primaryStage);
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

	private void setBackToApiButtonAction(Stage primaryStage) {
		backToApiButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToApiScene(primaryStage);
			}
		});
	}
	
	private void setSaveInfoButtonAction() {
		saveInfoButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToSaveInfo();
			}
		});
	}
	
	private void switchSceneToStartScene(Stage primaryStage) {
		primaryStage.setScene(startScene);
	}

	private void switchSceneToVerifyScene(Stage primaryStage) {
		primaryStage.setScene(verifyScene);
	}

	private void switchSceneToApiScene(Stage primaryStage) {
		primaryStage.setScene(apiScene);
	}

	private void setApiValues() {
		apiKey = apiKeyInputField.getText();
		apiSecret = apiSecretInputField.getText();
	}

	private void getApiValuesFromFile() {
		ParseFromJSONFile apiValueFileReader = new ParseFromJSONFile("twitter-api-values/api-values.txt");
		JSONObject apiFileObject = apiValueFileReader.tryTtoReadFromFile();
		String apiKeyFromFile = apiValueFileReader.parseOutObjectValue("apiKey", apiFileObject);
		String apiSecretFromFile = apiValueFileReader.parseOutObjectValue("apiSecret", apiFileObject);
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
	
	private void tryToSaveInfo() {
		try {
			saveInfo();
		} catch (Exception e) {
			alertUserToSaveError();
		}
	}
	
	private void saveInfo() throws Exception {
	}
	
	private void alertUserToSaveError(){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Something went wrong!");
		alert.setContentText("Given values did not save properly. Check the apiKey, apiSecret, AuthorizationCode and your Internet Connection");
		alert.showAndWait();
	}
	
	private void tryToGenerateGif() {
		try {
			generateGif();
		} catch (ParseException | IOException e) {
			tweetTextInputField.setText("Error Getting The Gif URL");
		}
	}
	
	private void generateGif() throws ParseException, IOException {
		GiphyConnection giphyConnection = new  GiphyConnection("cat");
		URLConnection  connection = giphyConnection.connectToGiphy();
		GiphyJSONParser giphyParser = new GiphyJSONParser(connection);
		String gifURL = giphyParser.parseOutURL();
		tweetTextInputField.setText(gifURL);
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