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
        System.out.println("Welcome to Blackjack!");
        while(!gameOver) {
            playOneRound();
            System.out.println("Do you want to play another round? (yes/no)");
            String response = player.getScanner().nextLine().trim().toLowerCase();
            if(response.equals("no")) {
                gameOver = true;
                System.out.println("Thanks for playing! Goodbye!");
            } else if(!response.equals("yes")) {
                System.out.println("Invalid input. Exiting the game.");
                gameOver = true;
            }
        }
    }

    private void playOneRound() {
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
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.println("Enter your name:");
        String playerName = scanner.nextLine().trim();
        scanner.close();
        BlackJackGame game = new BlackJackGame(playerName);

        game.startGame();
    }

}

