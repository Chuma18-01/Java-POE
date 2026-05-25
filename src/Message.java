import java.util.Random;

public class Message {

    private String recipient;
    private String message;
    private String messageID;
    private int messageNumber;

    private static int totalMessages = 0;

    //Constructor
    public Message(String recipient, String message, int messageNumber) {

        this.recipient = recipient;
        this.message = message;
        this.messageNumber = messageNumber;
        this.messageID = generateMessageID();
    }
    // Generate random 10-digit ID
    private String generateMessageID(){
         Random random = new Random();
         long number = 1000000000L + (long)(random.nextDouble() * 9000000000L);

         return String.valueOf(number);
    }
    //Check message ID
    public boolean checkMessageID(){

        return messageID.length() <= 10;
    }
    // Validate recipient number
    public String checkRecipientCell(){
         if (recipient.length() <= 12 && recipient.startsWith("+27")){
             return "Cellphone number successfully captured.";

         } else {

             return "Cellphone number is incorrectly formatted or does not contain international code.";
         }
    }
    // Validate message length
    public String checkMessageLength(){

        if (message.length() <= 250){
            return "Message ready to send";

        } else {

            int extra = message.length() - 250;

            return "Message exceeds 250 characters by " + extra + ", please reduce size.";
        }
    }
    //Create message hash
    public String createMessageHash(){
         String[] words = message.split(" ");
         String firstWord = words[0].toUpperCase();
         String lastWord = words[words.length - 1].toUpperCase();

         return messageID.substring(0, 2)
                 + ":" + messageNumber
                 + ":" + firstWord + lastWord;
    }
    // Send/ Store/ Disregard
    public String sentMessage(int choice){

        switch(choice){
            case 1:
                totalMessages++;
                return "Message successfully sent.";

            case 2:
                return "Press 0 to delete the message.";

            case 3:
                return "Message successfully stored.";

            default:
                return "Invalid option";
        }
    }
    // Return total messages
    public static int returnTotalMessages(){

        return totalMessages;
    }
    // Print message details
    public String printMessages(){

        return "\nMessage ID: " + messageID
                + "\nMessage hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }
}



