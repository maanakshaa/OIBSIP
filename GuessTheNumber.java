
import javax.swing.*;
import java.util.Random;

public class GuessTheNumber {

    public static void main(String[] args) {
        // Game variables
        int totalRounds = 3;
        int maxAttempts = 10;
        int score = 0;

        for (int round = 1; round <= totalRounds; round++) {
            int attempts = 0;
            int targetNumber = new Random().nextInt(100) + 1; // Random number between 1 and 100
            boolean guessedCorrectly = false;

            // Displaying the round info
            JOptionPane.showMessageDialog(null, "Round " + round + " of " + totalRounds);

            while (attempts < maxAttempts && !guessedCorrectly) {
                // Prompt user for input
                String userInput = JOptionPane.showInputDialog(null, "Guess the number (1-100): ");
                if (userInput == null) break; // Exit if user presses Cancel

                try {
                    int userGuess = Integer.parseInt(userInput);
                    attempts++;

                    if (userGuess < targetNumber) {
                        JOptionPane.showMessageDialog(null, "Too low! Try again.");
                    } else if (userGuess > targetNumber) {
                        JOptionPane.showMessageDialog(null, "Too high! Try again.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number.");
                        guessedCorrectly = true;
                        score += (maxAttempts - attempts + 1); // Give points based on attempts
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                }
            }

            if (!guessedCorrectly) {
                JOptionPane.showMessageDialog(null, "Sorry! You couldn't guess the number in " + maxAttempts + " attempts. The number was " + targetNumber);
            }
        }

        // Display the final score after all rounds
        JOptionPane.showMessageDialog(null, "Game Over! Your total score is: " + score);
    }
}
