package ui;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("inventory-viewer.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Inventory manager");
		primaryStage.setResizable(false);
		primaryStage.getIcons().add(new Image(new File("images/icon.jpeg").toURI().toString()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
