public class Card {
    // rank: "A", "2", ..., "10", "J", "Q", "K"
    // suit: 'H', 'D', 'C', 'S'
    private final String rank;
    private final char suit;

    public Card(String rank, char suit) {
        if (rank == null) {
            throw new IllegalArgumentException("rank cannot be null");
        }
        rank = rank.trim();
        if (!isValidRank(rank) || !isValidSuit(suit)) {
            throw new IllegalArgumentException("Invalid rank or suit: " + rank + suit);
        }
        this.rank = rank;
        this.suit = suit;
    }

    private boolean isValidRank(String r) {
        if (r.equals("A") || r.equals("J") || r.equals("Q") || r.equals("K")) return true;
        try {
            int v = Integer.parseInt(r);
            return v >= 2 && v <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidSuit(char s) {
        return s == 'H' || s == 'D' || s == 'C' || s == 'S';
    }

    public String getRank() {
        return rank;
    }

    public char getSuit() {
        return suit;
    }

    // Returns true if this card is an Ace
    public boolean isAce() {
        return "A".equals(rank);
    }

    // Minimum numeric value of this card (aces return 1).
    // Face cards (J, Q, K) return 10. Number cards return their integer.
    public int getMinValue() {
        if (isAce()) return 1;
        if (rank.equals("J") || rank.equals("Q") || rank.equals("K")) return 10;
        return Integer.parseInt(rank);
    }

     // Maximum numeric value of this card.
     // For aces this is 11, otherwise the same as getMinValue()

    public int getMaxValue() {
        if (isAce()) return 11;
        return getMinValue();
    }

    // String form used by the assignment: rank followed by suit.
    // Examples: "KH", "3D", "10S", "AS"

    public String toString() {
        return rank + suit;
    }
}
