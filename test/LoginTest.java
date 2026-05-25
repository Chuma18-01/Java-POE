import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginTest {

    private Login login;

    // Runs before each test
    @BeforeEach
    public void setUp() {
        login = new Login("Kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "May");
    }

    // =======================
    // USERNAME TESTS
    // =======================

    @Test
    public void testValidUsername() {
        assertTrue(login.checkUserName());
    }

    @Test
    public void testInvalidUsername() {
        Login invalidLogin = new Login("Kylie!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "May");
        assertFalse(invalidLogin.checkUserName());
    }

    // =======================
    // PASSWORD TESTS
    // =======================

    @Test
    public void testValidPassword() {
        assertTrue(login.checkPasswordComplexity());
    }

    @Test
    public void testPasswordNoUpperCase() {
        Login test = new Login("Ky1_1", "password1!", "+27838968976", "Kyle", "May");
        assertFalse(test.checkPasswordComplexity());
    }

    @Test
    public void testPasswordNoNumber() {
        Login test = new Login("Ky1_1", "Password!", "+27838968976", "Kyle", "May");
        assertFalse(test.checkPasswordComplexity());
    }

    @Test
    public void testPasswordTooShort() {
        Login test = new Login("Kyl_1", "P@ss1", "+27838968976", "Kyle", "May");
        assertFalse(test.checkPasswordComplexity());
    }

    // =======================
    // CELLPHONE TESTS
    // =======================

    @Test
    public void testValidCellphoneInternational() {
        assertTrue(login.checkCellPhoneNumber());
    }

    @Test
    public void testValidCellphoneLocal() {
        Login test = new Login("Kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "May");
        assertTrue(test.checkCellPhoneNumber());
    }

    @Test
    public void testInvalidCellphone() {
        Login test = new Login("Kyl-1", "Ch&&sec@ke99!", "08966553", "Kyle", "May");
        assertFalse(test.checkCellPhoneNumber());
    }

    // =======================
    // REGISTER USER TESTS
    // =======================

    @Test
    public void testRegisterUserSuccess() {
        assertEquals("User registered successfully.", login.registerUser());
    }

    @Test
    public void testRegisterUserInvalidUsername() {
        Login test = new Login("Kyle!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "May");
        assertEquals("Username is incorrectly formatted.", test.registerUser());
    }

    @Test
    public void testRegisterUserInvalidPassword() {
        Login test = new Login("Kyl_1", "pass", "+27838968976", "Kyle", "May");
        assertEquals("Password does not meet complexity requirements.", test.registerUser());
    }

    @Test
    public void testRegisterUserInvalidCellphone() {
        Login test = new Login("Kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "May");
        assertEquals("Cell phone number is incorrectly formatted", test.registerUser());
    }

    // =======================
    // LOGIN USER TESTS
    // =======================

    @Test
    public void testLoginSuccess() {
        assertTrue(login.loginUser("Kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFail() {
        assertFalse(login.loginUser("wrong", "wrong"));
    }

    // =======================
    // LOGIN STATUS TESTS
    // =======================

    @Test
    public void testReturnLoginStatusSuccess() {
        String message = login.returnLoginStatus(true);
        assertTrue(message.contains("Welcome"));
    }

    @Test
    public void testReturnLoginStatusFail() {
        String message = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", message);
    }
}