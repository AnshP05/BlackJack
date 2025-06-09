
public class GameController {

    private BlackjackGUI gui;
    private Deck deck;
    private HumanPlayer player;
    private Dealer dealer;
    private int numberOfRounds;
    private double betAmount;

    public GameController(BlackjackGUI gui) {
        this.gui = gui;
        this.deck = new Deck();
        this.player = new HumanPlayer("Player");
        this.dealer = new Dealer();
        this.numberOfRounds = 0;
    }
    
    public void startNewGame() {
        String betText = gui.getBetInput();
    
        try {
            betAmount = Double.parseDouble(betText);
            if (betAmount <= 0 || betAmount > player.getBankroll()) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            
            return;
        }

        player.placeBet(betAmount);
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

        if(player.getHand().isBlackjack()) {
            gui.showMessage("Blackjack! Player wins!");
            gui.enableControls(false);
            player.adjustBankroll(betAmount * 2.5); // Player wins 2.5 times the bet
            gui.updateBankrollLabel(player.getBankroll());
            return;
        }


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
        } else {
            gui.showMessage("Player hits. Current total: " + player.getHand().getTotalValue());
        }
    }

    public void playerStands() {
        gui.enableControls(false);

        gui.decrementDealerCardIndex();
        gui.displayDealerCard(dealer.getHand().getCards().get(1)); // Show the dealer's second card
        gui.showMessage("Player stands. Dealer's turn.");

        while(dealer.wantsToHit()) {
            if(dealer.getHand().isBlackjack()) {
                gui.showMessage("Dealer has Blackjack! Dealer wins.");
                gui.enableControls(false);
                return;
            }
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
            player.adjustBankroll(betAmount * 2); 
        } else if(dealerTotal > playerTotal && dealerTotal <= 21) {
            gui.showMessage("Dealer wins!");
        } else if(playerTotal < dealerTotal && dealerTotal > 21) {
            gui.showMessage("Dealer busts! Player wins!");
            player.adjustBankroll(betAmount * 2);
        } else {
            gui.showMessage("It's a tie! No one wins.");
            player.adjustBankroll(betAmount); 
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

    public BlackjackGUI getGui() {
        return gui;
    }
}
