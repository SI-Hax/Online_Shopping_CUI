package online_shopping_cui;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class maintains a set of tools (static methods)
 * paramount to data validation and data protection.
 *
 * <p>Behaviours:</p>
 * <ul>
 *   <li>Password Requirements Enforcer</li>
 *   <li>E-mail Format Checker</li>
 *   <li>Card Number Validator</li>
 *   <li>Text Encryptor</li>
 *   <li>CipherText Decryptor</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 30/03/2021
 **/
public class Utilities {
    // Data format validators, promoting the security/validity of data.
    //#################################################################################################

    /**
     * Static method with an algorithm to check if the passed in
     * data satisfies password requirements (at least one uppercase,
     * a lowercase, a number, and a special character).
     *
     * @param password : String containing the password to be checked.
     * @return T/F whether the passed in String is considered secure.
     **/
    public static boolean passIsSecure(String password) {
        boolean upperCase = false;
        boolean lowerCase = false;
        boolean number = false;
        boolean specialChar = false;
        boolean secure = false;

        // For loop to iterate through the String.
        for (int i = 0; i < password.length(); i++) {
            char temp = password.charAt(i);

            if (Character.isUpperCase(temp)) { // Check for upper case...
                upperCase = true;
            } else if (Character.isLowerCase(temp)) { // Check for lower case...
                lowerCase = true;
            } else if (Character.isDigit(temp)) { // Check for number...
                number = true;
            } else if ((temp >= 33 && temp <= 47) || (temp >= 58 && temp <= 64) ||
                    (temp >= 91 && temp <= 96) || (temp >= 123 && temp <= 126)) { // Check for special characters (symbols only).
                specialChar = true;
            }
        }

        if (upperCase && lowerCase && number && specialChar) { // If all checks are true....
            secure = true; // Password is secure.
        }

        return secure;
    }

    /**
     * Static method to check if the passed String is in email format (e.g "_____@___.__._").
     *
     * <p>Reference:</p>
     * https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
     *
     * @param email : String containing the email to be checked.
     * @return T/F whether the String is in an email format.
     **/
    public static boolean emailIsValid(String email) {
        Pattern pattern = Pattern.compile("^.+@.+\\..+$"); // Pattern for email.
        Matcher matcher = pattern.matcher(email); // Check if passed in data matches pattern.

        // Return format match state.
        return matcher.matches();
    }

    /**
     * Static method to determine if passed in String is a valid card
     * number. Uses Luhn's algorithm to evaluate a card's validity.
     *
     * <p> Reference:</p>
     * https://en.wikipedia.org/wiki/Luhn_algorithm
     *
     * @param cardNumber : String containing the card number to be checked.
     * @return T/F whether the card number is valid or not.
     **/
    public static boolean cardIsValid(String cardNumber) {
        int numDigits = cardNumber.length();
        int sum = Character.getNumericValue(cardNumber.charAt(numDigits - 1)); // Start from the right-most digit... 
        int parity = (numDigits % 2); // To determine if total digits on card number is odd/even. 

        // For loop to iterate through the card number, operates from 
        // the second number from the right, then progress to the left-most digit.
        for (int i = numDigits - 2; i >= 0; i--) {
            int digit = Character.getNumericValue(cardNumber.charAt(i));

            if (i % 2 == parity) { // Check for every second digit...
                digit *= 2; // Doubles the second-placed-digits.

                if (digit > 9) { // If after doubling, digit became two-digits...
                    digit -= 9; // Subtract 9 to get the sum of the two digits.
                }
            }

            sum += digit; // Add the numbers together.
        }

        // If sum modulo 10 is 0, then cardNumber is valid(T), otherwise invalid(F).
        return (sum % 10 == 0);
    }
    //#################################################################################################

    // Cryptographic Methods for Encryption and Decryption, protecting data. 
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

    /**
     * Static method that encrypts a String. Uses a symmetric-key encryption
     * algorithm called the Advanced Encryption Standard (AES) operating
     * in Electronic Code Book (ECB) mode.
     *
     * <p>References:</p>
     * <ul>
     *   <li>https://stackoverflow.com/questions/28025742/encrypt-and-decrypt-a-string-with-aes-128</li>
     *   <li>https://www.baeldung.com/java-aes-encryption-decryption</li>
     * </ul>
     *
     * @param text : String containing the text to be encrypted.
     * @return A String containing the cipherText (encrypted String).
     **/
    public static String encrypt(String text) {
        String key = "f0r4n31337h4xx0r"; // 128-bit key.
        String cipherText = null;

        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES"); // Create key.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Create Cipher.

            // Encrypt the text.
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());

            // Encode cipherText to prevent data loss.
            cipherText = Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) { // Catch any encryptor initialisation errors
            System.err.println("Error initialising encryptor.");
        }

        return cipherText;
    }

    /**
     * Static method that decrypts a String. Uses a symmetric-key decryption
     * algorithm called the Advanced Encryption Standard (AES) operating
     * in Electronic Code Book (ECB) mode.
     *
     * <p>References:</p>
     * <ul>
     *   <li>https://stackoverflow.com/questions/28025742/encrypt-and-decrypt-a-string-with-aes-128</li>
     *   <li>https://www.baeldung.com/java-aes-encryption-decryption</li>
     * </ul>
     *
     * @param cipheredText : String containing the text to be decrypted.
     * @return A String containing the decrypted text (decrypted String).
     **/
    public static String decrypt(String cipheredText) {
        String key = "f0r4n31337h4xx0r"; // 128-bit key.
        String decryptedText = null;

        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES"); // Create key.
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // Create Cipher.

            // Decode then decrypt the cipheredText.
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            byte[] encrypted = Base64.getDecoder().decode(cipheredText);
            decryptedText = new String(cipher.doFinal(encrypted));
        } catch (Exception e) { // Catch any decryptor initialisation errors.
            System.err.println("Error initialising decryptor.");
        }

        return decryptedText;
    }
    //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
}
