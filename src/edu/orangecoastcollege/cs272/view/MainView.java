package edu.orangecoastcollege.cs272.view;

import java.io.File;

import edu.orangecoastcollege.cs272.controller.Controller;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public final class MainView extends Application {
	@SuppressWarnings("unused")
	private Controller controller;
    private static final String[] loadingScreenTexts = new String[] {
    		"Shovelling coal into the server…",
    		"Notifying field agents. Children acquired.",
    		"Scanning your device for credit card details. Please be patient…",
    		"Dividing eternity by zero, please be patient…",
    		"Just stalling to simulate activity…",
    		"Commencing infinite loop (this may take some time)",
    		"Communing with nature"
    		};
    
    public static MediaPlayer backgroundMusicPlayer;
    public static MediaPlayer clickSoundPlayer;
    public static MediaPlayer typingSoundPlayer;
    
    private static final String GREETINGS_MUSIC_PATH = "resources/sounds/menu/menu_greetings.mp3";
    private static final String HEROIC_DESIRE_MUSIC_PATH = "resources/sounds/battle/battle_music_heroic_desire.mp3";
    private static final String GRIMHEART_MUSIC_PATH = "resources/sounds/menu/menu_grimheart.mp3";
    
    static final String BACKGROUND_IMAGE_PATH = "resources/images/menu/menu_background.jpg";
    static final String CLICK_MUSIC_PATH = "resources/sounds/menu/menu_typing.mp3";
    static final String TYPING_MUSIC_PATH = "resources/sounds/menu/menu_typing.mp3";
    
	static final String MUSIC_OFF_PATH = "resources/images/menu/menu_music_sound_off.png";
	static final String MUSIC_ON_PATH = "resources/images/menu/menu_music_sound_on.png";
	static final String SOUND_ON_PATH = "resources/images/menu/menu_sound_on.png";
	static final String SOUND_OFF_PATH = "resources/images/menu/menu_sound_off.png";
	
	static final String PREVIOUS_SONG_PATH = "resources/images/menu/menu_previous_song.jpg";
	static final String PLAY_MUSIC_PATH = "resources/images/menu/menu_play.png";
	static final String PAUSE_MUSIC_PATH = "resources/images/menu/menu_pause.png";
	static final String SKIP_SONG_PATH = "resources/images/menu/menu_skip_song.png";
	
	static final String OOPS_PATH = "resources/images/menu/menu_oops.png";
	
	static final String FIELD_REQUIRED = "Field Required";
    
	static final Media GREETINGS_MUSIC = new Media(new File(GREETINGS_MUSIC_PATH).toURI().toString());
	static final Media HEROIC_DESIRE_MUSIC = new Media(new File(HEROIC_DESIRE_MUSIC_PATH).toURI().toString());
    static final Media GRIMHEART_MUSIC = new Media(new File(GRIMHEART_MUSIC_PATH).toURI().toString());
    
    static final double DEFAULT_SOUND_LEVEL = 0.7;
    static final double DEFAULT_MUSIC_LEVEL = 0.5;
    
	@Override
	public void start(final Stage primaryStage) throws Exception {
		backgroundMusicPlayer = new MediaPlayer(GREETINGS_MUSIC);
		backgroundMusicPlayer.setAutoPlay(true);
		backgroundMusicPlayer.setVolume(DEFAULT_MUSIC_LEVEL);
		backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
			public void run() {
				backgroundMusicPlayer.seek(Duration.ZERO);
			}
		});

		final Media clickSound = new Media(new File(CLICK_MUSIC_PATH).toURI().toString());
		clickSoundPlayer = new MediaPlayer(clickSound);
		clickSoundPlayer.setVolume(DEFAULT_SOUND_LEVEL);

		final Media typingSound = new Media(new File(TYPING_MUSIC_PATH).toURI().toString());
		typingSoundPlayer = new MediaPlayer(typingSound);
		typingSoundPlayer.setVolume(DEFAULT_SOUND_LEVEL);
		
		ViewNavigator.setStage(primaryStage);
		//ViewNavigator.loadScene("Adventure Game", ViewNavigator.STARTUP_SCREEN);
		ViewNavigator.loadScene("Adventure Game: Sign In", ViewNavigator.SIGN_IN_SCENE);
		
		controller = Controller.getInstance();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}