public class BlackJackGame {

    private Deck deck;
    private HumanPlayer player;
    private Dealer dealer;
    private boolean gameOver;

    public BlackJackGame(String playerName) {
        deck = new Deck();
        player = new HumanPlayer(playerName);
        dealer = new Dealer();
        gameOver = false;
    }

    public void startGame() {
        initialDeal();
        if(player.getHand().isBlackjack()) {
            System.out.println(player.getName() + " has a Blackjack! You win!");
            return;
        }
        while(player.wantsToHit()) {
            player.getHand().addCard(deck.dealCard());
            if(player.getHand().isBlackjack()) {
                System.out.println(player.getName() + " has a Blackjack! You win!");
                return;
            }
            if(player.getHand().isBust()) {
                System.out.println(player.getName() + " busts! Dealer wins!");
                return;
            }
            System.out.println(player.getName() + "'s hand:\n" + player.getHand());
        }

        System.out.println(dealer.getName() + "'s turn.");
        System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());

        if(dealer.getHand().isBlackjack()) {
            System.out.println(dealer.getName() + " has a Blackjack! Dealer wins!");
            return;
        }
        while(dealer.wantsToHit()) {
            dealer.getHand().addCard(deck.dealCard());
            System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());
        }
        if(dealer.getHand().isBust()) {
            System.out.println(dealer.getName() + " busts! " + player.getName() + " wins!");
        } else {
            determineWinner();
        }
    }

    private void initialDeal() {
        deck.shuffle();
        player.resetHand();
        dealer.resetHand();

        player.getHand().addCard(deck.dealCard());
        player.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());
        dealer.getHand().addCard(deck.dealCard());

        System.out.println(player.getName() + "'s hand:\n" + player.getHand());
        System.out.println(dealer.getName() + "'s hand: " + dealer.getHand().getCards().get(0) + "\n[Hidden Card]");
    }

    private void determineWinner() {
        int playerTotal = player.getHand().getTotalValue();
        int dealerTotal = dealer.getHand().getTotalValue();

        System.out.println(player.getName() + "'s total: " + playerTotal);
        System.out.println(dealer.getName() + "'s total: " + dealerTotal);

        if(playerTotal > dealerTotal) {
            System.out.println(player.getName() + " wins!");
        } else if(dealerTotal > playerTotal) {
            System.out.println(dealer.getName() + " wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    public static void main(String[] args) {
    BlackJackGame game = new BlackJackGame("Player");
    game.startGame();
}

}

