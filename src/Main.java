import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        WelcomeScreen welcome = new WelcomeScreen();
        
        primaryStage.setScene(welcome.createScene(primaryStage, () -> {
            BlackjackGUI game = new BlackjackGUI();
            try {
                game.start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));

        primaryStage.setTitle("Blackjack");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
