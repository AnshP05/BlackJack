public class Dealer extends Player {

    public Dealer() {
        super("Dealer");
    }
    
    public boolean wantsToHit() {
        // Dealer always hits if total value is less than 17
        return hand.getTotalValue() < 17;
    }
    
}
