import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MessageTest {

    // Test message length success

    @Test
    public void testMessageLengthSuccess(){

        Message msg = new Message("+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                  1);

        String expected = "Message ready to send";
        assertEquals(expected, msg.checkMessageLength());
    }
    //Test message for failure
    @Test
    public void testMessageLengthFailure(){

        String longMessage = "A".repeat(260);

        Message msg = new Message("+27718693002",
                         longMessage, 1);

        String expected = "Message exceeds 250 characters by 10, please reduce size.";
        assertEquals(expected, msg.checkMessageLength());
    }
    // Test recipient number success
    @Test
    public void testRecipientSuccess(){

        Message msg = new Message("+27718963002",
                       "Hello", 1);

        String expected = "Cellphone number successfully captured.";
        assertEquals(expected, msg.checkRecipientCell());
    }
    // Test recipient number failure
    @Test
    public void testRecipientFailure() {


        Message msg = new Message("08123456789",
                "Hello", 1);

        String expected =
                "Cellphone number is incorrectly formatted or does not contain international code.";
        assertEquals(expected, msg.checkRecipientCell());
    }
    //  Test message ID length
    @Test
    public void testMessageHash(){

        Message msg = new Message("+27718963002",
                               "Hi Mike, would you like to join us for dinner tonight? ",
                                 1);

        String hash = msg.createMessageHash();
        assertTrue(hash.contains("HI"));
        assertTrue(hash.contains("TONIGHT?"));
    }
    // Test sent message
    @Test
    public void testSentMessage(){

        Message msg = new Message("+27718963002",
                "Hello", 1);

        String expected = "Message successfully sent.";
        assertEquals(expected, msg.sentMessage(1));
    }
    // Test stored message
    @Test
    public void testStoredMessage(){

        Message msg = new Message("+27718963002",
                      "Hello", 1);

        String expected = "Message successfully stored.";
        assertEquals(expected, msg.sentMessage(3));
    }
    // Test disregard message
    @Test
    public void testDisregardMessage(){

        Message msg = new Message("+27718963002",
                    "Hello", 1);

        String expected = "Press 0 to delete the message.";
        assertEquals(expected, msg.sentMessage(2));
    }
}
