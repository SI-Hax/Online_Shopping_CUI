package com.online.shopping_cui.driver;

import com.online.shopping_cui.enumerations.Category;
import com.online.shopping_cui.model.Customer;
import com.online.shopping_cui.model.ProductList;
import com.online.shopping_cui.model.User;
import com.online.shopping_cui.model.Administrator;
import com.online.shopping_cui.model.ShoppingCart;
import com.online.shopping_cui.model.Product;
import com.online.shopping_cui.utilities.ProductFileIO;
import com.online.shopping_cui.utilities.Utilities;
import com.online.shopping_cui.utilities.UserFileIO;
import java.util.*;

/**
 * User Interface Class - This class contains methods and attributes that allows
 * user-computer interaction.
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.08
 * @since 18/04/2021
 *
 */
public class UserInterface {
    static String ERROR = "Please choose from the options above"; 
    
    protected static Scanner scanner;
    protected ProductList products;
    protected ProductMenu productMenu;
    protected Login login;
    protected CreateAccount create;
    protected boolean mainMenuLoop, accountCreationSuccess, loginSuccess;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.products = ProductFileIO.importProductData();
        this.mainMenuLoop = true;
        this.accountCreationSuccess = false;
        this.loginSuccess = false;
        this.productMenu = new ProductMenu();
        this.login = new Login();
        this.create = new CreateAccount();
    }

    /**
     * Level 1 menu.
     */
    public void mainMenu() {
        System.out.println("\n\t1. Login");
        System.out.println("\t2. Create Account");
        System.out.println("\t3. Exit");
    }

    /**
     * Level 2 menu.
     */
    public void menuSelections() {
        do {
            try {
                mainMenu();
                System.out.print("\nPlease Choose Your Option: ");
                int uAnswer = scanner.nextInt();
                scanner.nextLine();
                switch (uAnswer) {
                    case 1: // User selects login in level 1 menu...
                        do {
                            loginSuccess = loginSelection(); // Choose To Login As Administrator/Customer/Guest
                        } while (!loginSuccess);
                        mainMenuLoop = true; // Goes to the main menu.
                        break;
                    case 2: // Choose To Register As Administrator Or Customer
                        do {
                            try {
                                System.out.println("\n\t1. Create Account For Customer");
                                System.out.println("\t2. Create Account For Administrator");
                                System.out.println("\t3. Go back");
                                System.out.print("\nPlease Choose Your Option: ");
                                uAnswer = scanner.nextInt();
                                scanner.nextLine();

                                switch (uAnswer) {
                                    case 1:
                                        accountCreationSuccess = create.createCustomerAccount();
                                        break;
                                    case 2:
                                        accountCreationSuccess = create.createAdministratorAccount();
                                        break;
                                    case 3:
                                        accountCreationSuccess = true;
                                        break;
                                    default:
                                        throw new IndexOutOfBoundsException();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println(ERROR);
                            } catch (InputMismatchException e) {
                                System.err.println(ERROR);
                                scanner.nextLine();
                            } catch (IllegalArgumentException e) {
                                System.err.println(e.getMessage());
                            }
                        } while (!accountCreationSuccess);
                        mainMenuLoop = true; // Goes to the main menu.
                        break;
                    case 3:
                        System.out.println("\nGood Bye! Thank you for shopping with us =)");
                        mainMenuLoop = false; // Exits the program.
                        ProductFileIO.exportProductData(this.products); // Exports/backs-up products' data.
                        UserFileIO.exportUserData(create.users); // Exports/backs-up users' data.
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(ERROR);
            } catch (InputMismatchException e) {
                System.err.println(ERROR);
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } while (mainMenuLoop);
    }

    /**
     * Level 3 menu.
     *
     * @return T/F whether login is successful or not.
     */
    public boolean loginSelection() {
        int userSelection = -1;
        String continueShop = "";

        System.out.println("\n\t1. Login As Customer");
        System.out.println("\t2. Login As Administrator");
        System.out.println("\t3. Continue as Guest");
        System.out.println("\t4. Go back");
        System.out.print("\nPlease Choose Your Option: ");

        try {
            userSelection = scanner.nextInt();
            scanner.nextLine();

            switch (userSelection) {
                case 1:
                    login.customerLogin(); // Login for customer.
                    if (login.currentUser != null) { // If currentUser is not null...
                        do {
                            productMenu.displayProducts(); // Displays available products.
                            productMenu.addToCart();  // Allows Customer-typed user to add items to cart and checkout.
                            
                            do{
                                System.out.print("Continue shopping (y/n)? "); // Prompt user if they wish to continue shopping.
                                continueShop = scanner.nextLine();
                            } while(!continueShop.equalsIgnoreCase("y") && !continueShop.equalsIgnoreCase("n")); // While user-entry is not y/n...
                            
                            if (continueShop.equalsIgnoreCase("n")) { // If user wishes to stop shopping...
                                System.out.println("Goodbye " + ((Customer) login.currentUser).getName() + "! Logging out...");
                                login.currentUser = null; // Log the user out.
                            }
                        } while (continueShop.equalsIgnoreCase("y"));
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 2:
                    login.adminLogin(); // Login for admins.
                    if (login.currentUser != null) { // If currentUser is not null...
                        adminPanel(); // Enter Admin Privileges 
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 3:
                    login.currentUser = (new Customer("Guest", "Guest123!", "Guest", "", "", "", "", ""));
                    if (login.currentUser != null) { // If currentUser is not null...
                        do {
                            productMenu.displayProducts(); // Displays available products.
                            productMenu.addToCart();  // Allows Customer(Guest)-typed user to add items to cart and checkout.
                            
                            do{
                                System.out.print("Continue shopping (y/n)? "); // Prompt user if they wish to continue shopping.
                                continueShop = scanner.nextLine();
                            } while(!continueShop.equalsIgnoreCase("y") && !continueShop.equalsIgnoreCase("n")); // While user-entry is not y/n...
                            
                            if (continueShop.equalsIgnoreCase("n")) { // If user wishes to stop shopping...
                                System.out.println("Goodbye " + ((Customer) login.currentUser).getName() + "! Logging out...");
                                login.currentUser = null; // Log the user out.
                            }
                        } while (continueShop.equalsIgnoreCase("y"));
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 4:
                    return true; // Goes to login as page..
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println(ERROR);
        } catch (InputMismatchException e) {
            login.currentUser = null; // Logs out any current user.
            System.err.println(ERROR);
            scanner.nextLine();
        }

        return false;
    }

    /**
     * Level 5b menu (Editing function for administrators).
     */
    public void adminPanel() {
        int userSelection;
        boolean done = false;

        do {
            try {
                System.out.println("\n\t1. Add Product");
                System.out.println("\t2. Remove Product");
                System.out.println("\t3. Edit Product");
                System.out.println("\t4. Log Out");
                System.out.print("\nPlease Choose Your Option: ");
                userSelection = scanner.nextInt();
                scanner.nextLine();

                switch (userSelection) { // Edit type
                    case 1:
                        productMenu.addProduct();
                        break;
                    case 2:
                        productMenu.removeProduct();
                        break;
                    case 3:
                        productMenu.editProduct();
                        break;
                    case 4:
                        System.out.println("Goodbye " + ((Administrator) login.currentUser).getAdminName() + "! Logging out...");
                        login.currentUser = null; // Log the user out.
                        done = true;
                        break;
                    default:
                        throw new IndexOutOfBoundsException(ERROR);
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(ERROR);
            } catch (InputMismatchException e) {
                System.err.println(ERROR);
                scanner.nextLine();
            }
        } while (!done);
    }
}