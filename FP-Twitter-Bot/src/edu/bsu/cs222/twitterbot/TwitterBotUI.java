package edu.bsu.cs222.twitterbot;

import java.util.Iterator;

import org.json.simple.JSONObject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TwitterBotUI extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	protected String apiKey;
	protected String apiSecret;
	private String selectedUser;
	private ParseFromJSONFile usersParser;
	protected JSONObject usersJSONObject;
	protected OAuth oAuth;
	protected AlertFactory alertFactory = new AlertFactory();
	private AutoTweetUI autoUI;
	private ManualTweetUI manualUI;
	private TypeOfTweetUI tweetTypeUI;
	private ApiUI apiUI;
	private VerifyUI verifyUI;
	
	private ComboBox<String> userSelector = new ComboBox<>();
	private Label userSelectorLabel = new Label("Select User To Tweet From");
	private Label addNewUserLabel = new Label("Add A New User");
	private Label startTweetingLabel = new Label("Start Tweeting Now");
	private Button addNewUserButton = new Button("Add User");
	private Button startTweetingButton = new Button("Start Tweeting");
	private GridPane startGrid = new GridPane();
	private Scene startScene = new Scene(startGrid);

	@Override
	public void start(Stage primaryStage) {
		setAllGrids();
		addToAllGrids();
		configureComboBox();
		
		setButtonActions(primaryStage);
		setStage(primaryStage);
		autoUI = new AutoTweetUI(primaryStage, this);
		autoUI.setUp();
		manualUI = new ManualTweetUI(primaryStage, this);
		manualUI.setUp();
		tweetTypeUI = new TypeOfTweetUI(primaryStage, this);
		tweetTypeUI.setUp();
		apiUI = new ApiUI(primaryStage, this);
		apiUI.setUp();
		verifyUI = new VerifyUI(primaryStage, this);
		verifyUI.setUp();
	}
	
	private void setAllGrids(){
		setGrid(startGrid);
	}

	protected void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
	}
	
	private void addToAllGrids() {
		addToStartGrid();
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
				switchSceneToTweetTypeScene(primaryStage);
			}
		});
	}
	
	protected void switchSceneToApiScene(Stage primaryStage) {
		primaryStage.setScene(apiUI.getAPIScene());
	}
	
	protected void switchSceneToTweetTypeScene(Stage primaryStage) {
		selectedUser = this.userSelector.getValue();
		if (selectedUser.equals("None")){
			alertFactory.createInfoAlert("You must select a username to start tweeting.");
		} else {
			primaryStage.setScene(tweetTypeUI.getTweetTypeTweetScene());
			setUpTweetService();
		}
	}
	
	private void setUpTweetService() {
		getApiValuesFromFile();
		createOAuthInstance();
		oAuth.createOAuthService();
		JSONObject userObject = usersParser.parseOutObject( selectedUser, usersJSONObject);
		String tokenString = usersParser.parseOutObjectValue("tokenString", userObject);
		String tokenSecret = usersParser.parseOutObjectValue("tokenSecret", userObject);
		oAuth.createAccessTokenFromValues(tokenString, tokenSecret);
	}
	
	protected void switchSceneToTweetScene(Stage primaryStage) {
		primaryStage.setScene(manualUI.getManualTweetScene());
}
	
	protected void switchSceneToAutomaticTweetScene(Stage primaryStage) {
			primaryStage.setScene(autoUI.getAutomaticTweetScene());
	}
	
	protected void getApiValuesFromFile() {
		ParseFromJSONFile apiValueFileReader = new ParseFromJSONFile("twitter-values/api-values.json");
		JSONObject apiFileObject = apiValueFileReader.tryTtoReadFromFile();
		String apiKeyFromFile = apiValueFileReader.parseOutObjectValue("apiKey", apiFileObject);
		String apiSecretFromFile = apiValueFileReader.parseOutObjectValue("apiSecret", apiFileObject);
		apiKey = apiKeyFromFile;
		apiSecret = apiSecretFromFile;
	}
	
	protected void switchSceneToStartScene(Stage primaryStage) {
		primaryStage.setScene(startScene);
	}
	
	protected void switchSceneToVerifyScene(Stage primaryStage) {
		primaryStage.setScene(verifyUI.getVerifyScene());
	}
	
	protected void createOAuthInstance() {
		oAuth = new OAuth(apiKey, apiSecret);
	}
	
	protected void switchSceneToStartSceneAfterSave(Stage primaryStage) {
		String userName = apiUI.userNameInputField.getText();
		userSelector.getItems().add(userName);
		primaryStage.setScene(startScene);
	}
	
	protected String getInputedNewUserName() {
		return apiUI.userNameInputField.getText();
	}

}