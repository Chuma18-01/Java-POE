import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp(){
        Message.resetAll();
    }

    //==================
    // PART 2 TESTS
    //==================


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

    //=============
    // PART 3 TESTS
    //=============

    private void populateTestData(){
        Message m1 = new Message("+27834557896", "Did you get the cake?", 1);
        m1.sentMessage(1); // Sent

        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        m2.sentMessage(3); //Stored

        Message m3 = new Message("+27834484567", "Yohooo, I am at your gate.", 3);
        m3.sentMessage(2); //Disregard

        Message m4 = new Message("0838884567", "It is dinner time !", 4);
        m4.sentMessage(1); // Sent

        Message m5 = new Message("+27838884567", "Ok, I am leaving without you.", 5);
        m5.sentMessage(3); // Stored
    }
    // Test sent messages array is correctly populated
    @Test
    public void testSentMessagesArrayContainsExpectedMessages(){
        populateTestData();
        ArrayList<String> sent = Message.getSentMessages();
        assertTrue(sent.contains("Did you get the cake?"));
        assertTrue(sent.contains("It is dinner time !"));
    }

    @Test
    public void testSentMessagesArraySize(){
        populateTestData();
        assertEquals(2, Message.getSentMessages().size());
    }
    // Test disregarded messages array
    @Test
    public void testDisregardedMessagesArray(){
        populateTestData();
        assertTrue(Message.getDisregardedMessages().contains("Yohooo, I am at your gate."));
    }
    // Test display longest message
    @Test
    public void testDisplayLongestMessage(){
        populateTestData();
        String result = Message.displayLongestMessage();
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
    }
    // Test search by message id - found
    @Test
    public void testSearchByMessageIDFound(){
        Message m4 = new Message("0838884567", "It is dinner time !", 4);
        m4.sentMessage(3);
        ArrayList<Message> stored = Message.loadStoredMessages();
        assertFalse(stored.isEmpty());
    }
    // Test search by message ID not found
    @Test
    public void testSearchByMessageIDNotFound(){
        String result = Message.searchByMessageID("0000000000");
        assertTrue(result.contains("not found"));
    }
    // Test search by recipient found
    @Test
    public void testSearchByRecipientFound(){
        populateTestData();
        String result = Message.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("Ok, I am leaving without you."));
    }
    // Test search by recipient - not found
    @Test
    public void testSearchByRecipientNotFound(){
        String result = Message.searchByRecipient("+27000000000");
        assertTrue(result.contains("No messages found"));
    }
    // Test delete by hash success
    @Test
    public void testDeleteMessageByHashSuccess(){
        Message m2 = new Message("+27838884567", "Where are you? You are late! I have asked you to be on time.", 2);
        m2.sentMessage(3);
        String hash = m2.createMessageHash();
        String result = Message.deleteMessageByHash(hash);
        assertTrue(result.contains("successfully deleted"));
    }
    // Test delete by hash not found
    @Test
    public void testDeleteMessageByHashNotFound(){
        String result = Message.deleteMessageByHash("XX:9:FAKEHASH");
        assertTrue(result.contains("not found"));
    }
    // Test display report
    @Test
    public void testDisplayReportContainsDetails(){
        populateTestData();
        String report = Message.displayReport();
        assertTrue(report.contains("Message Hash"));
        assertTrue(report.contains("Recipient"));
        assertTrue(report.contains("Message"));
        assertTrue(report.contains("+27838884567"));
    }
}
