package game.gui;

import game.engine.Battle;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;
import game.engine.titans.Titan;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.Weapon;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Game {
    public static Map<Titan, Pair<Circle, Double>> titanCircleMap = new HashMap<>();
    public static int titanSpawnDistance = 75;
    public  static BorderPane root;
    public static int lastGambleTurn=0;
    public static CheckBox AIButton;
    public static Timeline AItimeLine;
    public static void shinzo() {
        MediaPlayer m = new MediaPlayer(new Media(new File("src/shinzo.mp3").toURI().toString()));
        m.play();
        // set timeline until audio ends
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (m.getCurrentTime().toSeconds() == m.getTotalDuration().toSeconds()) {
                m.stop();
                /// this wont play but just to make shinszou works
                MediaPlayer m2 = new MediaPlayer(new Media(new File("src/battlesong.mp3").toURI().toString()));
                m2.play();
                m2.stop();
            }
        }));
    }


    public static Tooltip createLaneTooltip(Battle battle, int i) {
        return new Tooltip("Lane " + (i + 1) +
                "\nDanger Level: " + battle.getOriginalLanes().get(i).getDangerLevel() +
                "\nPure Titans: " + battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == PureTitan.TITAN_CODE).count() +
                "\nAbnormal Titans: " + battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == AbnormalTitan.TITAN_CODE).count() +
                "\nArmored Titans: " + battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == ArmoredTitan.TITAN_CODE).count() +
                "\nColossal Titans: " + battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == ColossalTitan.TITAN_CODE).count() +
                "\nHealth: " + battle.getOriginalLanes().get(i).getLaneWall().getCurrentHealth() + "/" + battle.getOriginalLanes().get(i).getLaneWall().getBaseHealth() +
                "\nPiercing Cannon: x" + battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("PiercingCannon")).count() +
                "\nSniper Cannon: x" + battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("SniperCannon")).count() +
                "\nVolley Spread Cannon: x" + battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("VolleySpreadCannon")).count() +
                "\nWall Trap: x" + battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("WallTrap")).count());
    }

    public static void displayInsufficientResourcesMessage(StackPane root, String messageText) {
        Label message = new Label(messageText);
        message.setTextFill(Color.RED);
        message.setFont(new Font("Arial Black", 30)); // Set the font size to 30

        root.setAlignment(Pos.CENTER); // Set the alignment to center
        root.getChildren().add(message); // Add the message to the root

        FadeTransition ft = new FadeTransition(Duration.seconds(1), message);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), message);
        tt.setFromY(0);
        tt.setToY(-50); // Adjust this value as needed

        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.play();

        pt.setOnFinished(ev -> root.getChildren().remove(message));  // Remove the message after the fade out
    }

    public static void displayInvalidLaneMessage(StackPane root, String messageText) {
        Label message = new Label(messageText);
        message.setTextFill(Color.BLUEVIOLET);
        message.setFont(new Font("Arial Black", 30)); // Set the font size to 30

        root.setAlignment(Pos.CENTER); // Set the alignment to center
        root.getChildren().add(message); // Add the message to the root

        FadeTransition ft = new FadeTransition(Duration.seconds(1), message);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);

        TranslateTransition tt = new TranslateTransition(Duration.seconds(1), message);
        tt.setFromY(0);
        tt.setToY(-50); // Adjust this value as needed

        ParallelTransition pt = new ParallelTransition(ft, tt);
        pt.play();

        pt.setOnFinished(ev -> root.getChildren().remove(message));  // Remove the message after the fade out
    }

    public static void addFadeOutEffect(Label label, StackPane root) {
        new MediaPlayer(new Media(new File("src/Buying.mp3").toURI().toString())).play();
        label.setTextFill(Color.GREEN); // Set the text color to green
        label.setFont(new Font("Arial", 50)); // Set the font size to 50

        // Create a FadeTransition for the label
        FadeTransition ft = new FadeTransition(Duration.seconds(1), label);
        ft.setFromValue(0.0); // Start from transparent
        ft.setToValue(1.0); // End at fully opaque
        ft.setAutoReverse(true); // Reverse the effect afterwards

        // Create a ScaleTransition for the label
        ScaleTransition st = new ScaleTransition(Duration.seconds(.5), label);
        st.setFromX(0.5);
        st.setFromY(0.5);
        st.setToX(1.0); // Double the width
        st.setToY(1.0); // Halve the height
        st.setAutoReverse(true); // Reverse the effect afterwards

        // Create a ParallelTransition to play the FadeTransition and ScaleTransition at the same time
        ParallelTransition pt = new ParallelTransition(ft, st);
        pt.play();

        pt.setOnFinished(ev -> root.getChildren().remove(label));  // Remove the label after the transitions are finished

        root.getChildren().add(label); // Add the label to the root
    }

    public static void deployGame(Stage stage, String difficulty) {
        shinzo();
        Battle battle;

        try {
            // Create an instance of the Battle class
            int numberOfTurns = 1;
            int score = 0;
            int initialNumOfLanes;
            int initialResourcesPerLane;
            if (difficulty.equalsIgnoreCase("EASY")) {
                initialNumOfLanes = 3;
                initialResourcesPerLane = 250;
            } else {
                initialNumOfLanes = 5;
                initialResourcesPerLane = 125;
            }
            battle = new Battle(numberOfTurns, score, titanSpawnDistance, initialNumOfLanes, initialResourcesPerLane);
             root = new BorderPane();
            StackPane stackPane = new StackPane(root);
            BackgroundImage myBI;
            if (difficulty.equalsIgnoreCase("EASY")) {
                myBI = new BackgroundImage(new Image("EasyWalls.jpg", stage.getWidth(), stage.getHeight(), false, true),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
            }
            else {
                myBI = new BackgroundImage(new Image("HardWalls.jpg", stage.getWidth(), stage.getHeight(), false, true),
                        BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
            }
            root.setBackground(new Background(myBI));
            // Create labels
            Label scoreLabel = new Label("Score: " + battle.getScore());
            Label turnLabel = new Label("Turn: " + battle.getNumberOfTurns());
            Label phaseLabel = new Label("Phase: " + battle.getBattlePhase());
            Label resourcesLabel = new Label("Resources: " + battle.getResourcesGathered());
            Label lanesLabel = new Label("Lanes: " + battle.getLanes().size());
            Label weaponShopLabel = new Label("Weapon Shop: ");

            // Add labels to the scene
            HBox statusBox = new HBox(scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, weaponShopLabel);
            // Add the CSS file to the scene's stylesheets
            statusBox.getStylesheets().add(Objects.requireNonNull(Game.class.getResource("/styles2.css")).toExternalForm());
            //set spaces
            statusBox.setSpacing(10);
            //add css style

            root.setTop(statusBox);

            HBox weapons = new HBox();

            Button piercingCannon = new Button("Piercing Cannon");
            Tooltip piercingCannonTooltip = new Tooltip("Weapon Type: Piercing Cannon\n" +
                    "Price: "+battle.getWeaponFactory().getWeaponShop().get(1).getPrice()+"\n" +
                    "Damage: "+battle.getWeaponFactory().getWeaponShop().get(1).getDamage()+"\n" +
                    "Weapon Name: Anti-Titan Shell\n" +
                    "Min Range: ∞\n" +
                    "Max Range: ∞");
            Tooltip.install(piercingCannon, piercingCannonTooltip);

            Button sniperCannon = new Button("Sniper Cannon");
            Tooltip sniperCannonTooltip = new Tooltip("Weapon Type: Sniper Cannon\n" +
                    "Price: "+battle.getWeaponFactory().getWeaponShop().get(2).getPrice()+"\n" +
                    "Damage: "+battle.getWeaponFactory().getWeaponShop().get(2).getDamage()+"\n" +
                    "Weapon Name: Long Range Spear\n" +
                    "Min Range: ∞\n" +
                    "Max Range: ∞");
            Tooltip.install(sniperCannon, sniperCannonTooltip);

            Button volleySpreadCannon = new Button("Volley Spread Cannon");
            Tooltip volleySpreadCannonTooltip = new Tooltip("Weapon Type: Volley Spread Cannon\n" +
                    "Price: "+battle.getWeaponFactory().getWeaponShop().get(3).getPrice()+"\n" +
                    "Damage: "+battle.getWeaponFactory().getWeaponShop().get(3).getDamage()+"\n" +
                    "Weapon Name: Wall Spread Cannon\n" +
                    "Min Range: "+battle.getWeaponFactory().getWeaponShop().get(3).getMinRange()+"\n" +
                    "Max Range: "+battle.getWeaponFactory().getWeaponShop().get(3).getMaxRange());
            Tooltip.install(volleySpreadCannon, volleySpreadCannonTooltip);

            Button wallTrap = new Button("Wall Trap");
            Tooltip wallTrapTooltip = new Tooltip("Weapon Type: Wall Trap\n" +
                    "Price: "+battle.getWeaponFactory().getWeaponShop().get(4).getPrice()+"\n" +
                    "Damage: "+battle.getWeaponFactory().getWeaponShop().get(4).getDamage()+"\n" +
                    "Weapon Name: Proximity Trap\n" +
                    "Min Range: 0\n" +
                    "Max Range: 0");
            Tooltip.install(wallTrap, wallTrapTooltip);

            weapons.getChildren().addAll(piercingCannon, sniperCannon, volleySpreadCannon, wallTrap);
            statusBox.getChildren().add(weapons);


            VBox lanes = new VBox();
            lanes.setAlignment(Pos.BOTTOM_LEFT);  // Start lanes from the bottom
            lanes.setPadding(new Insets(0, 0, 0, 0));  // Remove padding
            lanes.setSpacing(0);  // Set spacing to 0

            GridPane gridPane=new GridPane();
            gridPane.setPadding(new Insets(30, 10, 10, 10));
            gridPane.setVgap(100);
            gridPane.setHgap(10);
            for(int i=0;i<battle.getOriginalLanes().size();i++){
                //add Panes to the gridPane
                Pane pane=new Pane();
                pane.setPrefHeight(stage.getHeight()/battle.getOriginalLanes().size());
                gridPane.add(pane,0,i);
            }
            root.setCenter(gridPane);
            for (int i = 0; i < initialNumOfLanes; i++) {
                Button laneButton = new Button("L" + (i + 1));
                laneButton.setAlignment(Pos.CENTER_LEFT);
                laneButton.setPrefWidth(230);
                laneButton.setPrefHeight((stage.getHeight()) / (initialNumOfLanes));
                laneButton.setStyle("-fx-background-color: transparent; -fx-border-color: rgba(0,0,0,0.15);");
                Tooltip laneTooltip = createLaneTooltip(battle, i);
                Tooltip.install(laneButton, laneTooltip);

                int finalI = i;
                laneButton.setOnAction(event -> {
                    laneCurrentstatues(battle, finalI,root,stage,finalI);
                        });

                lanes.getChildren().add(laneButton);
            }
            //add css style
            lanes.getStylesheets().add(Objects.requireNonNull(Game.class.getResource("/styles2.css")).toExternalForm());
            lanes.setAlignment(Pos.BOTTOM_LEFT);
            lanes.setSpacing(30);
            root.setLeft(lanes);

            AtomicBoolean weaponPurchased = new AtomicBoolean(false);

            AIButton = new CheckBox("AI");
            AIButton.setFont(new Font("Arial", 20));
            AIButton.setPrefSize(50, 20);

// Create a new timeline that calls the AI's action method every second
             AItimeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                // Your AI's action code here...
            }));
            AItimeLine.setCycleCount(Animation.INDEFINITE); // The AItimeLine will loop indefinitely

            AIButton.setOnAction(event -> {
                if (AIButton.isSelected()) {
                    AItimeLine.play();
                } else {
                    AItimeLine.stop();
                }
            });

            AItimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
                if(battle.isGameOver()){
                    AItimeLine.stop();
                }
                // Your AI's action code here...
                weaponPurchased.set(false); // Reset the flag at the start of each action
                // If it's the first turn, buy a sniper weapon on a random lane
                if (battle.getNumberOfTurns() == 0) {
                    int randomLaneIndex = new Random().nextInt(battle.getOriginalLanes().size());
                    Lane randomLane = battle.getOriginalLanes().get(randomLaneIndex);
                    try {
                        battle.purchaseWeapon2(2, randomLane);
                        weaponPurchased.set(true);
                        updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, randomLane, 2, lanes);
                        updateAllLanes(gridPane, battle, stage, root);
                    } catch (InvalidLaneException | InsufficientResourcesException ex) {
                        // Handle exceptions
                    }
                } else {
                    // Loop through each lane
                    for (Lane lane : battle.getOriginalLanes()) {
                        if (weaponPurchased.get()) {
                            break;
                        }
                        // Check if there's a titan that has reached or is about to reach the wall
                        boolean titanNearWall = lane.getTitans().stream().anyMatch(titan -> titan.getDistance() < titanSpawnDistance/3+5);
                        if (titanNearWall) {
                            // If resources >= 75, buy weapon 4, else if resources >= 25, buy weapon 2
                            try {
                                if (!battle.isGameOver() && battle.getResourcesGathered() >= battle.getWeaponFactory().getWeaponShop().get(4).getPrice()) {
                                    battle.purchaseWeapon2(4, lane);
                                    weaponPurchased.set(true);
                                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, lane, 4, lanes);
                                } else if (!battle.isGameOver() && battle.getResourcesGathered() >=  battle.getWeaponFactory().getWeaponShop().get(2).getPrice()) {
                                    battle.purchaseWeapon2(2, lane);
                                    weaponPurchased.set(true);
                                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, lane, 2, lanes);
                                }
                                updateAllLanes(gridPane, battle, stage, root);
                            } catch (InvalidLaneException | InsufficientResourcesException ex) {
                                // Handle exceptions
                            }
                        }

                        else {
                            // If there are no titans near the wall, find the lane with the highest danger level
                            Lane mostDangerousLane = null;
                            for (Lane l : battle.getLanes()) {
                                if (l.isLaneLost()) {
                                    continue;
                                }
                                mostDangerousLane = l;
                            }
                            // If resources >= 25, buy a sniper weapon on the most dangerous lane
                            if (mostDangerousLane != null && battle.getResourcesGathered() >=  battle.getWeaponFactory().getWeaponShop().get(4).getPrice() &&battle.getNumberOfTurns()>45&&!battle.isGameOver()) {

                                try {
                                    battle.purchaseWeapon2(4, mostDangerousLane);
                                    weaponPurchased.set(true);
                                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, mostDangerousLane, 2, lanes);
                                    updateAllLanes(gridPane, battle, stage, root);
                                } catch (InvalidLaneException | InsufficientResourcesException ex) {
                                    // Handle exceptions
                                }
                            }
                            else if (mostDangerousLane != null && battle.getResourcesGathered() >= 3*battle.getWeaponFactory().getWeaponShop().get(4).getPrice() &&battle.getNumberOfTurns()>20&&!battle.isGameOver()) {

                                try {
                                    battle.purchaseWeapon2(4, mostDangerousLane);
                                    weaponPurchased.set(true);
                                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, mostDangerousLane, 2, lanes);
                                    updateAllLanes(gridPane, battle, stage, root);
                                } catch (InvalidLaneException | InsufficientResourcesException ex) {
                                    // Handle exceptions
                                }
                            }
                            else if (mostDangerousLane != null && battle.getResourcesGathered() >=  battle.getWeaponFactory().getWeaponShop().get(2).getPrice()&&!battle.isGameOver()) {

                                try {
                                    battle.purchaseWeapon2(2, mostDangerousLane);
                                    weaponPurchased.set(true);
                                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, mostDangerousLane, 2, lanes);
                                    updateAllLanes(gridPane, battle, stage, root);
                                } catch (InvalidLaneException | InsufficientResourcesException ex) {
                                    // Handle exceptions
                                }
                            }
                        }
                    }
                }
                // If no weapon was purchased, pass the turn
                if (!weaponPurchased.get()) {
                    battle.performTurn2();
                    updateAllLanes(gridPane, battle, stage, root);
                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, null, -1, lanes);
                }
            }));
            statusBox.getChildren().add(AIButton);
            // Create the pass turn button
            Button passTurnButton = new Button("Pass Turn");
            passTurnButton.setOnAction(event -> {
                try {
                    AIButton.setSelected(false);
                    AItimeLine.stop();
                    battle.performTurn2();
                    updateAllLanes(gridPane, battle,stage, root);
                    updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, null, -1, lanes);
                }
                catch (NullPointerException e){}
            });
            statusBox.getChildren().add(passTurnButton);
            Button returnButton = new Button("Return");
            returnButton.setOnAction(event -> {
                AIButton.setSelected(false);
                AItimeLine.stop();
                Main m=new Main();
                m.start(stage);
            });
            statusBox.getChildren().add(returnButton);


            piercingCannon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                //set to the right a pane holding Data
                // Create a ComboBox with lanes labels from 1 to initialNumOfLanes
                ComboBox<String> lanes1 = new ComboBox<>();
                lanes1.setPromptText("Choose Lane");
                for (int i = 0; i < initialNumOfLanes; i++) {
                    String laneLabel = "Lane " + (i + 1);
                    lanes1.getItems().add(laneLabel);
                }

                // Replace the weapon button with the ComboBox at the same index
                weapons.getChildren().set(0, lanes1);

                // Set the action to be performed when an item is selected
                lanes1.setOnAction(e -> {
                    String selectedLane = lanes1.getSelectionModel().getSelectedItem();
                    int laneNumber = Integer.parseInt(selectedLane.split(" ")[1]) - 1;  // Get the lane number from the selected item

                    // Purchase the weapon
                    try {
                        battle.purchaseWeapon2(1, battle.getOriginalLanes().get(laneNumber));
                        updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, battle.getOriginalLanes().get(laneNumber), 1, lanes);
                        updateAllLanes(gridPane, battle, stage, root);

                    } catch (InvalidLaneException ex) {
                        displayInvalidLaneMessage(stackPane, "This lane is no longer available");
                    } catch (InsufficientResourcesException ex) {
                        displayInsufficientResourcesMessage(stackPane, "You have not enough resources");
                    }
                    // Remove the ComboBox and show the weapon button
                    weapons.getChildren().set(0, piercingCannon);
                });

                // Add an event filter to the root node
                root.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
                    // Check if the event target is not within the HBox
                    if (!weapons.getChildren().contains(event1.getTarget())) {
                        // Set the button back
                        weapons.getChildren().set(0, piercingCannon);
                    }
                });
            });

            sniperCannon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // Create a ComboBox with lanes labels from 1 to initialNumOfLanes
                ComboBox<String> lanes2 = new ComboBox<>();
                lanes2.setPromptText("Choose Lane");
                for (int i = 0; i < initialNumOfLanes; i++) {
                    String laneLabel = "Lane " + (i + 1);
                    lanes2.getItems().add(laneLabel);
                }

                // Replace the weapon button with the ComboBox at the same index
                weapons.getChildren().set(1, lanes2);

                // Set the action to be performed when an item is selected
                lanes2.setOnAction(e -> {
                    String selectedLane = lanes2.getSelectionModel().getSelectedItem();
                    int laneNumber = Integer.parseInt(selectedLane.split(" ")[1]) - 1;  // Get the lane number from the selected item

                    // Purchase the weapon
                    try {
                        battle.purchaseWeapon2(2, battle.getOriginalLanes().get(laneNumber));
                        updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, battle.getOriginalLanes().get(laneNumber), 2, lanes);
                        updateAllLanes(gridPane, battle, stage, root);

                    } catch (InvalidLaneException ex) {
                        displayInvalidLaneMessage(stackPane, "This lane is no longer available");
                    } catch (InsufficientResourcesException ex) {
                        displayInsufficientResourcesMessage(stackPane, "You have not enough resources");
                    }
                    // Remove the ComboBox and show the weapon button
                    weapons.getChildren().set(1, sniperCannon);
                });

                // Add an event filter to the root node
                root.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
                    // Check if the event target is not within the HBox
                    if (!weapons.getChildren().contains(event1.getTarget())) {
                        // Set the button back
                        weapons.getChildren().set(1, sniperCannon);
                    }
                });
            });

            volleySpreadCannon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // Create a ComboBox with lanes labels from 1 to initialNumOfLanes
                ComboBox<String> lanes3 = new ComboBox<>();
                lanes3.setPromptText("Choose Lane");
                for (int i = 0; i < initialNumOfLanes; i++) {
                    String laneLabel = "Lane " + (i + 1);
                    lanes3.getItems().add(laneLabel);
                }

                // Replace the weapon button with the ComboBox at the same index
                weapons.getChildren().set(2, lanes3);

                // Set the action to be performed when an item is selected
                lanes3.setOnAction(e -> {
                    String selectedLane = lanes3.getSelectionModel().getSelectedItem();
                    int laneNumber = Integer.parseInt(selectedLane.split(" ")[1]) - 1;  // Get the lane number from the selected item

                    // Purchase the weapon
                    try {
                        battle.purchaseWeapon(3, battle.getOriginalLanes().get(laneNumber));
                        updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, battle.getOriginalLanes().get(laneNumber), 3, lanes);
                        updateAllLanes(gridPane, battle, stage, root);

                    } catch (InvalidLaneException ex) {
                        displayInvalidLaneMessage(stackPane, "This lane is no longer available");
                    } catch (InsufficientResourcesException ex) {
                        displayInsufficientResourcesMessage(stackPane, "You have not enough resources");
                    }
                    // Remove the ComboBox and show the weapon button
                    weapons.getChildren().set(2, volleySpreadCannon);
                });

                // Add an event filter to the root node
                root.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
                    // Check if the event target is not within the HBox
                    if (!weapons.getChildren().contains(event1.getTarget())) {
                        // Set the button back
                        weapons.getChildren().set(2, volleySpreadCannon);
                    }
                });
            });

            wallTrap.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                // Create a ComboBox with lanes labels from 1 to initialNumOfLanes
                ComboBox<String> lanes4 = new ComboBox<>();
                lanes4.setPromptText("Choose Lane");
                for (int i = 0; i < initialNumOfLanes; i++) {
                    String laneLabel = "Lane " + (i + 1);
                    lanes4.getItems().add(laneLabel);
                }

                // Replace the weapon button with the ComboBox at the same index
                weapons.getChildren().set(3, lanes4);

                // Set the action to be performed when an item is selected
                lanes4.setOnAction(e -> {
                    String selectedLane = lanes4.getSelectionModel().getSelectedItem();
                    int laneNumber = Integer.parseInt(selectedLane.split(" ")[1]) - 1;  // Get the lane number from the selected item

                    // Purchase the weapon
                    try {
                        battle.purchaseWeapon2(4, battle.getOriginalLanes().get(laneNumber));
                        updateStatus(stackPane, battle, scoreLabel, turnLabel, phaseLabel, resourcesLabel, lanesLabel, battle.getOriginalLanes().get(laneNumber), 4, lanes);
                        updateAllLanes(gridPane, battle, stage, root);

                    } catch (InvalidLaneException ex) {
                        displayInvalidLaneMessage(stackPane, "This lane is no longer available");
                    } catch (InsufficientResourcesException ex) {
                        displayInsufficientResourcesMessage(stackPane, "You have not enough resources");
                    }
                    // Remove the ComboBox and show the weapon button
                    weapons.getChildren().set(3, wallTrap);
                });

                // Add an event filter to the root node
                root.addEventFilter(MouseEvent.MOUSE_CLICKED, event1 -> {
                    // Check if the event target is not within the HBox
                    if (!weapons.getChildren().contains(event1.getTarget())) {
                        // Set the button back
                        weapons.getChildren().set(3, wallTrap);
                    }
                });
            });

            // Create the scene
            BorderPane b=new BorderPane();
            Label l=new Label("Press on A\nLane  |  Titan  \nOr Hover On Weapons\nTo See Data");
            l.setFont(new Font("Arial", 40));
            l.setAlignment(Pos.CENTER);
            b.setCenter (l);
            root.setRight(b);


            Scene scene = new Scene(stackPane, stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
            stage.show();
        } catch (IOException ignored) {
        }
    }

    public static void updateStatus(StackPane root, Battle battle, Label scoreLabel, Label turnLabel, Label phaseLabel, Label resourcesLabel, Label lanesLabel, Lane lane, int weaponCode, VBox lanes) {
        for (Lane l : battle.getOriginalLanes()) {
            Button laneButton = (Button) lanes.getChildren().get(battle.getOriginalLanes().indexOf(l));
            laneButton.setGraphic(null);
            if (l.getWeapons().size() > 0) {
                GridPane gridPane = new GridPane();
                gridPane.setCursor(Cursor.HAND);

                gridPane.setPrefHeight(70); // Adjust the value as needed
                gridPane.setPrefWidth(70); // Adjust the value as needed

                // Create a HashSet to store the names of the weapons that have been added to the lane
                Set<String> addedWeapons = new HashSet<>();

                for (Weapon weapon : l.getWeapons()) {
                    // Check if the weapon has already been added to the lane
                    if (!addedWeapons.contains(weapon.getClass().getSimpleName())) {
                        ImageView image = new ImageView();
                        image.setFitHeight(70);
                        image.setFitWidth(70);

                        if (weapon.getClass().getSimpleName().equals("PiercingCannon")) {
                            image.setImage(new Image("/peirc.png"));
                        } else if (weapon.getClass().getSimpleName().equals("SniperCannon")) {
                            image.setImage(new Image("/sonipor2.png"));
                        } else if (weapon.getClass().getSimpleName().equals("VolleySpreadCannon")) {
                            image.setImage(new Image("/volly.png"));
                        } else if (weapon.getClass().getSimpleName().equals("WallTrap")) {
                        image.setImage(new Image("/SniperCannonTip.jpg"));
                    }

                        int columns = battle.getOriginalLanes().size() == 5 ? 2 : 4;
                        gridPane.add(image, gridPane.getChildren().size() / columns, gridPane.getChildren().size() % columns);
                        if (gridPane.getChildren().size() / columns == columns) {
                            break;
                        }

                        // Add the weapon to the HashSet
                        addedWeapons.add(weapon.getClass().getSimpleName());
                    }
                }

                laneButton.setGraphic(gridPane);
                laneButton.setOpacity(1);
            }
        }
        //update the lane ToolTip for all lanes
        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            Tooltip laneTooltip = createLaneTooltip(battle, i);
            Tooltip.install(lanes.getChildren().get(i), laneTooltip);
            // showAndUpdateCurrentTitansPositionsfromLane(battle, lanes, i);
        }
        scoreLabel.setText("Score: " + battle.getScore());
        phaseLabel.setText("Phase: " + battle.getBattlePhase());
        resourcesLabel.setText("Resources: " + battle.getResourcesGathered());
        lanesLabel.setText("Lanes: " + battle.getLanes().size());
        if(battle.isGameOver()){
            return;
        }
        turnLabel.setText("Turn: " + battle.getNumberOfTurns());
        if(weaponCode==-1){
            return;
        }
        Label laneLabel = new Label("Lane " + (battle.getOriginalLanes().indexOf(lane) + 1) + " Weapon: " + lane.getWeapons().get(lane.getWeapons().size() - 1).getClass().getSimpleName());
        addFadeOutEffect(laneLabel, root);
    }
    public static Circle titanCircle(Titan titan) {
        //switch based on titan type for radius and color
        switch (titan.getDangerLevel()) {
            case AbnormalTitan.TITAN_CODE:
                return new Circle(15, Color.YELLOW);
            case ArmoredTitan.TITAN_CODE:
                return new Circle(20, Color.ORANGE);
            case ColossalTitan.TITAN_CODE:
                return new Circle(25, Color.RED);
            default:
                return new Circle(10, Color.GREEN);
        }
    }

    public static void updateAllLanes(GridPane gridPane, Battle battle, Stage stage, BorderPane root) {
        if(battle.isGameOver()){
            new MediaPlayer(new Media(new File("src/GameOver.mp3").toURI().toString())).play();
        }
        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
            if(battle.getOriginalLanes().get(i).isLaneLost()){
                continue;
            }
            Pane pane = (Pane) gridPane.getChildren().get(i);

            for (Titan titan : battle.getOriginalLanes().get(i).getTitans()) {
                Pair<Circle, Double> pair = titanCircleMap.get(titan);
                Circle circle;
                double yPosition;
                if (pair == null) {
                    // This is a new titan
                    circle = titanCircle(titan);
                    yPosition = Math.random() * (pane.getHeight());  // Set a random Y position within the height of the pane
                    titanCircleMap.put(titan, new Pair<>(circle, yPosition));
                    circle.setCenterX(titan.getDistance()*15);
                    circle.setCenterY(yPosition);

                    addTitanInfoOnClick(circle, titan, Game.root);

                    AddToolTipToTitan(titan, circle, battle, stage);

                    pane.getChildren().add(circle);
                } else {

                    AddToolTipToTitan(titan, pair.getKey(), battle, stage);

                    circle = pair.getKey();
                    yPosition = pair.getValue();

                    addTitanInfoOnClick(circle, titan, Game.root);
                    //check if titan has reached target and circle is at the left most of pane
                    if(!titan.hasReachedTarget()){
                        // Create a TranslateTransition for the circle
                        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), circle);
                        tt.setFromX( 15 * (titan.getDistance() + titan.getSpeed() - (titan.getDangerLevel()==4?1:0) - titanSpawnDistance));
                        tt.setToX( 15 * (titan.getDistance() - titanSpawnDistance));
                        tt.play();
                    }
                    else if(titan.FirstTime){
                        //stick the titan to left most of pane in a transition
                        TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), circle);
                        tt.setFromX( 15 * (distancer(titan)  - titanSpawnDistance));
                        tt.setToX( 15 * ( - titanSpawnDistance));
                        tt.play();
                        titan.FirstTime=false;
                    }
                    circle.setCenterY(yPosition);  // Use the initial Y position
                }
            }

            // Remove defeated titans
            pane.getChildren().removeIf(node -> {
                if (node instanceof Circle) {
                    Circle circle = (Circle) node;
                    // Find the titan corresponding to this circle
                    Optional<Titan> optionalTitan = titanCircleMap.entrySet().stream()
                            .filter(entry -> entry.getValue().getKey().equals(circle))
                            .map(Map.Entry::getKey)
                            .findFirst();
                    // If the titan is present and is defeated, remove the circle
                    return optionalTitan.isPresent() && optionalTitan.get().isDefeated();
                }
                return false;
            });
        }
    }

    public static void addTitanInfoOnClick(Circle circle, Titan titan, BorderPane root) {
        circle.setOnMouseClicked(event -> {
            randomDataProcessEffect();
            // Create a new BorderPane
            BorderPane bp = new BorderPane();

            // Create a ProgressBar for the Titan's health
            ProgressBar healthBar = new ProgressBar((double) titan.getCurrentHealth() / titan.getBaseHealth());
            healthBar.setPrefWidth(200);

            // Create a BarChart for the Titan's attributes
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
            dataSeries.getData().add(new XYChart.Data<>("Damage", titan.getDamage()));
            dataSeries.getData().add(new XYChart.Data<>("Danger Level", titan.getDangerLevel()));
            dataSeries.getData().add(new XYChart.Data<>("Height", titan.getHeightInMeters()));
            dataSeries.getData().add(new XYChart.Data<>("Distance from Base", titan.getDistance()));
            barChart.getData().add(dataSeries);

            // Create a Label for the Titan's name
            Label nameLabel = new Label("Titan: " + titan.getClass().getSimpleName());
            nameLabel.setFont(new Font("Arial", 30));

            Button closeButton = new Button("Close");
            closeButton.setOnAction(event1 -> {
                root.setRight(null);
            });
            closeButton.getStylesheets().add("/styles2.css");
            // Add the ProgressBar, BarChart, and Label to the BorderPane
            VBox v=new VBox();
            v.getChildren().addAll(nameLabel, healthBar,barChart,closeButton);
            bp.setCenter(v);
            // Add the BorderPane to the right of the root pane
            // Create a "Close" button
            root.setRight(bp);

            // Animate the BarChart
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(barChart.animatedProperty(), true)));
            timeline.play();
        });
    }
    public static void AddToolTipToTitan(Titan titan, Circle circle, Battle battle, Stage stage) {
        Tooltip.install(circle, new Tooltip("Titan Name: " + titan.getClass().getSimpleName() +
                "\nDistanceFromWall: " + titan.getDistance() +
                "\nHP: " + titan.getCurrentHealth() + "/" + titan.getBaseHealth() +
                "\nDamage: " + titan.getDamage() +
                "\nHeight: " + titan.getHeightInMeters() +
                "\nSpeed: " + titan.getSpeed() +
                "\nResource Value: " + titan.getResourcesValue()+
                "\nDanger Level: " + titan.getDangerLevel()));
    }
    public static int distancer(Titan titan){
        int ans = titanSpawnDistance%titan.getSpeed();
        return (ans==0?titan.getSpeed():ans)+(titan.getDangerLevel()==4?2:0);
    }



    public static void laneCurrentstatues(Battle battle, int i, BorderPane root,Stage stage,int laneIndex){
        randomDataProcessEffect();


        //set the date on a pane set to the right of root
        //show the health of the wall as a health bar
        ProgressBar wallHealth = new ProgressBar(1); // Start with full health
        wallHealth.setPrefWidth(200);
        wallHealth.setPrefHeight(10);
        BorderPane wallHealthPane = new BorderPane();
        root.setRight(wallHealthPane);

// Calculate the current health ratio
        double currentHealthRatio = (double) battle.getOriginalLanes().get(i).getLaneWall().getCurrentHealth() / battle.getOriginalLanes().get(i).getLaneWall().getBaseHealth();

// Create a timeline for the animation
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(wallHealth.progressProperty(), 1) // Start value
                ),
                new KeyFrame(
                        Duration.seconds(.5), // Animation duration
                        new KeyValue(wallHealth.progressProperty(), currentHealthRatio) // End value
                )
        );

