public class HumanPlayer extends Player {

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);
    private double bankroll = 0.0;

    public HumanPlayer(String name) {
        super(name);
    }
    public boolean wantsToHit() {
    while (true) {
        System.out.println("Do you want to 'hit' or 'stand'? Type your choice:");
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("hit")) return true;
        if (input.equals("stand")) return false;
        System.out.println("Invalid input. Please type 'hit' or 'stand'.");
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
    scanner.nextLine(); // consume newline
    while(bet <= 0 || bet > bankroll) {
        System.out.println("Invalid bet. Enter an amount between $0 and $" + bankroll);
        bet = scanner.nextDouble();
        scanner.nextLine();
    }
    return bet;
}

}