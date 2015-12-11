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
	private UIController controller;
	
	public VerifyUI(Stage primaryStage, UIController controller) {
		this.primaryStage = primaryStage;
		this.controller = controller;
	}
	
	public Scene getVerifyScene() {
		return verifyScene;
	}
	
	protected void setUp() {
		controller.setGrid(verifyGrid);
		addToVerifyGrid();
		configureTextFields();
		setGetAuthorizationUrlButtonAction();
		setBackToApiButtonAction(primaryStage);
		setSaveInfoButtonAction(primaryStage);
		setVerifySceneStyle();
	}
	
	private void setVerifySceneStyle(){
		verifyScene.getStylesheets().add("uiStyle.css");
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
				controller.switchSceneToApiScene(primaryStage);
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
		controller.createOAuthInstance();
		controller.oAuth.createOAuthService();
		controller.oAuth.createRequestToken();
		controller.oAuth.createAuthorizationUrl();
		displayAuthorizationUrl();
	}
	
	private void displayAuthorizationUrl() {
		String authorizationUrl = controller.oAuth.getAuthorizationUrl();
		authorizationUrlOutputField.setText(authorizationUrl);
	}
	
	private void tryToSaveInfo(Stage primaryStage) {
		try {
			saveInfo();
			controller.alertFactory.createConfirmAlert("Your account was saved!");
			controller.switchSceneToStartSceneAfterSave(primaryStage);
		} catch (Exception e) {
			controller.alertFactory.createErrorAlert("Given values did not save properly. Check the apiKey, apiSecret, AuthorizationCode and your Internet Connection");
		}
	}
	
	private void saveInfo() throws Exception {
		String verifierCode = tokenVerifierInputField.getText();
		controller.oAuth.createVerifier(verifierCode);
		controller.oAuth.createAccessToken();
		Token accessToken = controller.oAuth.getAccessToken();
		String tokenString = accessToken.getToken();
		String tokenSecret = accessToken.getSecret();
		UserValueFileWriter userWriter = new UserValueFileWriter(controller.getNewUserName(), tokenString, tokenSecret);
		userWriter.tryToWriteToJsonFile(controller.usersJSONObject);
	}

}
