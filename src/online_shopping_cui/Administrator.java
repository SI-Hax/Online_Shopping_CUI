package online_shopping_cui;

/**
 * This class holds information about an Administrator of the system.
 * It is an extension of User class. Behaviours include getters and
 * setters that uses externally-sourced validation methods
 * (from Utilities class).
 *
 * <p>Attributes:</p>
 * <ul>
 *  <li>Administrator's Name</li>
 *  <li>E-mail Address</li>
 * </ul>
 * <p>
 * Behaviours:
 * <ul>
 *  <li>2-Parameter Constructor</li>
 *  <li>4-Parameter Constructor</li>
 *  <li>Getters and Setters</li>
 *  <li>To String Method</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.02
 * @since 30/03/2021
 **/
public class Administrator extends User {
    //Veriables----------------------------------------------------------------
    private String adminName;
    private String adminEmail;

    /**
     * 2-parameter constructor for Administrator class. This is a
     * constructor for Administrator class. Admins must have at least
     * a loginID and a password. The rest will be computer-assigned.
     *
     * @param loginID    : Login identifier for admins.
     * @param password   : Password defined by the admin.
     **/
    public Administrator(String loginID, String password) {
        super(loginID);
        this.setPassword(password);
        this.setAdminName("Admin");
        this.setAdminEmail("");
    }
    
    //Constructors-------------------------------------------------------------
    /**
     * 4-parameter constructor for Administrator class. This is a
     * constructor for Administrator class. Admins can have a loginID,
     * password, name, and email.
     *
     * @param loginID    : Login identifier for admins.
     * @param password   : Password defined by the admin.
     * @param adminName  : Name of the administrator.
     * @param adminEmail : Email address of the admin.
     **/
    public Administrator(String loginID, String password, String adminName, String adminEmail) throws IllegalArgumentException {
        super(loginID);
        this.setPassword(password);
        this.setAdminName(adminName);
        this.setAdminEmail(adminEmail);
    }

    // Getters and Setters-----------------------------------------------------
    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = (adminName.isEmpty() ? "UNKNOWN" : adminName);
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) throws IllegalArgumentException {
        if(adminEmail.equalsIgnoreCase("UNKNOWN")) { // Checks if passed in data is "UNKNOWN"...
            this.adminEmail = "UNKNOWN";
        } else if (!(adminEmail.isEmpty() || adminEmail.equals(" "))) { // Checks if passed in data is not empty...
            if (Utilities.emailIsValid(adminEmail)) { // If passed in email passes check...
                this.adminEmail = adminEmail; // Assign passed in data to instance's attribute.
            } else {
                throw new IllegalArgumentException("Invalid email"); // Throw exception if does not satisfy pattern.
            }
        } else {
            this.adminEmail = "UNKNOWN";
        }
    }

    /**
     * Overridden method from superclass (User). Users that are Administrators
     * are required to have a password length of at least 14 characters.
     *
     * <p>In addition, the password must also meet general password requirements
     * (At least: 1 Uppercase, 1 Lowercase, 1 Number, and 1 Symbol).</p>
     *
     * @param password : User defined password.
     * @throws IllegalArgumentException
     **/
    @Override
    public void setPassword(String password) throws IllegalArgumentException {
        // Check if password length is at least 14 characters and if its secure...
        if (password.length() >= 14 && Utilities.passIsSecure(password)) {
            this.password = password; // Saves user-defined password.
        } else {
            throw new IllegalArgumentException("Weak password."); // Throw an exception.
        }
    }

    /**
     * To String method to serialise object. Stringifies Object's
     * attributes, which are separated by commas.
     *
     * @return A String containing User type, Admin's login
     *          credentials and details, all separated by commas.
     **/
    @Override
    public String toString() {
        String comma = ",";
        String details = "";

        // Admins login credentials and details.
        details += this.getLoginID() + comma;
        details += Utilities.encrypt(this.getPassword()) + comma;
        details += this.getAdminName() + comma;
        details += this.getAdminEmail() + comma;
        details += "\n";

        return details;
    }
}
