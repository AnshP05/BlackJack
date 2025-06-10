import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WelcomeScreen {
    
    public Scene createScene(Stage primaryStage, Runnable onPlay) {
        Pane root = new Pane();

        Image bgImage = new Image(getClass().getResourceAsStream("/backgroundImages/BlackjackBackground.jpg"));
        BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));

        Button startButton = new Button();
        animateButton(startButton);
        startButton.setPrefSize(258, 103);
        startButton.setLayoutX(77);
        startButton.setLayoutY(345);
        startButton.setOnAction(e -> {
            onPlay.run();
        });


        Button settingsButton = new Button();
        animateButton(settingsButton);
        settingsButton.setPrefSize(258, 103);
        settingsButton.setLayoutX(77);
        settingsButton.setLayoutY(487);
        settingsButton.setOnAction(e -> {
            onPlay.run();
        });

        Button exitButton = new Button();
        animateButton(exitButton);
        exitButton.setPrefSize(258, 103);
        exitButton.setLayoutX(77);
        exitButton.setLayoutY(629);
        exitButton.setOnAction(e -> {
            primaryStage.close();
        });

        Button gameRulesButton = new Button();
        animateButton(gameRulesButton);
        gameRulesButton.setPrefSize(228, 120);
        gameRulesButton.setLayoutX(93);
        gameRulesButton.setLayoutY(795);

        root.getChildren().add(startButton);
        root.getChildren().add(settingsButton);
        root.getChildren().add(exitButton);
        root.getChildren().add(gameRulesButton);
        return new Scene(root, 800, 600);
    }

    private void animateButton(Button button) {
        button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1);" +  // light glow effect
                "-fx-border-color: white;" +
                "-fx-border-width: 2px;" +
                "-fx-background-radius: 8;" +
                "-fx-border-radius: 8;");

            button.setScaleX(1.05);
            button.setScaleY(1.05);
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
            button.setScaleX(1.0);
            button.setScaleY(1.0);
        });
    }

    
}
