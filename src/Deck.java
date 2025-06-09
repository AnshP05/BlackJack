import java.util.List;

public class Deck {
    
    private List<Card> cards;
    private int currentCardIndex;

    public Deck() {
        cards = new java.util.ArrayList<>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king", "ace"};

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
            case "jack" :
            case "queen" :
            case "king" :
                return 10;
            case "ace" :
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
