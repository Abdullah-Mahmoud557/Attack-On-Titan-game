package game.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static game.gui.Game.deployGame;


public class Main extends Application {
    private PauseTransition hoverPause;
    private ImageView bgImageView;
    private MediaPlayer mediaPlayer;
    private final IntegerProperty currImageIndex = new SimpleIntegerProperty(0);
    private boolean isMute;
    CheckBox muteCheckBox = new CheckBox("Mute");

    private List<String> imagePaths = Arrays.asList(
            "src/pic1.jpg",
            "src/pic2.png",
            "src/pic3.jpg",
            "src/pic4.jpg",
            "src/pic5.jpg",
            "src/pic6.jpg",
            "src/pic7.jpg",
            "src/pic8.jpg",
            "src/pic9.jpg"
    );

    @Override
    public void start(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/logo.jpg")).toString()));
        stage.setTitle("Attack on Titan: Utopia");
        Pane pane = new Pane();
        bgImageView = new ImageView();
        pane.getChildren().add(bgImageView);

        bgImageView.imageProperty().bind(Bindings.createObjectBinding(() -> {
            try {
                return new Image(new File(imagePaths.get(currImageIndex.get())).toURI().toURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
        }, currImageIndex));

        bgImageView.fitWidthProperty().bind(pane.widthProperty());
        bgImageView.fitHeightProperty().bind(pane.heightProperty());

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4),
                event -> currImageIndex.set((currImageIndex.get() + 1) % imagePaths.size())));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        mediaPlayer = new MediaPlayer(new Media(new File("src/aot.mp3").toURI().toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        // Create the text
        Text text = new Text("Press Enter to continue");
        text.setFont(Font.font("Cosmic Sans MS", FontWeight.BOLD, 20));
        text.setFill(Color.WHITE);
        text.layoutYProperty().bind(pane.heightProperty().subtract(text.getLayoutBounds().getHeight())); // Position at the bottom
        text.layoutXProperty().bind(pane.widthProperty().subtract(text.getLayoutBounds().getWidth()).divide(2));

        // Apply a drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(5.0);
        dropShadow.setOffsetY(5.0);
        dropShadow.setColor(Color.color(.9, 0.4, 0.4));
        text.setEffect(dropShadow);

        // Add the text to the pane
        pane.getChildren().add(text);


        Scene scene = new Scene(pane,1800,900);

        // Add an event listener for the Enter key
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                startGame(stage);
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public void click() {
        new MediaPlayer(new Media(new File("src/newClick.mp3").toURI().toString())).play();
    }

    public void startGame(Stage stage) {
        new MediaPlayer(new Media(new File("src/Hover.mp3").toURI().toString())).play();

        // Load the image
        Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/pic10.png")).toString());


        // Create a BackgroundImage object
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        bgImageView.fitWidthProperty().bind(stage.widthProperty());
        bgImageView.fitHeightProperty().bind(stage.heightProperty());
        // Create a VBox as the root node and set its background
        VBox root = new VBox();
        root.setBackground(new Background(background));
        root.setAlignment(Pos.CENTER);
        root.setSpacing(20);

        // Create the buttons
        Button playButton = new Button("Play");
        Button howToPlayButton = new Button("How to Play");
        Button infoButton = new Button("Info");
        Button settingsButton = new Button("Settings");
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> stage.close());
        // Add the buttons to the VBox
        root.getChildren().addAll(playButton, howToPlayButton, infoButton, settingsButton, exitButton);

        // Set the id of the VBox and add the CSS file to its stylesheets
        root.setId("root");
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

        // Get the current scene's size
       // double width = stage.getScene().getWidth();
        //double height = stage.getScene().getHeight();
        // Create a Scene with the VBox as the root node and the same size as the old scene
        Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());

        // Set the scene to the stage
        stage.setScene(scene);

        playButton.setOnAction(event -> {
            new MediaPlayer(new Media(new File("src/tatakae.mp3").toURI().toString())).play();

            // Store the start game scene in a variable
            Scene startGameScene = stage.getScene();

            // Create a new VBox for the new buttons
            VBox newButtonsPane = new VBox();
            newButtonsPane.setAlignment(Pos.CENTER);
            newButtonsPane.setSpacing(20);
            //set background image
            Image backgroundImage1 = new Image(new File("src/pic10.png").toURI().toString());
            BackgroundImage background1 = new BackgroundImage(backgroundImage1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            newButtonsPane.setBackground(new Background(background1));

            // Create the "EASY", "HARD", and "Main Menu" buttons
            Button easyButton = new Button("EASY");
            Button hardButton = new Button("HARD");
            Button mainMenuButton = new Button("Main Menu");

            // Set the action for the "EASY" button
            easyButton.setOnAction(e -> {
                mediaPlayer.stop();
                // Deploy the game with "EASY" difficulty
                deployGame(stage, "EASY");
            });

            // Set the action for the "HARD" button
            hardButton.setOnAction(e -> {
                mediaPlayer.stop();
                // Deploy the game with "HARD" difficulty
                deployGame(stage, "HARD");
            });
            // Set the action for the "Main Menu" button
            mainMenuButton.setOnAction(e -> {
                // Play the click sound
                click();
                // Set the scene to the start game scene
                stage.setScene(startGameScene);
            });
            //set Hover sound
            easyButton.setOnMouseEntered(event1 -> Hover());
            hardButton.setOnMouseEntered(event1 -> Hover());
            mainMenuButton.setOnMouseEntered(event1 -> Hover());

            // add css style to buttons
            newButtonsPane.setId("root");
            newButtonsPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            // Add the buttons to the VBox
            newButtonsPane.getChildren().addAll(easyButton, hardButton, mainMenuButton);

            // Set the VBox as the root of the scene
            Scene gamescene = new Scene(newButtonsPane, stage.getScene().getWidth(), stage.getScene().getHeight());

            // Add the CSS file to the scene's stylesheets
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

            // Set the scene to the stage
            stage.setScene(gamescene);
        });

        infoButton.setOnAction(event -> {click();showInfo(stage);});

        settingsButton.setOnAction(event -> {
            click();
            // Store the current scene
            Scene oldScene = stage.getScene();

            // Create a new StackPane for the settings and close button
            StackPane settingsPane = new StackPane();
            settingsPane.setStyle("-fx-background-color: rgba(0,0,0,0.8);"); // Semi-transparent background

            muteCheckBox.setFont(Font.font("Cosmic Sans MS", FontWeight.BOLD, 20));
            muteCheckBox.setTextFill(Color.DARKGREEN);
            muteCheckBox.setOnAction(event1 -> {
                click();
                isMute = muteCheckBox.isSelected();
                if (isMute) {
                    mediaPlayer.pause();
                } else {
                    mediaPlayer.play();
                }
            });

            // Create the close button
            Button closeButton = new Button("Close Settings");
            //get css style
            settingsPane.setId("root");
            settingsPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
            // Add the close button and mute checkbox to the StackPane
            settingsPane.getChildren().addAll(muteCheckBox, closeButton);

            // Set the alignment of the close button to top right
            StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

            // Create a new scene for the settings pane
            Scene settingsScene = new Scene(settingsPane,stage.getScene().getWidth(), stage.getScene().getHeight());


            // Set the scene to the settings scene
            stage.setScene(settingsScene);
            closeButton.setOnMouseEntered(event1 -> Hover());
            closeButton.setOnAction(event1 -> {
                click();
                // Set the scene back to the old scene when the close button is clicked
                stage.setScene(oldScene);
                stage.show();

                Platform.runLater(() -> {
                    // Add listeners to the stage's width and height properties to resize the scene
                    stage.widthProperty().addListener((observable, oldValue, newValue) -> {
                        if (oldScene.getWindow() != null) {
                            oldScene.getWindow().setWidth(newValue.doubleValue());
                        }
                    });

                    stage.heightProperty().addListener((observable, oldValue, newValue) -> {
                        if (oldScene.getWindow() != null) {
                            oldScene.getWindow().setHeight(newValue.doubleValue());
                        }
                    });
                });
            });
        });


        // Show the stage
        stage.show();

        howToPlayButton.setOnAction(event -> {
            //set clicking sound
            click();

            // Create a new Stage for the instructions
            Stage instructionsStage = new Stage();
            instructionsStage.initStyle(StageStyle.UNDECORATED); // No title bar
            instructionsStage.setTitle("How to Play");

            // Set the logo
            instructionsStage.getIcons().add(new Image(new File("src/logo.jpg").toURI().toString()));

            // Create a new StackPane for the instructions and close button
            StackPane instructionsPane = new StackPane();

            // Create the instructions text
            Text instructionsText = new Text("~INTRO~\n" +
                    "Attack on Titan: Utopia is a one-player, endless 2 , tower defense game 1\n" +
                    "inspired by the hit anime Attack on Titan. The story of the anime\n" +
                    "revolves around how titans, gigantic humanoid creatures, emerged one day\n" +
                    "and wiped out most of humanity. The few surviving humans fled and hid\n" +
                    "behind 3 great walls that provided safe haven from the titan threats. Wall\n" +
                    "Maria is the outer wall, Wall Rose is the middle wall and Wall Sina is the\n" +
                    "inside wall.\n" +
                    "This game takes place in an imaginary scenario where the titans breached\n" +
                    "their way throughout Wall Maria and reached the northern border of Wall\n" +
                    "Rose at the Utopia District. The human forces stationed in Utopia engage\n" +
                    "the titans in battle for one last hope of preventing the titans from\n" +
                    "breaching Wall Rose. The humans fight by deploying different types of\n" +
                    "Anti-Titan weapons in order to stop the Titan’s onslaught and keep Utopia’s\n" +
                    "(and Wall Rose’s) walls safe.\n" +
                    "1 Tower Defense Games: is a type of game where the player controls a\n" +
                    "base and the objective is to continue defending this base from incoming\n" +
                    "enemies by deploying some weapons/tools to get rid of these enemies. In\n" +
                    "our case the base we need to protect is the Utopia District Walls.\n" +
                    "2 Endless: it means that the game will have no winning condition and the\n" +
                    "player will keep playing and defeat as many enemies as possible.\n" +
                    "3\n" +
                    "Space (Battle) Setting\n" +
                    "The battlefield is divided into multiple lanes, each lane will have the\n" +
                    "following:\n" +
                    "1. A part of the wall to be defended. This wall part will have a starting\n" +
                    "HP (health points) that decreases after being attacked and if this part\n" +
                    "of the wall is destroyed, this lane will no longer be considered an\n" +
                    "active lane and will be a lost lane.\n" +
                    "2. The weapons that the player has already deployed into this lane.\n" +
                    "3. The titans that are on their way to attack the part of the wall at the\n" +
                    "end of the lane. The titans can be at different distances from the\n" +
                    "walls depending on how much they have already moved. Each titan\n" +
                    "will have a starting HP (health points) that decreases after being\n" +
                    "attacked.\n" +
                    "Each lane will have a danger level that can be calculated based on the\n" +
                    "number and types of titans inside this lane.\n" +
                    "In the player’s base, the player will have the option to see all the available\n" +
                    "types of weapons and can choose to buy and deploy them into their\n" +
                    "choice of an active lane. The player should be able to view the currently\n" +
                    "gathered resources and acquired score as well as the remaining HPs of\n" +
                    "all walls and titans as well as each titan’s distance from the wall. The\n" +
                    "player can also see the approaching titans in order, these approaching\n" +
                    "titans will be added to the lanes in the upcoming turns.\n" +
                    "The Battle has 3 phases depending on the number of turns that already\n" +
                    "passed: Early, Intense, Grumbling.");
            instructionsText.setFont(Font.font("Cosmic Sans MS", FontWeight.BOLD, 20));
            instructionsText.setFill(Color.DARKGREEN);
            instructionsText.setTextAlignment(TextAlignment.CENTER); // Center align the text

            // Wrap the text in a VBox for vertical alignment
            VBox vbox = new VBox(instructionsText);
            vbox.setAlignment(Pos.CENTER);

            // Wrap the VBox in a ScrollPane
            ScrollPane scrollPane = new ScrollPane(vbox);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true); // Make the ScrollPane fit to height

            // Center align the content in the ScrollPane
            scrollPane.setContent(vbox);
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Hide horizontal scroll bar
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Show vertical scroll bar as needed

            // Create the close button
            Button closeButton = new Button("Close Instructions");
            closeButton.setOnAction(event1 -> {
                click();
                // Close the instructions stage when the close button is clicked
                instructionsStage.close();
            });
            closeButton.setOnMouseEntered(event1 -> Hover());

            // Add the close button and scroll pane to the StackPane
            instructionsPane.getChildren().addAll(scrollPane, closeButton);

            // Set the alignment of the close button to bottom left
            StackPane.setAlignment(closeButton, Pos.BOTTOM_RIGHT);

            // Set the CSS style of the close button
            closeButton.getStyleClass().add("button");

            // Create a new scene for the instructions pane
            Scene instructionsScene = new Scene(instructionsPane, stage.getScene().getWidth(), stage.getScene().getHeight());


            // Add the CSS file to the scene's stylesheets
            instructionsScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

            // Set the scene to the instructions stage
            instructionsStage.setScene(instructionsScene);

            // Show the instructions stage
            instructionsStage.show();
        });
        //set hover sound
        playButton.setOnMouseEntered(event -> Hover());
        howToPlayButton.setOnMouseEntered(event -> Hover());
        infoButton.setOnMouseEntered(event -> Hover());
        settingsButton.setOnMouseEntered(event -> Hover());
        exitButton.setOnMouseEntered(event -> Hover());
    }





