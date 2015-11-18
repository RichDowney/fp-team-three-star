package edu.bsu.cs222.twitterbot;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.scribe.model.Token;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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
	private String selectedUser;
	private ParseFromJSONFile usersParser;
	private JSONObject usersJSONObject;
	private OAuth oAuth;
	private AlertFactory alertFactory = new AlertFactory();
	
	private ComboBox<String> userSelector = new ComboBox<>();
	private Label userSelectorLabel = new Label("Select User To Tweet From");
	private Label addNewUserLabel = new Label("Add A New User");
	private Label startTweetingLabel = new Label("Start Tweeting Now");
	private Button addNewUserButton = new Button("Add User");
	private Button startTweetingButton = new Button("Start Tweeting");
	private GridPane startGrid = new GridPane();
	private Scene startScene = new Scene(startGrid);

	private GridPane apiGrid = new GridPane();
	private TextField userNameInputField = new TextField();
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
	private TextField giphyInputField = new TextField();
	private Button gifButton = new Button("Get Gif");
	private Button postTweetButton = new Button("Post Tweet");
	private Label giphyLabel = new Label("Giphy Tag");
	private Label tweetTextLabel = new Label("Tweet Text Content");
	private Scene tweetPostScene = new Scene(tweetPostGrid);
	private final int tweetLimit = 140;

	@Override
	public void start(Stage primaryStage) {
		setAllGrids();
		addToAllGrids();
		configureTextFields();
		configureComboBox();
		setButtonActions(primaryStage);
		setStage(primaryStage);
	}
	
	private void setAllGrids(){
		setGrid(startGrid);
		setGrid(apiGrid);
		setGrid(verifyGrid);
		setGrid(tweetPostGrid);
	}

	private void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
		hbButtons.setSpacing(10.0);
	}
	
	private void addToAllGrids() {
		addToStartGrid();
		addtoApiGrid();
		addToVerifyGrid();
		addtoTweetPostGrid();
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
	private void addToVerifyGrid() {
		verifyGrid.add(authorizationUrlOutputField, 1, 0);
		verifyGrid.add(tokenVerifierInputField, 1, 1);
		verifyGrid.add(backToApiButton, 0, 2);
		verifyGrid.add(saveInfoButton, 1, 2);
		verifyGrid.add(getAuthorizationUrlButton, 0, 0);
		verifyGrid.add(tokenVerifierLabel, 0, 1);
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
		apiKeyInputField.setPromptText("Paste in API Key");
		apiSecretInputField.setPromptText("Paste in API Secret");
		authorizationUrlOutputField.setPromptText("Goto for Authorization Code");
		authorizationUrlOutputField.setEditable(false);
		tokenVerifierInputField.setPromptText("Paste in Authorization Code");
		tweetTextInputField.setPromptText("Tweet limited to 140 Characters");
		giphyInputField.setPromptText("Enter Giphy Search Term");
		tweetTextInputField.setPrefRowCount(5);
		tweetTextInputField.setPrefColumnCount(15);
		tweetTextInputField.setWrapText(true);
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
	
	private void configureComboBox() {
		userSelector.getItems().add("None");
		userSelector.setValue("None");
		addUsersFromFile();
	}
	
	private void addUsersFromFile() {
		usersParser = new ParseFromJSONFile("twitter-values/users.json");
		usersJSONObject = usersParser.tryTtoReadFromFile();
		if(usersJSONObject != null){	
			for(Iterator<?> iterator = usersJSONObject.keySet().iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				userSelector.getItems().add(key);
			}
		}
	}

	private void setButtonActions(Stage primaryStage) {
		setAddNewUserButtonAction(primaryStage);
		setStartTweetingButton(primaryStage);
		setReadApiValuesButtonAction();
		setWriteApiValuesButtonAction();
		setApiBackButtonAction(primaryStage);
		setApiNextButtonAction(primaryStage);
		setGetAuthorizationUrlButtonAction();
		setBackToApiButtonAction(primaryStage);
		setSaveInfoButtonAction(primaryStage);
		setGifButtonAction();
		setPostTweetButtonAction();
	}

	private void setStage(Stage primaryStage) {
		primaryStage.setTitle("Twitter Bot");
		primaryStage.setScene(startScene);
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	
	private void setAddNewUserButtonAction(Stage primaryStage) {
		addNewUserButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToApiScene(primaryStage);
			}
		});
	}
	
	private void setStartTweetingButton(Stage primaryStage) {
		startTweetingButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				switchSceneToTweetScene(primaryStage);
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

	private void setGetAuthorizationUrlButtonAction() {
		getAuthorizationUrlButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				generateAuthorizationUrl();
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
	
	private void setSaveInfoButtonAction(Stage primaryStage) {
		saveInfoButton.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				tryToSaveInfo(primaryStage);
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
	
	private void switchSceneToApiScene(Stage primaryStage) {
		primaryStage.setScene(apiScene);
	}
	
	private void switchSceneToTweetScene(Stage primaryStage) {
		selectedUser = this.userSelector.getValue();
		if (selectedUser.equals("None")){
			alertFactory.createInfoAlert("You must select a username to start tweeting.");
		} else {
			primaryStage.setScene(tweetPostScene);
		}
	}
	
	private void getApiValuesFromFile() {
		ParseFromJSONFile apiValueFileReader = new ParseFromJSONFile("twitter-values/api-values.json");
		JSONObject apiFileObject = apiValueFileReader.tryTtoReadFromFile();
		String apiKeyFromFile = apiValueFileReader.parseOutObjectValue("apiKey", apiFileObject);
		String apiSecretFromFile = apiValueFileReader.parseOutObjectValue("apiSecret", apiFileObject);
		apiKey = apiKeyFromFile;
		apiSecret = apiSecretFromFile;
	}
	
	private void setApiValues() {
		apiKeyInputField.setText(apiKey);
		apiSecretInputField.setText(apiSecret);
	}
	
	private void writeApiValuesToFile() {
		String apiKeyValueToWrite = apiKeyInputField.getText();
		String apiSecretValueToWrite = apiSecretInputField.getText();
		APIValueFileWriter apiValueFileWriter = new APIValueFileWriter(apiKeyValueToWrite, apiSecretValueToWrite);
		apiValueFileWriter.tryToWriteToJsonFile();
	}
	
	private void switchSceneToStartScene(Stage primaryStage) {
		primaryStage.setScene(startScene);
	}
	
	private void switchSceneToVerifyScene(Stage primaryStage) {
		primaryStage.setScene(verifyScene);
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
	
	private void tryToSaveInfo(Stage primaryStage) {
		try {
			saveInfo();
			alertFactory.createConfirmAlert("Your account was saved!");
			switchSceneToStartSceneAfterSave(primaryStage);
		} catch (Exception e) {
			alertFactory.createErrorAlert("Given values did not save properly. Check the apiKey, apiSecret, AuthorizationCode and your Internet Connection");
		}
	}
	
	private void saveInfo() throws Exception {
		String verifierCode = tokenVerifierInputField.getText();
		oAuth.createVerifier(verifierCode);
		oAuth.createAccessToken();
		Token accessToken = oAuth.getAccessToken();
		String tokenString = accessToken.getToken();
		String tokenSecret = accessToken.getSecret();
		String userName = userNameInputField.getText();
		UserValueFileWriter userWriter = new UserValueFileWriter(userName, tokenString, tokenSecret);
		userWriter.tryToWriteToJsonFile(usersJSONObject);
	}
	
	private void switchSceneToStartSceneAfterSave(Stage primaryStage) {
		String userName = userNameInputField.getText();
		userSelector.getItems().add(userName);
		primaryStage.setScene(startScene);
	}
	
	private void tryToGenerateGif() {
		try {
			generateGif();
		} catch (ParseException | IOException e) {
			alertFactory.createErrorAlert("Error getting the Giphy URL, check your internet connection");
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
			alertFactory.createConfirmAlert("Tweet Successfully Posted!");
		} catch (UnsupportedEncodingException e) {
			alertFactory.createErrorAlert("Error posting tweet to your Twitter account, check your internet connection and tweet content");
		}
	}

	private void postTweet() throws UnsupportedEncodingException {
		getApiValuesFromFile();
		createOAuthInstance();
		oAuth.createOAuthService();
		JSONObject userObject = usersParser.parseOutObject( selectedUser, usersJSONObject);
		String tokenString = usersParser.parseOutObjectValue("tokenString", userObject);
		String tokenSecret = usersParser.parseOutObjectValue("tokenSecret", userObject);
		String tweetText = tweetTextInputField.getText();
		oAuth.createAccessTokenFromValues(tokenString, tokenSecret);
		TweetPoster tweetPoster = new TweetPoster(oAuth, tweetText);
		tweetPoster.tryToPostTweet();
	}

}