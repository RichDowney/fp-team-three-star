import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class TwitterBotUI extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	private GridPane grid = new GridPane();
	private TextField apiKeyInputField = new TextField();
	private TextField apiSecretInputField = new TextField();
	private Label apiKeyLabel = new Label("API Key");
	private Label apiSecretLabel = new Label ("API Secret");
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
		grid.add(apiKeyInputField, 1, 1);
		grid.add(apiSecretInputField, 1, 3);
		grid.add(apiKeyLabel, 0, 1);
		grid.add(apiSecretLabel, 0, 3);
	}
	
	private void configureTextFields() {
		apiKeyInputField.setPromptText("Paste in API Key");
		apiSecretInputField.setPromptText("Paste in API Secret");
	}
	
}
