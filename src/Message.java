import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class Message {

    private String recipient;
    private String message;
    private String messageID;
    private int messageNumber;

    private static int totalMessages = 0;

    // Part 3 Arrays
    private static ArrayList<String> sentMessages = new ArrayList<>();
    private static ArrayList<String> disregardedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();

    private static final String JSON_FILE = "stored_messages.json";

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
        String hash = createMessageHash();

        switch(choice){
            case 1:
                totalMessages++;
                sentMessages.add(message);
                messageHashes.add(hash);
                messageIDs.add(messageID);
                return "Message successfully sent.";

            case 2:
                disregardedMessages.add(message);
                return "Press 0 to delete the message.";

            case 3:
                saveMessageToJSON();
                messageHashes.add(hash);
                messageIDs.add(messageID);
                return "Message successfully stored.";

            default:
                return "Invalid option";
        }
    }
    // Save stored message to JSON FILE
    private void saveMessageToJSON(){
        JSONArray jsonArray = loadJSONArray();
        JSONObject obj = new JSONObject();
        obj.put("messageID", messageID);
        obj.put("recipient", recipient);
        obj.put("message", message);
        obj.put("hash", createMessageHash());
        jsonArray.put(obj);

        try (FileWriter fw = new FileWriter(JSON_FILE)){
            fw.write(jsonArray.toString(2));
        } catch (IOException e){
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
    // Load JSON array from file
    private static JSONArray loadJSONArray(){
        try{
            if (Files.exists(Paths.get(JSON_FILE))){
                String content = new String(Files.readAllBytes(Paths.get(JSON_FILE)));
                return new JSONArray(content);
            }
        } catch (Exception e) {
            System.out.println("Error reading JSON: " + e.getMessage());
        }
        return new JSONArray();
    }
    // Load stored messages from JSON into a list of Message objects
    public static ArrayList<Message> loadStoredMessages(){
        ArrayList<Message> stored = new ArrayList<>();
        JSONArray jsonArray = loadJSONArray();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            Message m = new Message(obj.getString("recipient"), obj.getString("message"), i + 1);
            stored.add(m);
        }
        return stored;
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
    //PART 3 - STORED MESSAGES FEATURES

    //a. Display sender and recipient of all stored messages
    public static String displayStoredSendersAndRecipients(){
        ArrayList<Message> stored = loadStoredMessages();
        if (stored.isEmpty()) return "No stored messages found.";
        StringBuilder sb = new StringBuilder();
        for (Message m : stored){
            sb.append("Recipient: ").append(m.recipient)
                    .append(" | Message: ").append(m.message).append("\n");
        }
        return sb.toString().trim();
    }
    // b. Display longest stored message
    public static String displayLongestMessage(){
        ArrayList<Message> stored = loadStoredMessages();
        ArrayList<String> all = new ArrayList<>(sentMessages);
        for (Message m : stored) all.add(m.message);

        if (all.isEmpty()) return "No messages found.";
        String longest = all.get(0);
        for (String msg : all){
            if (msg.length() > longest.length()) longest = msg;
        }
        return "Longest message: " + longest;
    }
    // c. Search by message ID
    public static String searchByMessageID(String id){
        ArrayList<Message> stored = loadStoredMessages();
        for (Message m : stored){
            if (m.messageID.equals(id)){
                return "Recipient:" + m.recipient + "\nMessage: " + m.message;
            }
        }
        return "Message ID \"" + id + "\" not found.";
    }
    // d. Search all messages for a particular recipient
    public static String searchByRecipient(String recipientNum){
        ArrayList<Message> stored = loadStoredMessages();
        StringBuilder sb = new StringBuilder();
        for (Message m : stored){
            if (m.recipient.equals(recipientNum)){
                sb.append("Message: ").append(m.message).append("\n");
            }
        }
        return sb.length() > 0 ? sb.toString().trim() : "No messages found for " + recipientNum;
    }
    // e. Delete a message using message hash
    public static String deleteMessageByHash(String hash){
        JSONArray jsonArray = loadJSONArray();
        for (int i = 0; i < jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getString("hash").equals(hash)){
                String deleteMsg = obj.getString("message");
                jsonArray.remove(i);
                try (FileWriter fw = new FileWriter(JSON_FILE)){
                    fw.write(jsonArray.toString(2));
                } catch (IOException e) {
                    return "Error deleting message: " + e.getMessage();
                }
                return "Message: \"" + deleteMsg + "\" successfully deleted.";
            }
        }
        return "Hash \"" + hash +"\" not found";
    }
    // f. Display full report of all stored messages
    public static String displayReport(){
        ArrayList<Message> stored = loadStoredMessages();
        if (stored.isEmpty()) return "No stored messages to report.";
        StringBuilder sb = new StringBuilder("===STORED MESSAGES REPORT===\n");
        for (Message m : stored){
            sb.append("\nMessage Hash: ").append(m.createMessageHash())
                    .append("\nRecipient: ").append(m.recipient)
                    .append("\nMessage: ").append(m.message)
                    .append("\n------------------");
        }
        return sb.toString();
    }
    // Getters for testing
    public String getRecipient() {
        return recipient;
    }
    public String getMessage() {
        return message;
    }
    public String getMessageID() {
        return messageID;
    }
    public static ArrayList<String> getSentMessages() {
        return sentMessages;
    }
    public static ArrayList<String> getDisregardedMessages() {
        return disregardedMessages;
    }
    public static ArrayList<String> getMessageHashes(){
        return messageHashes;
    }
    public static ArrayList<String> getMessageIDs() {
        return messageIDs;
    }
        // For testing: reset static state
        public static void resetAll() {
            sentMessages.clear();
            disregardedMessages.clear();
            messageHashes.clear();
            messageIDs.clear();
            totalMessages = 0;
            try {
                Files.deleteIfExists(Paths.get(JSON_FILE));
            } catch (IOException ignored) {

            }
        }

    }



