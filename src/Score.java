// Score.java
import java.util.ArrayList;
import java.util.List;

public class Score {
    // Primary scoring method
    public int evaluateScore(Card[][] grid) {
        int total = 0;

        // score rows
        for (int i = 0; i < 4; i++) {
            List<Card> row = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                if (grid[i][j] != null && isPlayableSpot(i, j)) {
                    row.add(grid[i][j]);
                }
            }
            total += scoreHand(row);
        }

        // score columns
        for (int j = 0; j < 5; j++) {
            List<Card> col = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if (grid[i][j] != null && isPlayableSpot(i, j)) {
                    col.add(grid[i][j]);
                }
            }
            total += scoreHand(col);
        }

        return total;
    }

    // Scores a single hand (row or column)
    private int scoreHand(List<Card> hand) {
        if (hand.isEmpty()) return 0;

        // calculate the possibl totals considering aces
        int sum = 0;
        int aces = 0;
        for (Card c : hand) {
            if (c.isAce()) aces++;
            sum += c.getMinValue();
        }

        // try to make as many aces high (11) as possible without the bust outcome
        int best = sum;
        for (int i = 0; i < aces; i++) {
            if (best + 10 <= 21) best += 10;
        }

        // assign the points based on rules
        if (best > 21) return 0; // bust
        if (hand.size() == 2 && best == 21) return 10; // blackjack
        if (best == 21) return 7;
        if (best == 20) return 5;
        if (best == 19) return 4;
        if (best == 18) return 3;
        if (best == 17) return 2;
        return 1; // <=16
    }

    // Matches the playable spot logic that i put in my BlackjackSolitaire.java file
    private boolean isPlayableSpot(int row, int col) {
        if (row == 0 || row == 1) return true;
        if (row == 2 || row == 3) return col >= 1 && col <= 3;
        return false;
    }
}
