package edu.orangecoastcollege.cs272.view;

import javafx.event.ActionEvent;	
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.*;
import edu.orangecoastcollege.cs272.view.ViewNavigator;

@SuppressWarnings("deprecation")
public final class SignInScene implements Initializable {
	private static final Controller controller = Controller.getInstance();

	@FXML
	private TextField usernameTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private Label usernameErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private ImageView backgroundMusicButton;
	@FXML
	private ImageView soundButton;
	@FXML
	private Label currentlyPlayingLabel;
	@FXML
	private Label songNameLabel;
	@FXML
	private ImageView pauseMusicButton;
	@FXML
	private Slider volumeSlider;
	
	private Stage dialogStage;
	private HBox hbox;
	private Button continueButton;
	private Button helpButton;
	private GridPane grid;
	private Label errorResultLabel;
	private ImageView oopsImage;
	
	/* (non-Javadoc)
	 * @see javafx.fxml.Initializable#initialize(java.net.URL, java.util.ResourceBundle)
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        dialogStage = new Stage();
        dialogStage.setWidth(420);
        dialogStage.setHeight(250);
        dialogStage.setTitle("Invalid Field(s) Entered!");
        
		oopsImage = new ImageView(new Image(new File(MainView.OOPS_PATH).toURI().toString()));
		oopsImage.setFitWidth(120);
		oopsImage.setFitHeight(120);
		
		errorResultLabel = new Label();
		errorResultLabel.setAlignment(Pos.CENTER);
		errorResultLabel.setTextFill(Color.RED);
		errorResultLabel.setFont(Font.font("Calibri", FontPosture.ITALIC, Font.getDefault().getSize()));
		
        continueButton = new Button("Continue");
        continueButton.setFocusTraversable(false);
        helpButton = new Button("Help");
        helpButton.setFocusTraversable(false);
		hbox = new HBox(10);
		hbox.getChildren().addAll(continueButton, helpButton);
		hbox.setAlignment(Pos.CENTER);
		
		grid = new GridPane();
		grid.add(oopsImage, 0, 0);
		grid.add(errorResultLabel, 0, 1);
		grid.add(hbox, 0, 2);
		
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);
		
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.setScene(new Scene(VBoxBuilder.create().children(grid).
        alignment(Pos.CENTER).padding(new Insets(10)).build()));
        
        continueButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
            	playMenuClickSound();
               dialogStage.close();
            }
        });
        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override			
            public void handle(ActionEvent event){
               playMenuClickSound();
               dialogStage.close();
               loadHelpPage();
            }
        });
        
        if (MainView.backgroundMusicPlayer.isMute()) {
            volumeSlider.setValue(0.0);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_OFF_PATH).toURI().toString()));
        } else {
            volumeSlider.setValue(MainView.backgroundMusicPlayer.getVolume());
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
        }
        if (MainView.clickSoundPlayer.isMute())
			soundButton.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
        else
			soundButton.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));
	}

	@FXML
	public final Object signIn() {
	    final String username  = usernameTF.getText().trim();
	    final String password = passwordTF.getText().trim();   
        final String result = controller.signIn(username, password);
    	
        if (result.equalsIgnoreCase(Controller.SUCCESS)) {
        	System.out.println(result);
            //ViewNavigator.loadScene("Video Games List", ViewNavigator.MAIN_MENU_SCENE);
        } else {
    	    errorResultLabel.setText(result);
    	    dialogStage.show();
    	    
    	    if (username.isEmpty() && password.isEmpty()) {
    	    	usernameErrorLabel.setText(MainView.FIELD_REQUIRED);
    	    	passwordErrorLabel.setText(MainView.FIELD_REQUIRED);
    	    	usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(true);
    	    }
    	    else if (username.isEmpty()) {
    	    	usernameErrorLabel.setText(MainView.FIELD_REQUIRED);
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(false);
    	    }
    	    else if (password.isEmpty()) {
    	    	passwordErrorLabel.setText(MainView.FIELD_REQUIRED);
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(true);
    	    } 
    	    else if (result.equals(Controller.COMBINATION_INCORRECT)) {
    	    	usernameErrorLabel.setText("Invalid Username");
    	    	passwordErrorLabel.setText("Invalid Password");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(true);
    	    }
    	    else if (result.equals(Controller.PASSWORD_INCORRECT)) {
    	    	passwordErrorLabel.setText("Invalid Password");
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(true);
    	    }
        }
        return null;
	}
	
	@FXML
	public final Object loadHelpPage() {
		ViewNavigator.loadScene("Adventure Game: Sign Up", ViewNavigator.SIGN_UP_SCENE);
		return this;
	}

	@FXML
	public final Object loadSignUp() {
		ViewNavigator.loadScene("Adventure Game: Sign Up", ViewNavigator.SIGN_UP_SCENE);
		return this;
	}
	
	@FXML
	public final void playMenuClickSound() {
		MainView.clickSoundPlayer.play();
		MainView.clickSoundPlayer.seek(Duration.ZERO);
	}
	
	@FXML
	public final void playMenuTypeSound() {
		MainView.typingSoundPlayer.play();
		MainView.typingSoundPlayer.seek(Duration.ZERO);
	}
	
	@FXML
	public final void changeSoundStatus() {
		if (MainView.clickSoundPlayer.getVolume() > 0.0) {
			MainView.clickSoundPlayer.setVolume(0.0);
			MainView.typingSoundPlayer.setVolume(0.0);
			soundButton.setImage(new Image(new File(MainView.SOUND_OFF_PATH).toURI().toString()));
		} else {
			MainView.clickSoundPlayer.setVolume(MainView.DEFAULT_SOUND_LEVEL);
			MainView.typingSoundPlayer.setVolume(MainView.DEFAULT_SOUND_LEVEL);
			soundButton.setImage(new Image(new File(MainView.SOUND_ON_PATH).toURI().toString()));
		}
	}
	
	@FXML
	public void changeBackgroundSongStatus() {
		if (!MainView.backgroundMusicPlayer.isMute()) {
			MainView.backgroundMusicPlayer.setMute(true);
			volumeSlider.setValue(0.0);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_OFF_PATH).toURI().toString()));
		} else {
			MainView.backgroundMusicPlayer.setMute(false);
			volumeSlider.setValue(MainView.backgroundMusicPlayer.getVolume());
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
		}
	}
	
	@FXML
	public final void restartSong() {
		MainView.backgroundMusicPlayer.pause();
		MainView.backgroundMusicPlayer.seek(Duration.ZERO);
		MainView.backgroundMusicPlayer.play();
	}
	
	@FXML
	public final void pauseSong() {
		if (MainView.backgroundMusicPlayer.getStatus().equals(Status.PLAYING)) {
			MainView.backgroundMusicPlayer.pause();
			currentlyPlayingLabel.setText("Currently Paused:");
			pauseMusicButton.setImage(new Image(new File(MainView.PLAY_MUSIC_PATH).toURI().toString()));
		} else {
			MainView.backgroundMusicPlayer.play();
			currentlyPlayingLabel.setText("Currently Playing:");
			pauseMusicButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
		}
	}
	
	@FXML
	public final void skipSong() {
		if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GREETINGS_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);
			
			songNameLabel.setText("Heroic Desire");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.HEROIC_DESIRE_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		} else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.HEROIC_DESIRE_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);
			
			songNameLabel.setText("Grimheart");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.GRIMHEART_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		} else if (MainView.backgroundMusicPlayer.getMedia().equals(MainView.GRIMHEART_MUSIC)) {
			MainView.backgroundMusicPlayer.stop();
			MainView.backgroundMusicPlayer.seek(Duration.ZERO);
			
			songNameLabel.setText("Greetings");
			MainView.backgroundMusicPlayer = new MediaPlayer(MainView.GREETINGS_MUSIC);
			MainView.backgroundMusicPlayer.setAutoPlay(true);
			MainView.backgroundMusicPlayer.setVolume(volumeSlider.getValue());
			MainView.backgroundMusicPlayer.setOnEndOfMedia(new Runnable() {
				public void run() {
					MainView.backgroundMusicPlayer.seek(Duration.ZERO);
				}
			});
		}	
		pauseMusicButton.setImage(new Image(new File(MainView.PAUSE_MUSIC_PATH).toURI().toString()));
	}
	
	@FXML
	public final void adjustVolume() {
		final double value = volumeSlider.getValue();
		MainView.backgroundMusicPlayer.setVolume(value);
		
		if (MainView.backgroundMusicPlayer.isMute() && value > 0.0) {
			MainView.backgroundMusicPlayer.setMute(false);
			backgroundMusicButton.setImage(new Image(new File(MainView.MUSIC_ON_PATH).toURI().toString()));
		}
	}
}