import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        char[] suits = {'H', 'D', 'C', 'S'};

        // loop to create the 52 card deck
        for (char suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(rank, suit));
            }
        }
        // Use shuffle method from below
        shuffle();
    }

    //Shuffles the deck randomly
    public void shuffle() {
        Collections.shuffle(cards);
    }

    //Draws and removes the top card from deck - the one at index 0
    public Card drawCard() {
        if (isEmpty()) {
            throw new IllegalStateException("No more cards in deck!");
        }
        return cards.remove(0);
    }

    //isEmpty() returns true if there are no cards remaining
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    //cardsLeft() returns the number of cards remaining in the deck
    public int cardsLeft() {
        return cards.size();
    }

}