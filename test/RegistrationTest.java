import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class RegistrationTest {

    // Create an instance of the Registration class
    Registration registration = new Registration();

    // Test for the checkUserName method
    @Test
    public void testCheckUserName_ValidUsername() {
        assertTrue(registration.checkUserName("Kyl_1"));
    }

    @Test
    public void testCheckUserName_InvalidUsername_TooLong() {
        assertFalse(registration.checkUserName("Kyle!!!!"));
    }

    @Test
    public void testCheckUserName_InvalidUsername_NoUnderscore() {
        assertFalse(registration.checkUserName("Kyle1"));
    }

    // Test for the checkPasswordComplexity method
    @Test
    public void testCheckPasswordComplexity_ValidPassword() {
        assertTrue(registration.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testCheckPasswordComplexity_InvalidPassword_TooShort() {
        assertFalse(registration.checkPasswordComplexity("P@ss1"));
    }

    @Test
    public void testCheckPasswordComplexity_InvalidPassword_MissingCapital() {
        assertFalse(registration.checkPasswordComplexity("password1"));
    }

    @Test
    public void testCheckPasswordComplexity_InvalidPassword_MissingSpecialCharacter() {
        assertFalse(registration.checkPasswordComplexity("Password123"));
    }

    // Test for the checkCellPhoneNumber method
    @Test
    public void testCheckCellPhoneNumber_ValidPhoneNumber() {
        assertTrue(registration.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_InvalidPhoneNumber_WrongPrefix() {
        assertFalse(registration.checkCellPhoneNumber("+28838968976"));
    }

    @Test
    public void testCheckCellPhoneNumber_InvalidPhoneNumber_IncorrectFormat() {
        assertFalse(registration.checkCellPhoneNumber("08966553"));
    }
}