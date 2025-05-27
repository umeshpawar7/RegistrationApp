import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class RegistrationApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Registration System Menu ===");
            System.out.println("1. Register new user");
            System.out.println("2. Search by Registration Number");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerUser(scanner);
                    break;
                case "2":
                    searchRegistration(scanner);
                    break;
                case "0":
                    System.out.println("Exiting program. Goodbye!");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        }
        scanner.close();
    }

    private static void registerUser(Scanner scanner) {
        System.out.println("\n=== User Registration ===");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your mobile number: ");
        String mobile = scanner.nextLine();

        System.out.print("Enter your age: ");
        int age = readInt(scanner);

        System.out.print("Enter your Aadhar number: ");
        String aadhar = scanner.nextLine();

        System.out.print("Enter your address: ");
        String address = scanner.nextLine();

        System.out.print("Enter your work: ");
        String work = scanner.nextLine();

        System.out.print("Enter your education: ");
        String education = scanner.nextLine();

        String registrationNumber = "REG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        System.out.println("\n=== Registration Successful ===");
        System.out.println("Registration Number: " + registrationNumber);
        System.out.println("Name: " + name);
        System.out.println("Mobile: " + mobile);
        System.out.println("Age: " + age);
        System.out.println("Aadhar: " + aadhar);
        System.out.println("Address: " + address);
        System.out.println("Work: " + work);
        System.out.println("Education: " + education);

        // Save to file
        try (FileWriter writer = new FileWriter("registrations.txt", true)) {
            writer.write("Registration Number: " + registrationNumber + "\n");
            writer.write("Name: " + name + "\n");
            writer.write("Mobile: " + mobile + "\n");
            writer.write("Age: " + age + "\n");
            writer.write("Aadhar: " + aadhar + "\n");
            writer.write("Address: " + address + "\n");
            writer.write("Work: " + work + "\n");
            writer.write("Education: " + education + "\n");
            writer.write("=====================================\n");
            System.out.println("✔ User information saved successfully.");
        } catch (IOException e) {
            System.out.println("⚠ Error saving registration data: " + e.getMessage());
        }
    }

    private static void searchRegistration(Scanner scanner) {
        System.out.println("\n=== Search Registration ===");
        System.out.print("Enter Registration Number: ");
        String searchReg = scanner.nextLine().trim().toUpperCase();

        if (searchReg.isEmpty()) {
            System.out.println("⚠ Registration Number cannot be empty.");
            return;
        }

        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader("registrations.txt"))) {
            String line;
            StringBuilder record = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Registration Number: ")) {
                    // Check previous record before resetting
                    if (record.length() > 0 && record.toString().contains(searchReg)) {
                        System.out.println("\n=== Registration Found ===");
                        System.out.println(record);
                        found = true;
                        break;
                    }
                    record.setLength(0);
                }
                record.append(line).append("\n");
            }

            // Check last record
            if (!found && record.toString().contains(searchReg)) {
                System.out.println("\n=== Registration Found ===");
                System.out.println(record);
                found = true;
            }

            if (!found) {
                System.out.println("⚠ Registration Number not found.");
            }
        } catch (IOException e) {
            System.out.println("⚠ Error reading the file: " + e.getMessage());
        }
    }

    // Helper method to safely read an integer
    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter a valid integer: ");
            }
        }
    }
}
