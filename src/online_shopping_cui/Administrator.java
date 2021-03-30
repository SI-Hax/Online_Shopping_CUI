package online_shopping_cui;

/**
 * This class handles the Administrator actions.
 *
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 30/03/2021
 **/

public class Administrator extends User{
    private String adminName;
    private String adminEmail;

    /**
     * 1-parameter constructor for User class. Subclasses of User must
     * call super() and pass in the loginID to initialise their account.
     *
     * <p>Newly created accounts have their state set to Active.</p>
     *
     * @param loginID : user's login id.
     **/
    public Administrator(String loginID) {
        super(loginID);
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public void addProduct() {
        // TODO
    }

    public void removeProduct(int productID) {
        // TODO
    }

    public void editProduct() {
        // TODO
    }

    @Override
    public boolean setPassword(String password) {
        return false;
    }
}
