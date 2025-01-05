import java.util.*;
import java.util.concurrent.*;

public class OnlineExamSystem {

    static Scanner scanner = new Scanner(System.in);
    static Map<String, String> users = new HashMap<>();
    static Map<String, String> userProfiles = new HashMap<>();
    static String currentUser = null;
    static boolean isLoggedIn = false;
    static int totalQuestions = 5;  // Total number of questions in the exam
    static String[] userAnswers = new String[totalQuestions];

    // MCQ questions and options
    static String[] questions = {
        "What is the capital of France?",
        "What is 2 + 2?",
        "Which planet is known as the Red Planet?",
        "What is the largest mammal?",
        "Who wrote 'Romeo and Juliet'?"
    };

    static String[][] options = {
        {"1. Paris", "2. London", "3. Berlin", "4. Madrid"},
        {"1. 3", "2. 4", "3. 5", "4. 6"},
        {"1. Earth", "2. Mars", "3. Venus", "4. Jupiter"},
        {"1. Elephant", "2. Blue Whale", "3. Giraffe", "4. Lion"},
        {"1. Shakespeare", "2. Dickens", "3. Austen", "4. Poe"}
    };

    static String[] correctAnswers = {"1", "2", "2", "2", "1"}; // Correct answers for the questions

    public static void main(String[] args) {
        System.out.println("Welcome to the Online Exam System");

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("Select option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void login() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username) && users.get(username).equals(password)) {
            currentUser = username;
            isLoggedIn = true;
            System.out.println("Login successful!");

            while (isLoggedIn) {
                showMenu();
            }
        } else {
            System.out.println("Invalid username or password. Try again.");
        }
    }

    public static void showMenu() {
        System.out.println("\n--- User Menu ---");
        System.out.println("1. Update Profile and Password");
        System.out.println("2. Start Online Exam");
        System.out.println("3. Logout");
        System.out.print("Select option: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                updateProfile();
                break;
            case 2:
                startExam();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    public static void updateProfile() {
        System.out.print("Enter your new profile information: ");
        String newProfile = scanner.nextLine();
        userProfiles.put(currentUser, newProfile);

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();
        users.put(currentUser, newPassword);

        System.out.println("Profile and password updated successfully!");
    }

    public static void startExam() {
        System.out.println("\nStarting the exam...");
        System.out.println("You have 2 minutes to complete the exam.");

        // Start a timer thread that will automatically submit the exam after 2 minutes
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                TimeUnit.MINUTES.sleep(2);  // Exam duration is 2 minutes
                autoSubmit();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Ask the questions and allow the user to select answers
        for (int i = 0; i < totalQuestions; i++) {
            System.out.println("\n" + (i + 1) + ". " + questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.print("Enter your answer (1/2/3/4): ");
            userAnswers[i] = scanner.nextLine();
        }

        System.out.println("Exam completed. Submitting...");
        evaluateExam();
    }

    public static void autoSubmit() {
        System.out.println("\nTime is up! Automatically submitting your exam.");
        evaluateExam();
    }

    public static void evaluateExam() {
        int score = 0;
        for (int i = 0; i < totalQuestions; i++) {
            if (userAnswers[i].equals(correctAnswers[i])) {
                score++;
            }
        }

        System.out.println("\nExam submitted.");
        System.out.println("You scored " + score + " out of " + totalQuestions);
    }

    public static void logout() {
        System.out.println("Logging out...");
        isLoggedIn = false;
        currentUser = null;
    }
}
