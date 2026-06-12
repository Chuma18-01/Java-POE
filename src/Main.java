import java.util.Scanner;

public class Main{
    void main() {

        Scanner scanner = new Scanner(System.in);

        Registration registration = new Registration();

        String username;
        String password;
        String cellphone;
        String firstName;
        String lastName;

        System.out.println("Please enter first name:");
        firstName = scanner.nextLine();

        System.out.println("Please enter last name:");
        lastName = scanner.nextLine();

        //Username loop
        do {
            System.out.println("Enter username: ");
            username = scanner.nextLine();
        } while (!registration.checkUserName(username));

        // Password loop
        do {
            System.out.println("Enter password: ");
            password = scanner.nextLine();
        } while (!registration.checkPasswordComplexity(password));

        //cellphone loop
        do {
            System.out.println("Enter SA cellphone (+27...): ");
            cellphone = scanner.nextLine();
        } while (!registration.checkCellPhoneNumber(cellphone));


        System.out.println("\nRegistration successful");

        // Create Login object and pass all 5 values to construct
        Login user = new Login(username, password, cellphone, firstName, lastName);

        //methods form login.java
        String registrationMessage = user.registerUser();
        System.out.println(registrationMessage);

        if (registrationMessage.equals("User registered successfully.")) {
            System.out.println("'\n === LOGIN ===");
            System.out.println("Enter username to login:");
            String loginUser = scanner.nextLine();

            System.out.println("Enter password to login: ");
            String loginPass = scanner.nextLine();

            //Call loginUser() method
            boolean success = user.loginUser(loginUser, loginPass);

            // call returnLoginStatus() method
            System.out.println(user.returnLoginStatus(success));
        }

        // Existing login system

        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to QuickChat");

        int choice;

        do {
            System.out.println("\nChoose an option:");
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages ");
            System.out.println("3) Quit ");

            choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("How many messages do you want to send? ");
                    int totalMessages = input.nextInt();
                    input.nextLine();

                    for (int i = 0; i < totalMessages; i++){

                        System.out.println("\nMessage " + i + 1);

                        System.out.println("Enter recipient number: ");
                        String recipient = input.nextLine();

                        System.out.println("Enter your message: ");
                        String text = input.nextLine();

                        Message msg = new Message(recipient, text, i + 1);

                        System.out.println(msg.checkRecipientCell());
                        System.out.println(msg.checkMessageLength());

                        if (msg.checkRecipientCell().contains("successfully")
                                && msg.checkMessageLength().contains("ready")){

                            System.out.println("Message Hash: " + msg.createMessageHash());

                            System.out.println("\nChoose: ");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message");

                            int option = input.nextInt();
                            input.nextLine();

                            System.out.println(msg.sentMessage(option));
                        }
                    }
                    System.out.println("\nTotal messages sent: "
                               + Message.returnTotalMessages());

                    break;

                case 2:
                    System.out.println("Coming Soon");
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option");
            }
        } while (choice != 3);

    }
        }

