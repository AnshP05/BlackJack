
public class GameController {

    private BlackjackGUI gui;
    private Deck deck;
    private HumanPlayer player;
    private Dealer dealer;
    private int numberOfRounds;

    public GameController(BlackjackGUI gui) {
        this.gui = gui;
        this.deck = new Deck();
        this.player = new HumanPlayer("Player");
        this.dealer = new Dealer();
        this.numberOfRounds = 0;
    }
    //clears table 
    public void startNewGame() {
        deck.shuffle();
        gui.clearTable();
        gui.updateBankrollLabel(player.getBankroll());
        
        player.resetHand();
        dealer.resetHand();

        player.getHand().addCard(deck.dealCard());
        player.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());

        for(Card card : player.getHand().getCards()) {
            gui.displayPlayerCard(card);
        }

        gui.displayDealerCard(dealer.getHand().getCards().get(0)); 
        gui.displayDealerCard(null);

        gui.enableControls(true);
        gui.showMessage("New game started. Player's turn.");

    }

    public void playerHits() {
        Card newCard = deck.dealCard();
        player.getHand().addCard(newCard);
        gui.displayPlayerCard(newCard);

        if(player.getHand().isBust()) {
            gui.showMessage("Player busts! Dealer wins.");
            gui.enableControls(false);
            updateBankroll(-10); 
        } else {
            gui.showMessage("Player hits. Current total: " + player.getHand().getTotalValue());
        }
    }

    public void playerStands() {
        gui.enableControls(false);

        gui.displayDealerCard(dealer.getHand().getCards().get(1));
        gui.showMessage("Player stands. Dealer's turn.");

        while(dealer.wantsToHit()) {
            Card newCard = deck.dealCard();
            dealer.getHand().addCard(newCard);
            gui.displayDealerCard(newCard);
        }

        determineWinner();

    }

    private void determineWinner() {
        int playerTotal = player.getHand().getTotalValue();
        int dealerTotal = dealer.getHand().getTotalValue();

        gui.showMessage("Player: " + playerTotal + " vs Dealer: " + dealerTotal);


        if(playerTotal > dealerTotal) {
            gui.showMessage("Player wins!");
            player.adjustBankroll(10);
        } else if(dealerTotal > playerTotal) {
            gui.showMessage("Dealer wins!");
            player.adjustBankroll(-10);
        } else {
            gui.showMessage("It's a tie!");
        }
        gui.updateBankrollLabel(player.getBankroll());
    }

    public HumanPlayer getHumanPlayer() {
        return player;
    }
    public void updateBankroll(double amount) {
        player.adjustBankroll(amount);
        gui.updateBankrollLabel(player.getBankroll());
    }
}
