import javafx.scene.image.Image;
import java.util.*;

public class CardImageLoader {
    
    private static final String IMAGE_PATH = "/cards/";
    private static final String BACK_IMAGE_NAME = "backOfCard.png";

    private static final Map<String, Image> cardImages = new HashMap<>();
    private static final Image backImage;

    static {
        System.out.println(CardImageLoader.class.getResource("/cards/backOfCard.png"));

        backImage = new Image(CardImageLoader.class.getResourceAsStream(IMAGE_PATH + BACK_IMAGE_NAME));
        
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

        for(String suit : suits) {
            for(String rank : ranks) {
                String key = rank + "_of_" + suit;
                Image img = new Image(CardImageLoader.class.getResourceAsStream(IMAGE_PATH + key + ".png"));
                cardImages.put(key, img);
            }
        }
    }

    public static Image getCardImage(Card card) {
        if(card == null) {
            return backImage;
        }
        String key = card.getRank().toLowerCase() + "_of_" + card.getSuit().toLowerCase();
        return cardImages.getOrDefault(key, backImage);
    }

    public static Image getBackImage() {
        return backImage;
    }
}
