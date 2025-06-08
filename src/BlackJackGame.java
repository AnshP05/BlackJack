public class BlackJackGame {

    private Deck deck;
    private HumanPlayer player;
    private Dealer dealer;
    private boolean gameOver;
    private int numberOfRounds = 0;
    private boolean lastRoundWasTie = false;
    private double lastBet = 0.0;

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
            if(player.getBankroll() <= 0) {
                System.out.println("You have no money left. Game over!");
                gameOver = true;
                break;
            }
            System.out.println("Do you want to play another round? (yes/no)");
            String response = player.getScanner().nextLine().trim().toLowerCase();
            if(response.equals("no")) {
                gameOver = true;
                System.out.println("Thanks for playing! Goodbye!");
            } else if(!response.equals("yes")) {
                System.out.println("Invalid input. Exiting the game.");
                gameOver = true;
            }
            deck.shuffle(); // Shuffle the deck for the next round
        }
    }

    private void playOneRound() {
        double bet = lastRoundWasTie ? lastBet : player.placeBet();
        lastBet = bet;
        initialDeal();
        
        System.out.printf("%s's money: $%.2f\n", player.getName(), player.getBankroll());
        numberOfRounds++;
        
        if(player.getHand().isBlackjack()) {
            System.out.println(player.getName() + " has a Blackjack! You win!");
            player.adjustBankroll(bet * 1.5); 
            return;
        }
        while(player.wantsToHit()) {
            player.getHand().addCard(deck.dealCard());
            System.out.println(player.getName() + "'s hand:\n" + player.getHand());
            if(player.getHand().isBlackjack()) {
                System.out.println(player.getName() + " has a Blackjack! You win!");
                player.adjustBankroll(bet * 1.5);
                resetTie();
                return;
            }
            if(player.getHand().isBust()) {
                System.out.println(player.getName() + " busts! Dealer wins!");
                resetTie();
                return;
            }
        }

        System.out.println(dealer.getName() + "'s turn.");
        System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());

        if(dealer.getHand().isBlackjack()) {
            System.out.println(dealer.getName() + " has a Blackjack! Dealer wins!");
            player.adjustBankroll(-bet);
            resetTie();
            return;
        }
        while(dealer.wantsToHit()) {
            dealer.getHand().addCard(deck.dealCard());
            System.out.println(dealer.getName() + "'s hand:\n" + dealer.getHand());
        }
        if(dealer.getHand().isBust()) {
            System.out.println(dealer.getName() + " busts! " + player.getName() + " wins!");
            player.adjustBankroll(bet);
            resetTie();
            return;
        } else {
            determineWinner(bet);
        }
        
        

    }

    private void initialDeal() {
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
            resetTie();
        } else if(dealerTotal > playerTotal) {
            System.out.println(dealer.getName() + " wins!");
            player.adjustBankroll(-bet);
            resetTie();
        } else {
            System.out.printf("It's a tie! Your bet of $%.2f carries over to the next round.\n", bet);
            lastRoundWasTie = true;
        }
    }

    private void resetTie() {
        lastRoundWasTie = false;
    }



    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while(true) {
            System.out.println("Welcome to Blackjack! Please enter your name:");
            String playerName = scanner.nextLine().trim();
            if (!playerName.isEmpty()) {
                System.out.print("Please enter your initial bankroll: ");
                if (scanner.hasNextDouble()) {
                    double initialBankroll = scanner.nextDouble();
                    if (initialBankroll > 0) {
                        scanner.nextLine(); // Consume the newline character
                        BlackJackGame game = new BlackJackGame(playerName, initialBankroll);
                        game.startGame();
                        break;
                    } else {
                        System.out.println("Starting bankroll must be greater than zero. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input for bankroll. Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            } else {
                System.out.println("Name cannot be empty. Please try again.");
            }
        }
    }

}

