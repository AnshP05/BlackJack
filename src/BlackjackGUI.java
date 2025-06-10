
import javafx.scene.image.ImageView;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackjackGUI extends Application {

    ImageView[] playerCardSlots = new ImageView[10];
    ImageView[] dealerCardSlots = new ImageView[10];
    Label bankrollLabel, statusLabel, playerLabel, dealerLabel;
    Button hitButton, standButton, newGameButton, setBankRollButton;
    TextField bankrollInput, betInput;
    final int CARD_WIDTH = 100;
    final int CARD_HEIGHT = 140;
    final int SPACING = 15;
    final int PADDING = 20;
    final Font LABEL_FONT = new Font("Arial", 18);
    int playerCardIndex = 0;
    int dealerCardIndex = 0;


    GameController controller; 

    @Override
    public void start(Stage primaryStage) {

        

        initializeButtons();
        createCardSlots();
        createLabels();

        VBox root = buildLayout();
        wireButtonActions();

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Blackjack");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = new GameController(this); 
        controller.updateBankroll(controller.getHumanPlayer().getBankroll());

        controller.startNewGame();
    }

    private VBox buildLayout() {

        HBox playerCardBox = new HBox(SPACING, playerCardSlots);
        playerCardBox.setAlignment(Pos.CENTER);
        HBox dealerCardBox = new HBox(SPACING, dealerCardSlots);
        dealerCardBox.setAlignment(Pos.CENTER);

        VBox playerSection = new VBox(SPACING,bankrollLabel, playerLabel, playerCardBox);
        playerSection.setAlignment(Pos.CENTER);

        VBox dealerSection = new VBox(SPACING, dealerLabel, dealerCardBox);
        dealerSection.setAlignment(Pos.CENTER);

        HBox controlsBox = new HBox(SPACING, hitButton, standButton, newGameButton);
        controlsBox.setAlignment(Pos.CENTER); 

        HBox inputBox = new HBox(SPACING, bankrollInput, setBankRollButton, betInput);
        inputBox.setAlignment(Pos.CENTER);

        VBox gameLayout = new VBox(PADDING, dealerSection, playerSection, inputBox, controlsBox, statusLabel);
        gameLayout.setAlignment(Pos.CENTER);
        gameLayout.setStyle("-fx-background-color: #2E7D32;");
        return gameLayout;
    }   

    void initializeButtons() {
        hitButton = new Button("Hit");
        standButton = new Button("Stand");
        newGameButton = new Button("New Game");
        
        bankrollInput = new TextField();
        bankrollInput.setPromptText("Enter Bankroll");

        betInput = new TextField();
        betInput.setPromptText("Enter Bet Amount");
        betInput.setDisable(true);

        newGameButton.setDisable(true);

        setBankRollButton = new Button("Set Bankroll");
    }
    void createCardSlots() {
        for (int i = 0; i < 10; i++) {
            playerCardSlots[i] = new ImageView();
            playerCardSlots[i].setFitWidth(CARD_WIDTH);
            playerCardSlots[i].setFitHeight(CARD_HEIGHT);

            dealerCardSlots[i] = new ImageView();
            dealerCardSlots[i].setFitWidth(CARD_WIDTH);
            dealerCardSlots[i].setFitHeight(CARD_HEIGHT);
        }
    }

    void createLabels() {
        playerLabel = new Label("Player");
        playerLabel.setFont(LABEL_FONT);
        playerLabel.setAlignment(Pos.CENTER);

        dealerLabel = new Label("Dealer");
        dealerLabel.setFont(LABEL_FONT);
        dealerLabel.setAlignment(Pos.CENTER);

        bankrollLabel = new Label("Bankroll: ");
        bankrollLabel.setFont(LABEL_FONT);
        bankrollLabel.setAlignment(Pos.CENTER);

        statusLabel = new Label("Welcome to Blackjack!");
        statusLabel.setFont(LABEL_FONT);
        statusLabel.setAlignment(Pos.CENTER);
    }

    void wireButtonActions() {
        setBankRollButton.setOnAction(e -> {
        try {
            double bankroll = Double.parseDouble(bankrollInput.getText());
            if (bankroll <= 0) throw new NumberFormatException();

            controller.getHumanPlayer().adjustBankroll(bankroll);

            // Disable bankroll setup
            setBankRollButton.setDisable(true);
            bankrollInput.setDisable(true); // 🔒 gray it out so user can't edit
            bankrollInput.clear();

            // Enable bet input
            betInput.setDisable(false);
            betInput.requestFocus(); // 👀 move cursor to bet field

            newGameButton.setDisable(false);

            controller.getGui().updateBankrollLabel(controller.getHumanPlayer().getBankroll());
            showMessage("Bankroll set to $" + bankroll + ". Please enter a bet amount.");
        } catch (NumberFormatException ex) {
            showMessage("Invalid bankroll amount. Please enter a positive number.");
        }
    });

        hitButton.setOnAction(e -> controller.playerHits());
        standButton.setOnAction(e -> controller.playerStands());
        newGameButton.setOnAction(e -> controller.startNewGame());
    }

    // --- Methods the controller can call ---

    void displayPlayerCard(Card card) {
        if (playerCardIndex < playerCardSlots.length) {
            playerCardSlots[playerCardIndex++].setImage(CardImageLoader.getCardImage(card));
        }
    }

    void displayDealerCard(Card card) {
        if (dealerCardIndex < dealerCardSlots.length) {
            dealerCardSlots[dealerCardIndex++].setImage(CardImageLoader.getCardImage(card));
        }
    }
    

    void updateBankrollLabel(double amount) {
        bankrollLabel.setText("Bankroll: $" + amount);
    }

    void showMessage(String message) {
        statusLabel.setText(message);
    }

    void clearTable() {
        for (ImageView iv : playerCardSlots) {
            iv.setImage(null);
        }
        for (ImageView iv : dealerCardSlots) {
            iv.setImage(null);
        }
        statusLabel.setText("New game started.");
        playerCardIndex = 0;
        dealerCardIndex = 0;

    }

    void decrementDealerCardIndex() {
        if (dealerCardIndex > 0) {
            dealerCardIndex--;
        }
    }

    void enableControls(boolean enable) {
        hitButton.setDisable(!enable);
        standButton.setDisable(!enable);
    }

    String getBetInput() {
        return betInput.getText();
    }
}

