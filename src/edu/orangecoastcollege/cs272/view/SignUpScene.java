package edu.orangecoastcollege.cs272.view;

import java.io.File;		
import java.net.URL;
import java.util.ResourceBundle;

import edu.orangecoastcollege.cs272.controller.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;


public class SignUpScene implements Initializable {

	private static final Controller controller = Controller.getInstance();

	@FXML
	private TextField usernameTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private TextField confirmPasswordTF;
	@FXML
	private Label signInLabel;
	@FXML
	private Label usernameErrorLabel;
	@FXML
	private Label passwordErrorLabel;
	@FXML
	private Label confirmPasswordErrorLabel;
	@FXML
	private ImageView signUpButton;
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
	private GridPane grid;
	private Label errorResultLabel;
	private TextArea accountCriteriaTA;
	private ImageView oopsImage;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
        dialogStage = new Stage();
        dialogStage.setWidth(500);
        dialogStage.setHeight(450);
        dialogStage.setTitle("Invalid Field(s) Entered!");
        
		oopsImage = new ImageView(new Image(new File(MainView.OOPS_PATH).toURI().toString()));
		oopsImage.setFitWidth(120);
		oopsImage.setFitHeight(120);
		
		errorResultLabel = new Label();
		errorResultLabel.setAlignment(Pos.CENTER);
		errorResultLabel.setTextFill(Color.RED);
		errorResultLabel.setFont(Font.font("Calibri", FontPosture.ITALIC, Font.getDefault().getSize()));
		
		accountCriteriaTA = new TextArea("Username must meet ALL of the following requirements:"
				+ "\n\t1.) The username must be at at least 4 (but no more than 20) characters long."
				+ "\n\t2.) The username may not contain any special characters aside from the following: ' ', '@', '.', '_'"
				+ "\n\t3.) The username may not start or end with spaces, (they will be removed)."
				+ "\n\t4.) The username must be new/unique. No duplicate usernames will be allowed."
				+ "\n\nPassword must meet ALL of the following requirements:"
				+ "\n\t1.) The password must be at least 8 (but no more than 20) characters long."
				+ "\n\t2.) The password must contain at least 1 alphabetical character."
				+ "\n\t3.) The password must contain at least 1 number."
				+ "\n\t4.) The password may not contain any special characters aside from the following: ' ', '@', '.', '_'"
				+ "\n\t5.) The password may not start or end with spaces, (they will be removed)."
				+ "\n\t6.) The password may not be the same as the username.");
		accountCriteriaTA.setEditable(false);
		accountCriteriaTA.setFocusTraversable(false);
		
        continueButton = new Button("Continue");
        continueButton.setFocusTraversable(false);
		hbox = new HBox(10);
		hbox.getChildren().addAll(continueButton);
		hbox.setAlignment(Pos.CENTER);
		
		grid = new GridPane();
		grid.add(errorResultLabel, 0, 1);
		grid.add(accountCriteriaTA, 0, 2);
		grid.add(hbox, 0, 3);
		grid.add(oopsImage, 0, 0);
		
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
	public final Object signUp() {
	    final String username = usernameTF.getText().trim();
	    final String password = passwordTF.getText().trim();
	    final String confirmPassword = confirmPasswordTF.getText().trim();
        final String result = controller.signUp(username, password, confirmPassword);
        
	    if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
    	    errorResultLabel.setText("Fields cannot be empty!");
    	    dialogStage.show();
    	    
	    	if (username.isEmpty()) {
	    		usernameErrorLabel.setText(MainView.FIELD_REQUIRED);
		    	usernameErrorLabel.setVisible(true);
	    	} else
		    	usernameErrorLabel.setVisible(false);
	    	if (password.isEmpty()) {
	    		passwordErrorLabel.setText(MainView.FIELD_REQUIRED);
		        passwordErrorLabel.setVisible(true);
	    	} else
		        passwordErrorLabel.setVisible(false);
	    	if (confirmPassword.isEmpty()) {
	    		confirmPasswordErrorLabel.setText(MainView.FIELD_REQUIRED);
		        confirmPasswordErrorLabel.setVisible(true);
	    	} else
	    		confirmPasswordErrorLabel.setVisible(false);
	        return this;
	    }

        if (result.equalsIgnoreCase("SUCCESS"))
            ViewNavigator.loadScene("Adventure Game", ViewNavigator.SIGN_IN_SCENE);
        else {
    	    if (result.equals(Controller.USERNAME_DOES_NOT_MEET_CRITERIA)) {
    	    	usernameErrorLabel.setText("Invalid Username");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.PASSWORD_DOES_NOT_MEET_CRITERIA)) {
    	    	passwordErrorLabel.setText("Invalid Password");
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(true);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.PASSWORDS_DO_NOT_MATCH)) {
	    		confirmPasswordErrorLabel.setText("Invalid Field");
    	        usernameErrorLabel.setVisible(false);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(true);
    	    } else if (result.equals(Controller.PASSWORD_SAME_AS_USER)) {
    	    	usernameErrorLabel.setText("Same As Password");
    	    	passwordErrorLabel.setText("Same As Username");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(true);
		        confirmPasswordErrorLabel.setVisible(false);
    	    } else if (result.equals(Controller.USER_EXISTS)) {
    	    	usernameErrorLabel.setText("Username Already Exists");
    	        usernameErrorLabel.setVisible(true);
    	        passwordErrorLabel.setVisible(false);
		        confirmPasswordErrorLabel.setVisible(false);
    	    }
    	    errorResultLabel.setText(result);
    	    dialogStage.show();
        }
        
		return this;
	}

	@FXML
	public final Object loadSignIn() {
	    ViewNavigator.loadScene("Adventure Game: Sign In", ViewNavigator.SIGN_IN_SCENE);
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