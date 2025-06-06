public abstract class Player {

    protected String name;
    protected Hand hand;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }
    
    public String getName() {
        return name;
    }

    public Hand getHand() {
        return hand;
    }

    public void resetHand() {
        hand.clear();
    }

    public abstract boolean wantsToHit();

}