// Start the animation
        timeline.play();

// Add a change listener to the progress property of the progress bar
        wallHealth.progressProperty().addListener((observable, oldValue, newValue) -> {
            double progress = newValue.doubleValue();
            if (progress >= .9) {
                wallHealth.setStyle("-fx-accent: green;");
            } else if (progress >= 0.6) {
                wallHealth.setStyle("-fx-accent: blue;");
            } else if (progress >= 0.3) {
                wallHealth.setStyle("-fx-accent: orange;");
            } else if (progress > 0.0) {
                wallHealth.setStyle("-fx-accent: red;");
            } else {
                wallHealth.setStyle("-fx-accent: black;");
            }
        });


        // Create the lane label
        Label laneLabel = new Label("Lane: " + (laneIndex + 1));
        laneLabel.setFont(new Font("Arial", 30)); // Set the font size to 30

        // Create the situation label
        Label situationLabel = new Label();
        situationLabel.setFont(new Font("Arial", 20)); // Set the font size to 20

        // Set the situation based on the current health
        int currentHealth = battle.getOriginalLanes().get(i).getLaneWall().getCurrentHealth();
        if (currentHealth >= 9000) {
            situationLabel.setText("Situation: Perfect");
            //set color to green
            situationLabel.setTextFill(Color.GREEN);
        } else if (currentHealth >= 6000) {
            situationLabel.setText("Situation: Good");
            //set color to blue
            situationLabel.setTextFill(Color.BLUE);
        } else if (currentHealth >= 3000) {
            situationLabel.setText("Situation: Bad");
            //set color to orange
            situationLabel.setTextFill(Color.ORANGE);
        } else if (currentHealth > 0) {
            situationLabel.setText("Situation: Falling");
            //set color to red
            situationLabel.setTextFill(Color.RED);
        } else {
            situationLabel.setText("Situation: Destroyed");
            //set color to black
            situationLabel.setTextFill(Color.BLACK);
        }
        // Create a translate transition for the situation label
        TranslateTransition tt = new TranslateTransition(Duration.seconds(.5), situationLabel);
        tt.setFromX(200); // Start from the right
        tt.setToX(0); // End at the left
        tt.play();

        // Create a PieChart for the titans
        PieChart titansChart = new PieChart();
        long pureTitans = battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == PureTitan.TITAN_CODE).count();
        long abnormalTitans = battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == AbnormalTitan.TITAN_CODE).count();
        long armoredTitans = battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == ArmoredTitan.TITAN_CODE).count();
        long colossalTitans = battle.getOriginalLanes().get(i).getTitans().stream().filter(titan -> titan.getDangerLevel() == ColossalTitan.TITAN_CODE).count();

        PieChart.Data slice1 = new PieChart.Data("Pure", pureTitans);
        PieChart.Data slice2 = new PieChart.Data("Abnormal", abnormalTitans);
        PieChart.Data slice3 = new PieChart.Data("Armored", armoredTitans);
        PieChart.Data slice4 = new PieChart.Data("Colossal", colossalTitans);
        titansChart.getData().addAll(slice1, slice2, slice3, slice4);

        titansChart.setPrefSize(220, 220); // Set the size of the PieChart
        // Create a label for the number of titans
        Label titansLabel = new Label("Pure: x" + pureTitans + ", Abnormal: x" + abnormalTitans + "\nArmored: x" + armoredTitans + ", Colossal: x" + colossalTitans);
        titansLabel.setFont(new Font("Arial", 20)); // Set the font size to 20

        //add another PieChart for the weapons
        PieChart weaponsChart = new PieChart();
        long piercingCannons = battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("PiercingCannon")).count();
        long sniperCannons = battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("SniperCannon")).count();
        long volleySpreadCannons = battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("VolleySpreadCannon")).count();
        long wallTraps = battle.getOriginalLanes().get(i).getWeapons().stream().filter(weapon -> weapon.getClass().getSimpleName().equals("WallTrap")).count();

        PieChart.Data slice5 = new PieChart.Data("Piercing Cannon", piercingCannons);
        PieChart.Data slice6 = new PieChart.Data("Sniper Cannon", sniperCannons);
        PieChart.Data slice7 = new PieChart.Data("Volley Spread Cannon", volleySpreadCannons);
        PieChart.Data slice8 = new PieChart.Data("Wall Trap", wallTraps);
        weaponsChart.getData().addAll(slice5, slice6, slice7, slice8);

        weaponsChart.setPrefSize(220, 220); // Set the size of the PieChart
        // Create a label for the number of weapons
        Label weaponsLabel = new Label("Piercing Cannon: x" + piercingCannons + ", Sniper Cannon: x" + sniperCannons + "\nVolley Spread Cannon: x" + volleySpreadCannons + ", Wall Trap: x" + wallTraps);
        weaponsLabel.setFont(new Font("Arial", 20)); // Set the font size to 20

        Label laneDangerLevel= new Label("\nLane Danger Level: "+battle.getOriginalLanes().get(i).getDangerLevel());
        laneDangerLevel.setFont(new Font("Arial", 20)); // Set the font size to 20

        VBox labelsBox;
        if(!battle.getOriginalLanes().get(i).getTitans().isEmpty()) {
            Label closestTitanAndDistanceOfHim = new Label("Closest Titan: " + battle.getOriginalLanes().get(i).getTitans().peek().getClass().getSimpleName()
                    + ", Distance: " + battle.getOriginalLanes().get(i).getTitans().peek().getDistance());
            closestTitanAndDistanceOfHim.setFont(new Font("Arial", 20)); // Set the font size to 20
            if(battle.getOriginalLanes().get(i).getTitans().peek().getDangerLevel()==ColossalTitan.TITAN_CODE || battle.getOriginalLanes().get(i).getTitans().peek().getDistance()<titanSpawnDistance/5) {
                closestTitanAndDistanceOfHim.setTextFill(Color.RED);
            }
            //sum up the damage of current titans at the lane
             labelsBox = new VBox( wallHealth, situationLabel, titansChart, titansLabel, weaponsChart, weaponsLabel, laneDangerLevel, closestTitanAndDistanceOfHim);
        }
        else {
             labelsBox = new VBox( wallHealth, situationLabel, titansChart, titansLabel, weaponsChart, weaponsLabel, laneDangerLevel);
        }
        // Create an "Update" button
        Button updateButton = new Button("Refresh");
        updateButton.setOnAction(event -> {laneCurrentstatues(battle, i, root, stage, laneIndex); randomDataProcessEffect();}); // Set the action to call this method recursively
        //add css file to the button
        updateButton.getStylesheets().add("/styles2.css");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(event -> {
            root.setRight(null);
        });