    private void showInfo(Stage stage) {
        // Store the current scene
        Scene oldScene = stage.getScene();

        //set Background image
        // Create a new VBox for the Titans and Weapons buttons

        VBox infoPane = new VBox();
        infoPane.setAlignment(Pos.CENTER);
        infoPane.setSpacing(30);

        Image backgroundImage = new Image(new File("src/pic10.png").toURI().toString());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        infoPane.setBackground(new Background(background));
        //set css style to buttons
        infoPane.setId("root");
        infoPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        // Create the Titans and Weapons buttons
        Button titansButton = new Button("Titans");
        titansButton.setOnMouseEntered(event -> Hover());
        titansButton.setOnAction(
                event -> {
                    showTitans(stage, oldScene);
                    click();
                }
        );
        Button weaponsButton = new Button("Weapons");
        weaponsButton.setOnMouseEntered(event -> Hover());
        weaponsButton.setOnAction(event -> {
            showWeapons(stage, oldScene);
            click();
        });


        // Add the Titans and Weapons buttons to the VBox
        infoPane.getChildren().addAll(titansButton, weaponsButton);

        // Create a new scene for the info pane
        Scene infoScene = new Scene(infoPane, stage.getScene().getWidth(), stage.getScene().getHeight());

        // Set the scene to the info scene
        stage.setScene(infoScene);
    }









