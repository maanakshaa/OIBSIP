
import java.util.*;

public class ATMSystem {
    
    static Scanner scanner = new Scanner(System.in);

    static class Transaction {
        String type;
        double amount;
        Date date;

        public Transaction(String type, double amount) {
            this.type = type;
            this.amount = amount;
            this.date = new Date();
        }

        @Override
        public String toString() {
            return "Transaction [Type=" + type + ", Amount=" + amount + ", Date=" + date + "]";
        }
    }

    static class User {
        String userId;
        String pin;
        double balance;
        List<Transaction> transactionHistory;

        public User(String userId, String pin, double balance) {
            this.userId = userId;
            this.pin = pin;
            this.balance = balance;
            this.transactionHistory = new ArrayList<>();
        }

        public void addTransaction(Transaction transaction) {
            transactionHistory.add(transaction);
        }

        public List<Transaction> getTransactionHistory() {
            return transactionHistory;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public double getBalance() {
            return balance;
        }
    }

    static class UserDatabase {
        static Map<String, User> users = new HashMap<>();

        static {
            // Adding some users to simulate a database
            users.put("user1", new User("user1", "1234", 1000));
            users.put("user2", new User("user2", "5678", 2000));
        }

        public static User authenticateUser(String userId, String pin) {
            User user = users.get(userId);
            if (user != null && user.pin.equals(pin)) {
                return user;
            }
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the ATM System");

        // Prompt for user ID and PIN
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();

        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();

        User currentUser = UserDatabase.authenticateUser(userId, pin);

        if (currentUser != null) {
            System.out.println("Login Successful!");
            showMenu(currentUser);
        } else {
            System.out.println("Invalid User ID or PIN. Try Again.");
        }
    }

    private static void showMenu(User currentUser) {
        int choice;

        do {
            System.out.println("\nATM Menu");
            System.out.println("1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewTransactionHistory(currentUser);
                    break;
                case 2:
                    withdraw(currentUser);
                    break;
                case 3:
                    deposit(currentUser);
                    break;
                case 4:
                    transfer(currentUser);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void viewTransactionHistory(User currentUser) {
        if (currentUser.getTransactionHistory().isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction transaction : currentUser.getTransactionHistory()) {
                System.out.println(transaction);
            }
        }
    }

    private static void withdraw(User currentUser) {
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getBalance()) {
            currentUser.setBalance(currentUser.getBalance() - amount);
            currentUser.addTransaction(new Transaction("Withdraw", amount));
            System.out.println("Withdrawal successful. New balance: " + currentUser.getBalance());
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    private static void deposit(User currentUser) {
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        if (amount > 0) {
            currentUser.setBalance(currentUser.getBalance() + amount);
            currentUser.addTransaction(new Transaction("Deposit", amount));
            System.out.println("Deposit successful. New balance: " + currentUser.getBalance());
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    private static void transfer(User currentUser) {
        System.out.print("Enter recipient user ID: ");
        scanner.nextLine();  // Consume newline
        String recipientUserId = scanner.nextLine();

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        if (amount > 0 && amount <= currentUser.getBalance()) {
            User recipientUser = UserDatabase.users.get(recipientUserId);
            if (recipientUser != null) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                currentUser.addTransaction(new Transaction("Transfer to " + recipientUserId, amount));
                recipientUser.setBalance(recipientUser.getBalance() + amount);
                recipientUser.addTransaction(new Transaction("Transfer from " + currentUser.userId, amount));
                System.out.println("Transfer successful. Your new balance: " + currentUser.getBalance());
            } else {
                System.out.println("Recipient user not found.");
            }
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }
}