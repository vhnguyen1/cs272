package edu.orangecoastcollege.cs272.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ViewNavigator {
	public static final String SIGN_UP_SCENE = "SignUpScene.fxml";
	public static final String SIGN_IN_SCENE = "SignInScene.fxml";
	public static final String MAIN_MENU_SCENE = "MainMenuScene.fxml";
    public static final String START_SCENE = "GameStartUpView.fxml";
    public static final String OPTION_SCENE = "OptionView.fxml";
    public static final String CHARACTER_SELECT_SCENE = "CharacterSelectView.fxml";
    public static final String CHARACTER_CREATE_SCENE = "CharacterCreateView.fxml";
    public static final String GAME_VIEW = "GameView.fxml";
    public static final String CHARACTER_SKILL_SCENE = "";
    public static final String INVENTORY_SCENE = "";
    public static final String EQUIPMENT_SCENE = "";
    public static final String BOSS_SCENE = "";
    public static final String GAME_OVER_SCENE = "";
    public static final String MONSTER_BOOK_SCENE = "";

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