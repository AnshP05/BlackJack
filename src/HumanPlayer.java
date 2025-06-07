public class HumanPlayer extends Player {

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);
    private double bankroll = 0.0;

    public HumanPlayer(String name, double initialBet) {
        super(name);
        this.bankroll = initialBet;
    }

    public boolean wantsToHit() {
        System.out.println("Do you want to 'hit' or 'stand'? Type your choice:");
        String input;
            input = scanner.nextLine().trim().toLowerCase();
            if(input.equals("hit")) {
                return true;
            } else {
                return false;
            }
    }

    public java.util.Scanner getScanner() {
        return scanner;
    }

    public double getBankroll() {
        return bankroll;
    }

    public void adjustBankroll(double amount) {
        bankroll += amount;
    }

    public double placeBet() {
        System.out.println("Your current bankroll is: $" + bankroll);
        System.out.print("Enter your bet amount: $");
        double bet = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character
        while(bet <= 0 || bet > bankroll) {
            if(bet <= 0) {
                System.out.println("Bet must be greater than zero. Please enter a valid bet amount.");
            } else {
                System.out.println("Insufficient funds. Please enter a valid bet amount.");
            }
            System.out.print("Enter your bet amount: $");
            bet = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character
        }
        return bet;
    } 
}