package online_shopping_cui;

/**
 * This class handles the customer actions.
 * It inherit User Class.
 *
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 30/03/2021
 **/

public class Customer extends User{
    private String customerName;
    private String address;
    private int phone;
    private String email;
    private int cardNumber;
    private int cardExpDate;
    private String cardType;
    private String cardName;

    /**
     * 1-parameter constructor for User class. Subclasses of User must
     * call super() and pass in the loginID to initialise their account.
     *
     * <p>Newly created accounts have their state set to Active.</p>
     *
     * @param loginID : user's login id.
     **/
    public Customer(String loginID) {
        super(loginID);
    }

    public Customer(String loginID, String customerName, String address, int phone, String email, int cardNumber, int cardExpDate, String cardType, String cardName) {
        super(loginID);
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.cardNumber = cardNumber;
        this.cardExpDate = cardExpDate;
        this.cardType = cardType;
        this.cardName = cardName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getCardExpDate() {
        return cardExpDate;
    }

    public void setCardExpDate(int cardExpDate) {
        this.cardExpDate = cardExpDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Override
    public boolean setPassword(String password) {
        return false;
    }
}
