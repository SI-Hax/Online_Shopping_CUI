package online_shopping_cui;

/**
 * This class holds information about a Customer.
 * It is an extension of User class. Behaviours include
 * getters and setters that uses externally-sourced 
 * validation methods (from Utilities class).
 * 
 * <p>Attributes:</p>
 * <ul>
 *  <li>Customer's Name</li>
 *  <li>Phone Number</li>
 *  <li>E-mail Address</li>
 *  <li>Shipping/Billing Address</li>
 *  <li>Card Number</li>
 *  <li>Card Holder</li>
 * </ul>
 * 
 * Behaviours:
 * <ul>
 *  <li>2-Parameter Constructor</li>
 *  <li>8-Parameter Constructor</li>
 *  <li>Getters and Setters</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.01
 * @since 30/03/2021
 **/
public class Customer extends User 
{
    private String name;
    private String phone;
    private String email;
    private String address;
    private String cardNumber;
    private String cardHolder;

    /**
     * 2-parameter constructor for Customer class. This is the most basic
     * constructor. User must have at least a loginID and password to sign up.
     *
     * @param loginID : User's login identifier.
     * @param password : User-defined password.
     **/
    public Customer(String loginID, String password) {
        super(loginID);
        this.setPassword(password);
    }
    
    /**
     * 8-parameter constructor for Customer class. This constructor
     * accounts for essential and non-essential details of a shopper.
     * Calls relevant set methods to perform checks (for data validity),
     * if data passes checks, it is then stored into the Object's attribute.
     * 
     * @param loginID : User's login identifier.
     * @param password : User-defined password.
     * @param name : Customer's name.
     * @param phone : Contact phone number.
     * @param email : User's email address.
     * @param address : Shipping/billing address.
     * @param cardNumber : Card number.
     * @param cardHolder : Card holder's name.
     **/
    public Customer(String loginID, String password, String name, String phone, 
                    String email, String address, String cardNumber, String cardHolder) 
    {
        super(loginID);
        this.setPassword(password);
        this.setName(name);
        this.setPhone(phone);
        this.setEmail(email);
        this.setAddress(address);
        this.setCardNumber(cardNumber);
        this.setCardHolder(cardHolder);
    }

    // Getters and setter methods for Object's instance data.
    //-------------------------------------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = (name.isEmpty()? "UNKNOWN":name);
    }
    //-------------------------------------------------------
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = (phone.isEmpty()? "UNKNOWN":phone);
    }
    //-------------------------------------------------------
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws IllegalArgumentException {
        if(!(email.isEmpty() || email.equals(" "))) { // Checks if passed in data is not empty...
            if(Utilities.emailIsValid(email)) { // If passed in email passes check...
                this.email = email; // Assign passed in data to instance's attribute.
            } else {
                throw new IllegalArgumentException("Invalid email"); // Throw exception if does not satisfy pattern.
            }
        } else {
            this.email = "UNKNOWN"; 
        }
    }
    //-------------------------------------------------------
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = (address.isEmpty()? "UNKNOWN":address);
    }
    //-------------------------------------------------------
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) throws IllegalArgumentException {
        if(!(cardNumber.isEmpty() || cardNumber.equals(" "))) { // Checks if passed in data is not empty...
            if(Utilities.cardIsValid(cardNumber.trim())) { // If passed in card number passes check...
                this.cardNumber = cardNumber.trim(); // Assign passed in data to instance's attribute.
            } else {
                throw new IllegalArgumentException("Invalid card number"); // Throw exception if card does not pass the Luhn's algorithm.
            }
        } else {
            this.cardNumber = "UNKNOWN";
        }
    }
    //-------------------------------------------------------
    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = (cardHolder.isEmpty()? "UNKNOWN":cardHolder);
    }
    //-------------------------------------------------------
    
    /**
     * Overridden method from superclass (User). Users that are Customers
     * are required to have a password length of at least 8 characters.
     * 
     * <p>In addition, the password must also meet general password requirements   
     * (At least: 1 Uppercase, 1 Lowercase, 1 Number, and 1 Symbol).</p>
     * Input: GeeksForGeeks
     * Output: Invalid Password!
     *
     * Input: Geek$ForGeeks7
     * Output: Valid Password
     * @param password : User defined password.
     * @throws IllegalArgumentException 
     **/
    @Override
    public void setPassword(String password) throws IllegalArgumentException {
        // Check if password length is at least 8 characters and if its secure...
        if(password.length() >= 8 && Utilities.passIsSecure(password)) {
            this.password = password; // Saves user-defined password.
        } else {
            throw new IllegalArgumentException("Weak password."); // Throw an exception.
        }
    }
}
