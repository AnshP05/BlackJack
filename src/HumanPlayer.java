public class HumanPlayer extends Player {

    private static final java.util.Scanner scanner = new java.util.Scanner(System.in);

    public HumanPlayer(String name) {
        super(name);
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
}
