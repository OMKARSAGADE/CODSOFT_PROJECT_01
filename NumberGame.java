import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends JFrame {
    private int targetNumber;
    private int attemptsLeft;
    private int score;
    private final int maxAttempts = 5;

    private JLabel messageLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel attemptsLabel;
    private JLabel scoreLabel;

    public NumberGame() {
        // Apply custom theme
        setCustomTheme();

        setTitle("Number Game");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Panel for the main game area
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));
        mainPanel.setBackground(new Color(240, 248, 255)); // Light blue background for the panel

        // Initialize game variables
        resetGame();

        // GUI Components
        messageLabel = new JLabel("Guess a number between 1 and 100:", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(new Color(0, 102, 204)); // Blue color for message
        mainPanel.add(messageLabel);

        guessField = new JTextField();
        guessField.setFont(new Font("Arial", Font.PLAIN, 14));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setBackground(new Color(255, 255, 224)); // Light yellow background for text field
        guessField.setForeground(new Color(0, 51, 102)); // Dark blue text color
        mainPanel.add(guessField);

        guessButton = new JButton("Submit Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 14));
        guessButton.setBackground(new Color(34, 193, 195)); // Teal color for button
        guessButton.setForeground(Color.WHITE); // White text on button
        guessButton.setFocusPainted(false); // Remove focus outline
        guessButton.setBorder(BorderFactory.createLineBorder(new Color(28, 170, 158), 2)); // Border color
        mainPanel.add(guessButton);

        attemptsLabel = new JLabel("Attempts left: " + attemptsLeft, SwingConstants.CENTER);
        attemptsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel.setForeground(new Color(255, 69, 0)); // Red color for attempts label
        mainPanel.add(attemptsLabel);

        scoreLabel = new JLabel("Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        scoreLabel.setForeground(new Color(0, 128, 0)); // Green color for score label
        mainPanel.add(scoreLabel);

        // Add the main panel to the JFrame
        add(mainPanel, BorderLayout.CENTER);

        // Button Action Listener
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleGuess();
            }
        });
    }

    private void setCustomTheme() {
        try {
            // Change Look and Feel to Nimbus (modern, clean UI)
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            // Custom Colors for Components
            UIManager.put("control", new Color(230, 230, 250)); // Lavender background
            UIManager.put("info", new Color(200, 200, 255)); // Light blue
            UIManager.put("nimbusBase", new Color(100, 100, 200)); // Deep blue
            UIManager.put("nimbusFocus", new Color(115, 164, 209)); // Lighter blue
            UIManager.put("nimbusLightBackground", new Color(248, 248, 255)); // Off-white
            UIManager.put("nimbusOrange", new Color(255, 204, 102)); // Orange for progress bars
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255)); // White text
            UIManager.put("nimbusSelectionBackground", new Color(100, 149, 237)); // Cornflower blue
            UIManager.put("text", new Color(0, 0, 0)); // Black text

            // Update the theme
            UIManager.getLookAndFeelDefaults().put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        } catch (Exception e) {
            System.err.println("Nimbus Look and Feel not available. Using default.");
        }
    }

    private void resetGame() {
        Random random = new Random();
        targetNumber = random.nextInt(100) + 1;
        attemptsLeft = maxAttempts;
        score = 0;
    }

    private void handleGuess() {
        String userInput = guessField.getText();
        try {
            int guess = Integer.parseInt(userInput);
            if (guess < 1 || guess > 100) {
                messageLabel.setText("Enter a number between 1 and 100.");
                return;
            }

            attemptsLeft--;
            if (guess == targetNumber) {
                score += 10;
                messageLabel.setText("Correct! Starting a new round.");
                messageLabel.setForeground(new Color(0, 128, 0)); // Green for correct guess
                startNewRound();
            } else if (guess < targetNumber) {
                messageLabel.setText("Too low! Try again.");
                messageLabel.setForeground(new Color(255, 69, 0)); // Red for low guess
            } else {
                messageLabel.setText("Too high! Try again.");
                messageLabel.setForeground(new Color(255, 69, 0)); // Red for high guess
            }

            if (attemptsLeft <= 0) {
                messageLabel.setText("Out of attempts! The number was " + targetNumber);
                messageLabel.setForeground(new Color(255, 69, 0)); // Red for game over
                startNewRound();
            }

            attemptsLabel.setText("Attempts left: " + attemptsLeft);
            scoreLabel.setText("Score: " + score);
        } catch (NumberFormatException ex) {
            messageLabel.setText("Please enter a valid number.");
            messageLabel.setForeground(new Color(255, 0, 0)); // Red for invalid input
        }
        guessField.setText("");
    }

    private void startNewRound() {
        int choice = JOptionPane.showConfirmDialog(this, "Play another round?", "Number Game", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
            messageLabel.setText("Guess a number between 1 and 100:");
            messageLabel.setForeground(new Color(0, 102, 204)); // Blue for new round
            attemptsLabel.setText("Attempts left: " + attemptsLeft);
        } else {
            JOptionPane.showMessageDialog(this, "Final Score: " + score);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGame game = new NumberGame();
            game.setVisible(true);
        });
    }
}