//add css file to the button
        closeButton.getStylesheets().add("/styles2.css");

// Add the "Close" button to the VBox
        BorderPane.setAlignment(updateButton, Pos.TOP_RIGHT);
        HBox hBox = new HBox(laneLabel, updateButton,closeButton);
        labelsBox.getChildren().add(0,hBox);
        labelsBox.setSpacing(10);
        // Calculate the sum of the damage of all titans in the lane with health > 50 and titan distance is > titans speed
        int sumDamage = battle.getOriginalLanes().get(i).getTitans().stream()
                .filter(titan -> titan.getCurrentHealth() > titan.getCurrentHealth()/2 && titan.getDistance()<=titan.getSpeed()+(titan instanceof ColossalTitan?1:0))
                .mapToInt(Titan::getDamage)
                .sum();

// Create the label for the sum of damage
        Label sumDamageLabel = new Label("Next predicted Damage: 0");
        sumDamageLabel.setFont(new Font("Arial", 20)); // Set the initial font size to 20

// Create a timeline for the animation
        Timeline timeline1 = new Timeline();

// Create key frames for the animation
        for (int j = 0; j <= sumDamage; j++) {
            int finalJ = j;
            timeline1.getKeyFrames().add(new KeyFrame(Duration.millis(j *10), event -> {
                // Update the text of the label
                sumDamageLabel.setText("Next Predicted Damage: " + finalJ);

                // Update the font size and color of the label
                double fontSize = 20 + finalJ * 0.1;
                if (fontSize > 40) { // Change 50 to the maximum font size you want
                    fontSize = 40; // Change 50 to the maximum font size you want
                }
                sumDamageLabel.setFont(new Font("Arial", fontSize)); // Set the font size
                int greenAndBlue = (int) Math.max(0, 255 - finalJ * 0.5); // Ensure the value is not less than 0
                sumDamageLabel.setTextFill(Color.rgb(255, greenAndBlue, greenAndBlue)); // Change the color to red as the number increases
            }));
        }
