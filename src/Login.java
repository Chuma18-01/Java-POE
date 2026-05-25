import java.util.Scanner;

public class Login {
    private final String username;
    private final String password;
    private final String cellphone;
    private final String firstName;
    private final String lastName;

    Scanner scanner = new Scanner(System.in);

    //Constructor
    public Login(String username, String password, String cellphone, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Check Username
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Check password complexity
    public boolean checkPasswordComplexity() {
        boolean hasUpperCase = !password.equals(password.toLowerCase());
        boolean hasNumber = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=]");
        boolean hasLength = password.length() >= 8;
        return hasUpperCase && hasNumber && hasSpecial && hasLength;

    }

    // Check cellphone number
    public boolean checkCellPhoneNumber() {
        return cellphone.matches("\\+27\\d{9}") || cellphone.matches("0\\d{9}");
    }

    //Register User
    public String registerUser() {
        if (!checkUserName()) {
            return "Username is incorrectly formatted.";
        }
        if (!checkPasswordComplexity()) {
            return "Password does not meet complexity requirements.";
        }
        if (!checkCellPhoneNumber()) {
            return "Cell phone number is incorrectly formatted";
        }
        return "User registered successfully.";
    }

    // Login User
    public boolean loginUser(String enteredUsername, String enteredPassword) {
        return enteredUsername.equals(username) && enteredPassword.equals(password);
    }

    // Return login status
    public String returnLoginStatus(boolean loginSuccess) {
        if (loginSuccess) {
            return "Welcome" + firstName + "," + lastName + "it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
