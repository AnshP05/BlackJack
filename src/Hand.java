public class Hand {
    
    private java.util.List<Card> cards;
    
    public Hand() {
        cards = new java.util.ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }
    public java.util.List<Card> getCards() {
        return cards;
    }

    public int getTotalValue() {
        int total = 0;
        int acesCount = 0;
        for(Card card : cards) {
            total += card.getValue();
            if(card.getRank().equals("A")) {
                acesCount++;
            }
        }
        while(isBust(total) && acesCount > 0) {
            total -= 10; // Adjust for Ace being 1 instead of 11
            acesCount--;
        }
        return total;
    }

    private boolean isBust(int total) {
        return total > 21;
    }
    public boolean isBust() {
        return getTotalValue() > 21;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getTotalValue() == 21;
    }

    public void clear() {
        cards.clear();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card card : cards) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }
}
