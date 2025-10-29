import java.util.*;

public class BlackjackSolitaire {
    private Card[][] grid;           // 4x5 grid of card spots (1–16 count)
    private int discardsUsed;        // how many discard slots have been used (max 4)
    private Deck deck;               // deck of the 52 cards
    private Scanner scanner;         // to collect the user input
    private int max_discards = 4;    // the constant which indicates maximum amount of discards possible: 4 in this case

    public BlackjackSolitaire() {
        grid = new Card[4][5];
        discardsUsed = 0;
        deck = new Deck();
        scanner = new Scanner(System.in);
    }

    //Below is the main loop to play the game
    public void play() {
        System.out.println("Welcome to Blackjack Solitaire!");
        while (!isBoardFull()) {
            Card current = deck.drawCard();
            printBoard();
            System.out.println("Current card: " + current);
            System.out.println("Discards remaining: " + (max_discards - discardsUsed));

            int position = promptForPosition();

            // Discard slots are 17 to 20
            if (position >= 17 && position <= 20) {
                if (discardsUsed < max_discards) {
                    discardsUsed++;
                    System.out.println("Card discarded.\n");
                } else {
                    System.out.println("No discards remaining! Try again.\n");
                    // re-prompt user to place card
                    placeCard(current, promptForPosition());
                }
            } else {
                placeCardInGrid(current, position);
            }
        }

        printBoard();
        System.out.println("All scoring spots filled — calculating your score...");
        Score scorer = new Score();
        int total = scorer.evaluateScore(grid);
        System.out.println("Game over! You scored " + total + " points.");
    }

    // Displays the board grid in correct format
    private void printBoard() {
        int pos = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                // check if the position is valid for placement
                if (isPlayableSpot(i, j)) {
                    if (grid[i][j] != null) {
                        System.out.print(grid[i][j] + "\t");
                    } else {
                        System.out.print(pos + "\t");
                    }
                    pos++;
                } else {
                    System.out.print("\t");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    // Returns true if all the 16 scoring spaces are filled, to stop earlier loop
    private boolean isBoardFull() {
        int filled = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (isPlayableSpot(i, j) && grid[i][j] != null) {
                    filled++;
                }
            }
        }
        return filled == 16;
    }

    // Prompt the user for position input (1–20)
    private int promptForPosition() {
        while (true) {
            System.out.print("Enter position (1–20, 17–20 = discards): ");
            String input = scanner.nextLine().trim();
            try {
                int pos = Integer.parseInt(input);
                if (pos >= 1 && pos <= 20) {
                    return pos;
                } else {
                    System.out.println("Invalid position number! Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
            }
        }
    }

    // Places a card based on user’s chosen position
    private void placeCard(Card c, int position) {
        if (position >= 17 && position <= 20) {
            if (discardsUsed < max_discards) {
                discardsUsed++;
                System.out.println("Card discarded.\n");
            } else {
                System.out.println("No discards remaining! Try again.\n");
                placeCard(c, promptForPosition());
            }
            return;
        }

        placeCardInGrid(c, position);
    }

    // Helper method to map the numbered slot (1–16) to the grid index and to place the card
    private void placeCardInGrid(Card c, int position) {
        int count = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                if (isPlayableSpot(i, j)) {
                    if (count == position) {
                        if (grid[i][j] != null) {
                            System.out.println("Spot already filled! Try again.\n");
                            placeCard(c, promptForPosition());
                            return;
                        } else {
                            grid[i][j] = c;
                            System.out.println("Card placed at position " + position + ".\n");
                            return;
                        }
                    }
                    count++;
                }
            }
        }
        System.out.println("Invalid position! Try again.\n");
        placeCard(c, promptForPosition());
    }

    // The below method is to determine which grid spots are playable (based on the example layout)
    private boolean isPlayableSpot(int row, int col) {
        // layout is as below:
        // row 0:cols 0–4 (positions 1–5)
        // row 1:cols 0–4 (positions 6–10)
        // row 2:cols 1–3 (positions 11–13)
        // row 3:cols 1–3 (positions 14–16)
        if (row == 0 || row == 1) return true;
        if (row == 2 || row == 3) return col >= 1 && col <= 3;
        return false;
    }
}
