public class BlackJackGame {

    private Deck deck;
    private HumanPlayer player;
    private Dealer dealer;
    private boolean gameOver;
    private int numberOfRounds = 0;

    public BlackJackGame(String playerName, double startingBankroll) {
        deck = new Deck();
        player = new HumanPlayer(playerName, startingBankroll);
        dealer = new Dealer();
        gameOver = false;
    }

    public void startGame() {
        while(!gameOver) {
            if(player.getBankroll() <= 0) {
                System.out.println("You have no money left. Game over!");
                gameOver = true;
                break;
            }
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
        double bet;
        if(numberOfRounds > 0) {
            bet = player.placeBet();
        } else {
            bet = player.getBankroll(); 
        }
        
        if(player.getHand().isBlackjack()) {
            System.out.println(player.getName() + " has a Blackjack! You win!");
            player.adjustBankroll(bet * 1.5); 
            return;
        }
        while(player.wantsToHit()) {
            player.getHand().addCard(deck.dealCard());
            if(player.getHand().isBlackjack()) {
                System.out.println(player.getName() + " has a Blackjack! You win!");
                player.adjustBankroll(bet * 1.5);
                return;
            }
            if(player.getHand().isBust()) {
                System.out.println(player.getName() + " busts! Dealer wins!");
                player.adjustBankroll(-bet);
                return;
            }
            System.out.println(player.getName() + "'s hand:\n" + player.getHand());
        }

        System.out.println(dealer.getName() + "'s turn.");
        System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());

        if(dealer.getHand().isBlackjack()) {
            System.out.println(dealer.getName() + " has a Blackjack! Dealer wins!");
            player.adjustBankroll(-bet);
            return;
        }
        while(dealer.wantsToHit()) {
            dealer.getHand().addCard(deck.dealCard());
            System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());
        }
        if(dealer.getHand().isBust()) {
            System.out.println(dealer.getName() + " busts! " + player.getName() + " wins!");
            player.adjustBankroll(bet);
            return;
        } else {
            determineWinner(bet);
        }
        System.out.printf("%s's money: $%.2f\n", player.getName(), player.getBankroll());
        numberOfRounds++;

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

    private void determineWinner(double bet) {
        int playerTotal = player.getHand().getTotalValue();
        int dealerTotal = dealer.getHand().getTotalValue();

        System.out.println(player.getName() + "'s total: " + playerTotal);
        System.out.println(dealer.getName() + "'s total: " + dealerTotal);

        if(playerTotal > dealerTotal) {
            System.out.println(player.getName() + " wins!");
            player.adjustBankroll(bet);
        } else if(dealerTotal > playerTotal) {
            System.out.println(dealer.getName() + " wins!");
            player.adjustBankroll(-bet);
        } else {
            System.out.println("It's a tie!");
        }
    }



    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while(true) {
            System.out.println("Welcome to Blackjack! Please enter your name:");
            String playerName = scanner.nextLine().trim();
            if (!playerName.isEmpty()) {
                System.out.println("Please enter your initial bet: ");
                if (scanner.hasNextDouble()) {
                    double initialBet = scanner.nextDouble();
                    if (initialBet > 0) {
                        scanner.nextLine(); // Consume the newline character
                        BlackJackGame game = new BlackJackGame(playerName, initialBet);
                        game.startGame();
                        break;
                    } else {
                        System.out.println("Starting bet must be greater than zero. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input for bet. Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            } else {
                System.out.println("Name cannot be empty. Please try again.");
            }
        }
    }

}