// Start the animation
        timeline1.play();

        labelsBox.getChildren().add(sumDamageLabel);
        wallHealthPane.setCenter(labelsBox);
    }


    public static void randomDataProcessEffect(){
        new MediaPlayer(new Media(new File("src/proc"+(int)(Math.random()*3+1)+".mp3").toURI().toString())).play();
    }
    public static void stopAI(){
        AIButton.setSelected(false);
        AItimeLine.stop();
    }
   /* public static void addWeaponInfoOnClick(Button button,Battle battle, BorderPane root,int i) {
        button.setOnAction(event -> {
            // Create a new BorderPane
            BorderPane bp = new BorderPane();


            // Create a BarChart for the Weapon's attributes
            CategoryAxis xAxis = new CategoryAxis();
            NumberAxis yAxis = new NumberAxis();
            BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
            XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
            dataSeries.getData().add(new XYChart.Data<>("Damage", battle.getWeaponFactory().getWeaponShop().get(i).getDamage()));
            //add min and max range only if i ==3
            if(i==3){
                dataSeries.getData().add(new XYChart.Data<>("Min Range", battle.getWeaponFactory().getWeaponShop().get(i).getMinRange()));
                dataSeries.getData().add(new XYChart.Data<>("Max Range", battle.getWeaponFactory().getWeaponShop().get(i).getMaxRange()));
            }
            // Add more attributes as needed
            barChart.getData().add(dataSeries);

            // Create a Label for the Weapon's name
            Label nameLabel = new Label("Weapon: " + battle.getWeaponFactory().getWeaponShop().get(i).getClass().getSimpleName());
            nameLabel.setFont(new Font("Arial", 30));

            // Add the ProgressBar, BarChart, and Label to the BorderPane
            VBox v = new VBox();
            v.getChildren().addAll(nameLabel,  barChart);
            bp.setCenter(v);

            // Add the BorderPane to the right of the root pane
            root.setRight(bp);

            // Animate the BarChart
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000), new KeyValue(barChart.animatedProperty(), true)));
            timeline.play();
        });
    }*/
}
