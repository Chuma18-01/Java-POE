

public class Registration {

    // Check username
    public boolean checkUserName(String username){

     // Must contain "_" and be <= 5 characters
        if (username.contains("_") && username.length() <=5){
            System.out.println("Username successfully captured");
            return true;
        } else {
            System.out.println("Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters long in length");
            return false;
        }
    }
    // Check Password
    public boolean checkPasswordComplexity(String password){
        boolean length = password.length() >= 8;
        boolean capital = password.matches(".*[a-z].*");
        boolean number = password.matches(".*[0-9].*");
        boolean special = password.matches(".*[^a-zA-Z0-9].*");

        if (length && capital && number && special){
            System.out.println("Password successfully captured");
            return true;
        } else {
            System.out.println("Password is not correctly formatted; please ensure that your password contains at least eight characters, a capital letter, a number and a special character ");
            return false;
        }
    }
    // Check cellphone using REGEX
    public boolean checkCellPhoneNumber(String number){

        //SA format: +27 followed by 9 digits
      String regex = "^\\+27\\d{9}$";

      if (number.matches(regex)){
          System.out.println("Cell phone number successfully added");
          return true;
      } else {
          System.out.println("Cell phone number incorrectly formatted or does not contain international code");
          return false;
      }
    }
}
