import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class OnlineReservation {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton reservationButton;
    private JButton cancelButton;
    private JTextArea infoArea;

    // Sample hardcoded users (username, password)
    private String[] validUsers = {"user1", "user2"};
    private String[] validPasswords = {"password1", "password2"};

    // In-memory reservation storage (PNR -> reservation details)
    private Map<String, String> reservations = new HashMap<>();
    private boolean loggedIn = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OnlineReservation().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Online Reservation System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        frame.add(panel);
        panel.setLayout(null);

        // Login form
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 20, 80, 25);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 165, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 165, 25);
        panel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 80, 80, 25);
        loginButton.addActionListener(new LoginActionListener());
        panel.add(loginButton);

        // Information area
        infoArea = new JTextArea();
        infoArea.setBounds(10, 150, 350, 150);
        infoArea.setEditable(false);
        panel.add(infoArea);

        // Reservation and cancellation buttons (hidden until logged in)
        reservationButton = new JButton("Make Reservation");
        reservationButton.setBounds(100, 120, 180, 25);
        reservationButton.setVisible(false);
        reservationButton.addActionListener(new ReservationActionListener());
        panel.add(reservationButton);

        cancelButton = new JButton("Cancel Reservation");
        cancelButton.setBounds(100, 120, 180, 25);
        cancelButton.setVisible(false);
        cancelButton.addActionListener(new CancelActionListener());
        panel.add(cancelButton);

        frame.setVisible(true);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            for (int i = 0; i < validUsers.length; i++) {
                if (validUsers[i].equals(username) && validPasswords[i].equals(password)) {
                    loggedIn = true;
                    infoArea.setText("Login successful! You can now make reservations or cancel.");
                    reservationButton.setVisible(true);
                    cancelButton.setVisible(true);
                    loginButton.setEnabled(false);
                    usernameField.setEnabled(false);
                    passwordField.setEnabled(false);
                    break;
                }
            }

            if (!loggedIn) {
                infoArea.setText("Invalid login details. Please try again.");
            }
        }
    }

    private class ReservationActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reservation form
            String name = JOptionPane.showInputDialog(frame, "Enter your name:");
            String trainNumber = JOptionPane.showInputDialog(frame, "Enter train number:");
            String trainName = JOptionPane.showInputDialog(frame, "Enter train name:");
            String classType = JOptionPane.showInputDialog(frame, "Enter class type:");
            String dateOfJourney = JOptionPane.showInputDialog(frame, "Enter date of journey (dd-mm-yyyy):");
            String from = JOptionPane.showInputDialog(frame, "Enter departure location:");
            String to = JOptionPane.showInputDialog(frame, "Enter destination location:");

            // Generate PNR (random number for simplicity)
            String pnr = UUID.randomUUID().toString();

            // Save the reservation
            String reservationDetails = String.format("Name: %s\nTrain Number: %s\nTrain Name: %s\nClass: %s\nDate: %s\nFrom: %s\nTo: %s",
                    name, trainNumber, trainName, classType, dateOfJourney, from, to);
            reservations.put(pnr, reservationDetails);

            infoArea.setText("Reservation successful!\nPNR: " + pnr);
        }
    }

    private class CancelActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String pnr = JOptionPane.showInputDialog(frame, "Enter PNR to cancel:");

            if (reservations.containsKey(pnr)) {
                String reservationDetails = reservations.get(pnr);
                int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to cancel the following reservation?\n" + reservationDetails);

                if (confirm == JOptionPane.YES_OPTION) {
                    reservations.remove(pnr);
                    infoArea.setText("Reservation cancelled successfully.");
                } else {
                    infoArea.setText("Cancellation aborted.");
                }
            } else {
                infoArea.setText("Invalid PNR. No reservation found.");
            }
        }
    }
}