package edu.bsu.cs222.twitterbot;

import javafx.application.Application;
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

	private GridPane grid = new GridPane();
	private TextField apiKeyInputField = new TextField();
	private TextField apiSecretInputField = new TextField();
	private TextField tokenVerifierInputField = new TextField();
	private TextArea tweetTextInputField = new TextArea();
	private Button postTweetButton = new Button("Post Tweet");
	private Button getVerifyURLButton = new Button("Get Verify URL");
	private Label apiKeyLabel = new Label("API Key");
	private Label apiSecretLabel = new Label ("API Secret");
	private Label tokenVerifierLabel = new Label( "Token Verifier Code" );
	private Label tweetTextLabel = new Label( "Tweet Text Content" );
	private Scene scene = new Scene(grid);

	@Override
	public void start(Stage primaryStage) {
		setGrid(grid);
		configureTextFields();
		setStage(primaryStage);

	}

	private void setStage(Stage primaryStage) {
		primaryStage.setTitle("Twitter Bot");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();

	}

	private void setGrid(GridPane grid) {
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(15);
		grid.setVgap(15);
		grid.setPadding(new Insets(30, 30, 30, 30));
		addtoGrid();
	}

	private void addtoGrid() {
		grid.add(apiKeyInputField, 1, 0);
		grid.add(apiSecretInputField, 1, 1);
		grid.add(tokenVerifierInputField, 1, 2);
		grid.add(tweetTextInputField, 1 , 3);
		grid.add(postTweetButton, 1, 4);
		grid.add(getVerifyURLButton, 0, 4);
		grid.add(apiKeyLabel, 0, 0);
		grid.add(apiSecretLabel, 0, 1);
		grid.add(tokenVerifierLabel, 0, 2);
		grid.add(tweetTextLabel, 0, 3);
	}
	
	private void configureTextFields() {
		apiKeyInputField.setPromptText("Paste in API Key");
		apiSecretInputField.setPromptText("Paste in API Secret");
		tokenVerifierInputField.setPromptText( "Paste in Verify Code" );
		tweetTextInputField.setPromptText( "Tweet limited to 140 Characters" );
		tweetTextInputField.setPrefRowCount(5);
		tweetTextInputField.setPrefColumnCount(15);
		tweetTextInputField.setWrapText(true);
	}
	
}