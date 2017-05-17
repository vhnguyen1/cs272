package edu.orangecoastcollege.cs272.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ViewNavigator {
	public static final String SIGN_UP_SCENE = "SignUpScene.fxml";
	public static final String SIGN_IN_SCENE = "SignInScene.fxml";
	public static final String MAIN_MENU_SCENE = "MainMenuScene.fxml";

	public static Stage mainStage;

	public static void setStage(final Stage stage) {
		mainStage = stage;
	}

	public static void loadScene(final String title, final String sceneFXML) {
		try {
			mainStage.setTitle(title);
			final Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setScene(scene);
			mainStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
		}
	}
}