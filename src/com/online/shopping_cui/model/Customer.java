package com.online.shopping_cui.model;

import com.online.shopping_cui.utilities.Utilities;

/**
 * This class holds information about a Customer. It is an extension of User
 * class. Behaviours include getters and setters that uses externally-sourced
 * validation methods (from Utilities class).
 *
 * <p>
 * Attributes:</p>
 * <ul>
 * <li>Customer's Name</li>
 * <li>Phone Number</li>
 * <li>E-mail Address</li>
 * <li>Shipping/Billing Address</li>
 * <li>Card Number</li>
 * <li>Card Holder</li>
 * </ul>
 * Behaviours:
 * <ul>
 * <li>2-Parameter Constructor</li>
 * <li>8-Parameter Constructor</li>
 * <li>To String Method</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.03
 * @since 30/03/2021
 *
 */
public class Customer extends User {

    private String name;
    private String phone;
    private String email;
    private String address;
    private String cardNumber;
    private String cardHolder;

    /**
     * 2-parameter constructor for Customer class. User must have at least a 
     * loginID and password to sign up.
     *
     * @param loginID : User's login identifier.
     * @param password : User-defined password.
     *
     */
    public Customer(String loginID, String password) {
        super(loginID);
        this.setPassword(password);
        this.setName("");
        this.setPhone("");
        this.setEmail("");
        this.setAddress("");
        this.setCardNumber("");
        this.setCardHolder("");
    }

    /**
     * 8-parameter constructor for Customer class. This constructor accounts for
     * essential and non-essential details of a shopper. Calls relevant set
     * methods to perform checks (for data validity), if data passes checks, it
     * is then stored into the Object's attribute.
     *
     * @param loginID : User's login identifier.
     * @param password : User-defined password.
     * @param name : Customer's name.
     * @param phone : Contact phone number.
     * @param email : User's email address.
     * @param address : Shipping/billing address.
     * @param cardNumber : Card number.
     * @param cardHolder : Card holder's name.
     *
     */
    public Customer(String loginID, String password, String name, String phone,
            String email, String address, String cardNumber, String cardHolder) {
        super(loginID);
        this.setPassword(password);
        this.setName(name);
        this.setPhone(phone);
        this.setEmail(email);
        this.setAddress(address);
        this.setCardNumber(cardNumber);
        this.setCardHolder(cardHolder);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name.isEmpty() ? "UNKNOWN" : name);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = (phone.isEmpty() ? "UNKNOWN" : phone);
    }

    public String getEmail() {
        return email;
    }

    public boolean setEmail(String email){
        boolean set = false;
        if (email.equalsIgnoreCase("UNKNOWN")) { // Checks if passed in data is "UNKNOWN"...
            this.email = "UNKNOWN";
            set = true;
        } else if (!(email.isEmpty() || email.equals(" "))) { // Checks if passed in data is not empty...
            if (Utilities.emailIsValid(email)) { // If passed in email passes check...
                this.email = email; // Assign passed in data to instance's attribute.
                set = true;
            } else { // If email in an invalid format...
                set = false; // Do not save email and reprompt user to enter a valid email/blank one.
            }
        } else { // If its blank...
            this.email = "UNKNOWN"; // Set email to unknown.
            set = true;
        }
        
        return set;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address.isEmpty() ? "UNKNOWN" : address);
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public boolean setCardNumber(String cardNumber){
        boolean set = false;
        if (cardNumber.equalsIgnoreCase("UNKNOWN")) { // Checks if passed in data is "UNKNOWN"...
            this.cardNumber = "UNKNOWN";
            set = true;
        } else if (!(cardNumber.isEmpty() || cardNumber.equals(" "))) { // Checks if passed in data is not empty...
            if (Utilities.cardIsValid(cardNumber.trim())) { // If passed in card number passes check...
                this.cardNumber = cardNumber.trim(); // Assign passed in data to instance's attribute.
                set = true;
            } else {
                set = false; // Return false to caller
            }
        } else {
            this.cardNumber = "UNKNOWN";
            set = true;
        }
        
        return set;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = (cardHolder.isEmpty() ? "UNKNOWN" : cardHolder);
    }

    /**
     * Overridden method from superclass (User). Users that are Customers are
     * required to have a password length of at least 8 characters.
     *
     * <p>
     * In addition, the password must also meet general password requirements
     * (At least: 1 Uppercase, 1 Lowercase, 1 Number, and 1 Symbol).</p>
     *
     * <p>
     * Examples:</p>
     * <ul>
     * <li>Invalid Password : GeeksForGeeks</li>
     * <li>Valid Password : Geek$ForGeeks7</li>
     * </ul>
     *
     * @param password : User defined password.
     * @throws IllegalArgumentException
     *
     */
    @Override
    public boolean setPassword(String password) {
        boolean set = false;
        // Check if password length is at least 8 characters and if its secure...
        if (Utilities.passIsSecure(password, password.length())) {
            this.password = password; // Saves user-defined password.
            set = true;
        } else {
            set = false;
        }
        
        return set;
    }

    /**
     * To String method to serialise object. String files Object's attributes,
     * which are separated by commas.
     *
     * @return A String containing the User's type, Customer's login credentials
     * and details, all separated by commas.
     *
     */
    @Override
    public String toString() {
        String comma = ",";
        String details = "";

        // Customer's login credentials and details.
        details += this.getLoginID() + comma;
        details += Utilities.encrypt(this.getPassword()) + comma; // Encrypted version of User's password.
        details += this.getName() + comma;
        details += this.getPhone() + comma;
        details += this.getEmail() + comma;
        details += this.getAddress().replaceAll(",", ";") + comma; // Replaces the commas in address to semi-colon to retain csv format.
        details += Utilities.encrypt(this.getCardNumber()) + comma; // Encrypted version of User's card number.
        details += this.getCardHolder();
        details += "\n";

        return details;
    }
}
