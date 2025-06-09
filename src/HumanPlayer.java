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

    public double placeBet(double betAmount) {
        if (betAmount <= 0 || betAmount > bankroll) {
            throw new IllegalArgumentException("Invalid bet amount.");
        }
        bankroll -= betAmount;
        return betAmount;
    }

}