import java.util.List;

public class Deck {
    
    private List<Card> cards;
    private int currentCardIndex;

    public Deck() {
        cards = new java.util.ArrayList<>();
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};

        for(String suit : suits) {
            for(String rank : ranks) {
                int value = getCardValue(rank);
                cards.add(new Card(rank, suit, value));
            }
        }
        shuffle();
    }

    public void shuffle() {
        java.util.Collections.shuffle(cards);
        currentCardIndex = 0;
    }

    private int getCardValue(String rank) {
        switch(rank) {
            case "J" :
            case "Q" :
            case "K" :
                return 10;
            case "A" :
                return 11; 
            default:
                return Integer.parseInt(rank);
        }
    }

    public Card dealCard() {
        if(currentCardIndex < cards.size()) {
            return cards.get(currentCardIndex++);
        } else {
            throw new IllegalStateException("No more cards in the deck.");
        }
    }

    public boolean isEmpty() {
        return currentCardIndex >= cards.size();
    }

    public int getRemainingCards() {
        return cards.size() - currentCardIndex;
    }
}