    private void showTitans(Stage stage, Scene oldScene) {
        // Create a new StackPane for the background image
        StackPane weaponsPane = new StackPane();
        weaponsPane.setAlignment(Pos.CENTER);

        //set Background image
        Image backgroundImage = new Image(new File("src/pic10.png").toURI().toString());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        weaponsPane.setBackground(new Background(background));
        // Create a new HBox for the Weapon buttons
        HBox weaponsBox = new HBox();
        weaponsBox.setAlignment(Pos.CENTER);
        weaponsBox.setSpacing(30);

        // Create the Weapon buttons
        Button pureTitan = new Button("Pure Titan");
        Button abnormalTitan = new Button("Abnormal Titan");
        Button armoredTitan = new Button("Armored Titan");
        Button colossalTitan = new Button("Colossal Titan");

        // Add the Weapon buttons to the HBox
        weaponsBox.getChildren().addAll(pureTitan, abnormalTitan, armoredTitan, colossalTitan);

        // Create a close button
        Button closeButton = new Button("Close");
        closeButton.setOnMouseEntered(event -> Hover());
        closeButton.setOnAction(event -> {click();stage.setScene(oldScene);});

        // Add the close button to the HBox
        weaponsBox.getChildren().add(closeButton);

        // Add the HBox to the StackPane
        weaponsPane.getChildren().add(weaponsBox);

        // Create a new scene for the Weapons pane
        Scene weaponsScene = new Scene(weaponsPane, stage.getScene().getWidth(), stage.getScene().getHeight());

        // Set the scene to the Weapons scene
        stage.setScene(weaponsScene);

// Array of all buttons
        Button[] buttons = {pureTitan, abnormalTitan, armoredTitan, colossalTitan,closeButton};

        for (Button button : buttons) {
            // Set the initial style of the buttons
            button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: white;");

            // Change the style of the button when the mouse hovers over it
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 5px; -fx-text-fill: white; -fx-scale-x: 1.2; -fx-scale-y: 1.2;"));

            // Revert the style of the button when the mouse stops hovering over it
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 3px; -fx-text-fill: white; -fx-scale-x: 1; -fx-scale-y: 1;"));
        }

        // Play the click sound when a button is clicked
        pureTitan.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/Pure.mp4");
        });

        abnormalTitan.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/Abnormal.mp4");
        });

        colossalTitan.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/Collosal.mp4");
        });
        armoredTitan.setOnAction(event -> playVideo(stage, weaponsPane, "src/Aromored2.mp4"));
        // Load the hover background image
        Image hoverBackgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/ArmoredTip.jpg")).toString());
        ImageView hoverBackgroundImageView = new ImageView(hoverBackgroundImage);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageView.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageView.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the VolleySpreadCannon button
        armoredTitan.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageView)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageView);
            }
        });
        // Remove the background image when the cursor exits the VolleySpreadCannon button
        armoredTitan.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageView));

        // Load the hover background image
        Image hoverBackgroundimgPiercing = new Image(Objects.requireNonNull(getClass().getResource("/PureTitan.png")).toString());
        ImageView hoverBackgroundImageViewPiercing = new ImageView(hoverBackgroundimgPiercing);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewPiercing.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewPiercing.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the PiercingCannon button
        pureTitan.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewPiercing)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewPiercing);
            }
        });
        // Remove the background image when the cursor exits the PiercingCannon button
        pureTitan.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewPiercing));

        // Load the hover background image for SniperCannon
        Image hoverBackgroundimgSniper = new Image(Objects.requireNonNull(getClass().getResource("/AbnormalTip.jpg")).toString());
        ImageView hoverBackgroundImageViewSniper = new ImageView(hoverBackgroundimgSniper);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewSniper.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewSniper.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the SniperCannon button
        abnormalTitan.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewSniper)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewSniper);
            }
        });
        // Remove the background image when the cursor exits the SniperCannon button
        abnormalTitan.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewSniper));

        // Load the hover background image for Walltrap
        Image hoverBackgroundimgWalltrap = new Image(Objects.requireNonNull(getClass().getResource("/ColossalTip.jpg")).toString());
        ImageView hoverBackgroundImageViewWalltrap = new ImageView(hoverBackgroundimgWalltrap);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewWalltrap.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewWalltrap.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the Walltrap button
        colossalTitan.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewWalltrap)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewWalltrap);
            }
        });
        // Remove the background image when the cursor exits the Walltrap button
        colossalTitan.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewWalltrap));
    }

    private void showWeapons(Stage stage, Scene oldScene) {
        // Create a new StackPane for the background image
        StackPane weaponsPane = new StackPane();
        weaponsPane.setAlignment(Pos.CENTER);

        //set Background image
        Image backgroundImage = new Image(new File("src/pic10.png").toURI().toString());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        weaponsPane.setBackground(new Background(background));
        // Create a new HBox for the Weapon buttons
        HBox weaponsBox = new HBox();
        weaponsBox.setAlignment(Pos.CENTER);
        weaponsBox.setSpacing(30);

        // Create the Weapon buttons
        Button PiercingCannonButton = new Button("Piercing Cannon");
        Button SniperCannonButton = new Button("Sniper Cannon");
        Button volleySpreadCannonButton = new Button("VolleySpreadCannon");
        Button WalltrapButton = new Button("Wall trap");

        // Add the Weapon buttons to the HBox
        weaponsBox.getChildren().addAll(PiercingCannonButton, SniperCannonButton, volleySpreadCannonButton, WalltrapButton);

        // Create a close button
        Button closeButton = new Button("Close");
        closeButton.setOnMouseEntered(event -> Hover());
        closeButton.setOnAction(event -> {click();stage.setScene(oldScene);});

        // Add the close button to the HBox
        weaponsBox.getChildren().add(closeButton);

        // Add the HBox to the StackPane
        weaponsPane.getChildren().add(weaponsBox);

        // Create a new scene for the Weapons pane
        Scene weaponsScene = new Scene(weaponsPane, stage.getScene().getWidth(), stage.getScene().getHeight());

        // Set the scene to the Weapons scene
        stage.setScene(weaponsScene);

     /*   // Add the CSS file to the scene's stylesheets
        weaponsScene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());*/
// Array of all buttons
        Button[] buttons = {PiercingCannonButton, SniperCannonButton, volleySpreadCannonButton, WalltrapButton,closeButton};

        for (Button button : buttons) {
            // Set the initial style of the buttons
            button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: white;");

            // Change the style of the button when the mouse hovers over it
            button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 4px; -fx-text-fill: white; -fx-scale-x: 1.2; -fx-scale-y: 1.2;"));

            // Revert the style of the button when the mouse stops hovering over it
            button.setOnMouseExited(e -> button.setStyle("-fx-background-color: purple; -fx-border-color: blue; -fx-border-width: 2px; -fx-text-fill: white; -fx-scale-x: 1; -fx-scale-y: 1;"));
        }

        // Play the click sound when a button is clicked
        PiercingCannonButton.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/PiercingCannon.mp4");
        });

        SniperCannonButton.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/Sniper.mp4");
        });

        WalltrapButton.setOnAction(event -> {
            playVideo(stage, weaponsPane, "src/Walltrap.mp4");
        });
        volleySpreadCannonButton.setOnAction(event -> playVideo(stage, weaponsPane, "src/NewVolley.mp4"));
        // Load the hover background image
        Image hoverBackgroundImage = new Image(Objects.requireNonNull(getClass().getResource("/tips1.jpg")).toString());
        ImageView hoverBackgroundImageView = new ImageView(hoverBackgroundImage);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageView.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageView.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the VolleySpreadCannon button
        volleySpreadCannonButton.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageView)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageView);
            }
        });
        // Remove the background image when the cursor exits the VolleySpreadCannon button
        volleySpreadCannonButton.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageView));

        // Load the hover background image
        Image hoverBackgroundimgPiercing = new Image(Objects.requireNonNull(getClass().getResource("/PriciningCannonTip.jpg")).toString());
        ImageView hoverBackgroundImageViewPiercing = new ImageView(hoverBackgroundimgPiercing);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewPiercing.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewPiercing.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the PiercingCannon button
        PiercingCannonButton.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewPiercing)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewPiercing);
            }
        });
        // Remove the background image when the cursor exits the PiercingCannon button
        PiercingCannonButton.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewPiercing));

        // Load the hover background image for SniperCannon
        Image hoverBackgroundimgSniper = new Image(Objects.requireNonNull(getClass().getResource("/SniperCannonTip.jpg")).toString());
        ImageView hoverBackgroundImageViewSniper = new ImageView(hoverBackgroundimgSniper);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewSniper.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewSniper.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the SniperCannon button
        SniperCannonButton.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewSniper)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewSniper);
            }
        });
        // Remove the background image when the cursor exits the SniperCannon button
        SniperCannonButton.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewSniper));

        // Load the hover background image for Walltrap
        Image hoverBackgroundimgWalltrap = new Image(Objects.requireNonNull(getClass().getResource("/WallTrapTip.png")).toString());
        ImageView hoverBackgroundImageViewWalltrap = new ImageView(hoverBackgroundimgWalltrap);
        // Bind the dimensions of the ImageView to the dimensions of the Scene
        hoverBackgroundImageViewWalltrap.fitWidthProperty().bind(weaponsPane.widthProperty());
        hoverBackgroundImageViewWalltrap.fitHeightProperty().bind(weaponsPane.heightProperty());
        // Change the background image when the cursor hovers over the Walltrap button
        WalltrapButton.setOnMouseEntered(event -> {
            Hover();
            if (!weaponsPane.getChildren().contains(hoverBackgroundImageViewWalltrap)) {
                weaponsPane.getChildren().add(0, hoverBackgroundImageViewWalltrap);
            }
        });
        // Remove the background image when the cursor exits the Walltrap button
        WalltrapButton.setOnMouseExited(event -> weaponsPane.getChildren().remove(hoverBackgroundImageViewWalltrap));
    }


    private void playVideo(Stage stage, StackPane weaponsPane, String videoFilePath) {
        click();
        // Pause the background music if it's not muted
        if (!isMute) {
            mediaPlayer.pause();
        }
        // Play the video
        Media video = new Media(new File(videoFilePath).toURI().toString());
        MediaPlayer videoPlayer = new MediaPlayer(video);
        MediaView mediaView = new MediaView(videoPlayer);

        // Bind the dimensions of the MediaView to the dimensions of the StackPane
        mediaView.fitWidthProperty().bind(weaponsPane.widthProperty());
        mediaView.fitHeightProperty().bind(weaponsPane.heightProperty());

        // Create a new StackPane for the video
        StackPane videoPane = new StackPane(mediaView);
        videoPane.setAlignment(Pos.CENTER);

        // Create the "Press q to Quit" text
        Text quitText = new Text("Press q to Quit");
        quitText.setFont(Font.font("Cosmic Sans MS", FontWeight.BOLD, 30));
        quitText.setFill(Color.WHITE);

        // Add the "Press q to Quit" text to the StackPane
        videoPane.getChildren().add(quitText);

        // Set the alignment of the "Press q to Quit" text to bottom center
        StackPane.setAlignment(quitText, Pos.BOTTOM_CENTER);

        // Set the scene's root node to the video pane
        stage.getScene().setRoot(videoPane);

        videoPlayer.play();

        // Add an event listener for the Q key to stop the video and return to the weapons scene
        stage.getScene().setOnKeyPressed(event1 -> {
            if (event1.getCode() == KeyCode.Q) {
                // Stop the video
                videoPlayer.stop();
                // Remove the MediaView and "Press q to Quit" text from the StackPane
                videoPane.getChildren().removeAll(mediaView, quitText);
                // Set the scene's root node back to the original pane
                stage.getScene().setRoot(weaponsPane);
                // Resume the background music if it's not muted
                if (!isMute) {
                    mediaPlayer.play();
                }
            }
        });

        // Add an event listener to stop the video and return to the weapons scene when the video ends
        videoPlayer.setOnEndOfMedia(() -> {
            // Stop the video
            videoPlayer.stop();
            // Remove the MediaView and "Press q to Quit" text from the StackPane
            videoPane.getChildren().removeAll(mediaView, quitText);
            // Set the scene's root node back to the original pane
            stage.getScene().setRoot(weaponsPane);
            // Resume the background music if it's not muted
            if (!isMute) {
                mediaPlayer.play();
            }
        });
    }

    /*public void setCursor(Scene scene) {
        // Load the cursor image
        Image cursorImage = new Image(Objects.requireNonNull(getClass().getResource("/cursor.png")).toString());
        // Create a new ImageCursor object
        ImageCursor cursor = new ImageCursor(cursorImage);
        // Set the cursor to the scene
        scene.setCursor(cursor);
    }*/

    public void Hover() {
        if (hoverPause != null && hoverPause.getStatus() == Animation.Status.RUNNING) {
            return;
        }

        hoverPause = new PauseTransition(Duration.millis(200)); // 200 milliseconds delay
        hoverPause.setOnFinished(event -> {
            // Load the hover sound
            MediaPlayer hoverSound = new MediaPlayer(new Media(new File("src/hover.mp3").toURI().toString()));
            // Play the hover sound when the cursor hovers over a button
            hoverSound.play();
        });
        hoverPause.play();
    }


    public static void main(String[] args) {
        launch();
    }
}