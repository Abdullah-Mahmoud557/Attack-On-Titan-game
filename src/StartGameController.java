import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class StartGameController {
    @FXML
    private VBox root;
    @FXML
    private Button playButton;
    @FXML
    private Button howToPlayButton;
    @FXML
    private Button infoButton;
    @FXML
    private Button settingsButton;
    @FXML
    private Button exitButton;

    public Button getPlayButton() {
        return playButton;
    }

    public Button getHowToPlayButton() {
        return howToPlayButton;
    }

    public Button getInfoButton() {
        return infoButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}