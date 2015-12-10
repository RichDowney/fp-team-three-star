package edu.bsu.cs222.twitterbot;

import org.scribe.model.Token;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VerifyUI {
	private GridPane verifyGrid = new GridPane();
	private TextField authorizationUrlOutputField = new TextField();
	private TextField tokenVerifierInputField = new TextField();
	private Button backToApiButton = new Button("Previous");
	private Button getAuthorizationUrlButton = new Button("Get Authorization URL");
	private Button saveInfoButton = new Button("Save Info");
	private Label tokenVerifierLabel = new Label("Token Verifier Code");
	private Scene verifyScene = new Scene(verifyGrid);
	private Stage primaryStage;
	private TwitterBotUI twitterBotUI;
	
	public VerifyUI(Stage primaryStage, TwitterBotUI twitterBotUI) {
		this.primaryStage = primaryStage;
		this.twitterBotUI = twitterBotUI;
	}
	
	public Scene getVerifyScene() {
		return verifyScene;
	}
	
	protected void setUp() {
		twitterBotUI.setGrid(verifyGrid);
		addToVerifyGrid();
		configureTextFields();
		setGetAuthorizationUrlButtonAction();
		setBackToApiButtonAction(primaryStage);
		setSaveInfoButtonAction(primaryStage);
	}
	
	private void addToVerifyGrid() {
		verifyGrid.add(authorizationUrlOutputField, 1, 0);
		verifyGrid.add(tokenVerifierInputField, 1, 1);
		verifyGrid.add(backToApiButton, 0, 2);
		verifyGrid.add(saveInfoButton, 1, 2);
		verifyGrid.add(getAuthorizationUrlButton, 0, 0);
		verifyGrid.add(tokenVerifierLabel, 0, 1);
	}
	
	private void configureTextFields() {
		authorizationUrlOutputField.setPromptText("Goto for Authorization Code");
		authorizationUrlOutputField.setEditable(false);
		tokenVerifierInputField.setPromptText("Paste in Authorization Code");
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
				twitterBotUI.switchSceneToApiScene(primaryStage);
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
	
	private void generateAuthorizationUrl() {
		twitterBotUI.createOAuthInstance();
		twitterBotUI.oAuth.createOAuthService();
		twitterBotUI.oAuth.createRequestToken();
		twitterBotUI.oAuth.createAuthorizationUrl();
		displayAuthorizationUrl();
	}
	
	private void displayAuthorizationUrl() {
		String authorizationUrl = twitterBotUI.oAuth.getAuthorizationUrl();
		authorizationUrlOutputField.setText(authorizationUrl);
	}
	
	private void tryToSaveInfo(Stage primaryStage) {
		try {
			saveInfo();
			twitterBotUI.alertFactory.createConfirmAlert("Your account was saved!");
			twitterBotUI.switchSceneToStartSceneAfterSave(primaryStage);
		} catch (Exception e) {
			twitterBotUI.alertFactory.createErrorAlert("Given values did not save properly. Check the apiKey, apiSecret, AuthorizationCode and your Internet Connection");
		}
	}
	
	private void saveInfo() throws Exception {
		String verifierCode = tokenVerifierInputField.getText();
		twitterBotUI.oAuth.createVerifier(verifierCode);
		twitterBotUI.oAuth.createAccessToken();
		Token accessToken = twitterBotUI.oAuth.getAccessToken();
		String tokenString = accessToken.getToken();
		String tokenSecret = accessToken.getSecret();
		UserValueFileWriter userWriter = new UserValueFileWriter(twitterBotUI.getNewUserName(), tokenString, tokenSecret);
		userWriter.tryToWriteToJsonFile(twitterBotUI.usersJSONObject);
	}

}
