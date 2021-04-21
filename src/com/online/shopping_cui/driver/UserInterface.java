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
 * @version 1.07
 * @since 18/04/2021
 *
 */
public class UserInterface {
    //Messages
    static String PASSWORDSTRENGTH = "Password is not strong enough (Requirements: 1 Upper, 1 Lower, 1 Number, 1 Symbol, and ";
    static String REQUIRED = "\n*Required Fields (\"b\" to go back or press \"enter\" to skip non-essential fields)";
    static String ERROR = "Please choose from the options above";
    static String BACK = "Select a Number (0 to go back): ";
    static String VALIDNO = "Please enter a number";
    static String WRONGPASS = "Incorrect password!";
    
    protected String[] categories = {"\t1. PC Parts\n", "\t2. Laptop\n", "\t3. Camera\n", "\t4. Printer\n", "\t5. Smartphone\n", "\t6. Misc"};       
    protected static Scanner scanner;
    protected HashMap<String, User> users;
    protected ProductList products;
    protected User currentUser;
    protected boolean mainMenuLoop, accountCreationSuccess, loginSuccess;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.users = UserFileIO.importUserData();
        this.products = ProductFileIO.importProductData();
        this.mainMenuLoop = true;
        this.accountCreationSuccess = false;
        this.loginSuccess = false;
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
                                        accountCreationSuccess = createCustomerAccount();
                                        break;
                                    case 2:
                                        accountCreationSuccess = createAdministratorAccount();
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
                        UserFileIO.exportUserData(this.users); // Exports/backs-up users' data.
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
                    customerLogin(); // Login for customer.
                    if (currentUser != null) { // If currentUser is not null...
                        do {
                            displayProducts(); // Displays available products.
                            addToCart();  // Allows Customer-typed user to add items to cart and checkout.

                            System.out.print("Continue shopping (y/n)? "); // Prompt user if they wish to continue shopping.
                            continueShop = scanner.nextLine();
                            if (continueShop.equalsIgnoreCase("n")) { // If user wishes to stop shopping...
                                System.out.println("Goodbye " + ((Customer) currentUser).getName() + "! Logging out...");
                                this.currentUser = null; // Log the user out.
                            }
                        } while (continueShop.equalsIgnoreCase("y"));
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 2:
                    adminLogin(); // Login for admins.
                    if (currentUser != null) { // If currentUser is not null...
                        adminPanel(); // Enter Admin Privileges 
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 3:
                    this.currentUser = (new Customer("Guest", "Guest123!", "Guest", "", "", "", "", ""));
                    if (currentUser != null) { // If currentUser is not null...
                        do {
                            displayProducts(); // Displays available products.
                            addToCart();  // Allows Customer(Guest)-typed user to add items to cart and checkout.

                            System.out.print("Continue shopping (y/n)? "); // Prompt user if they wish to continue shopping.
                            continueShop = scanner.nextLine();
                            if (continueShop.equalsIgnoreCase("n")) { // If user wishes to stop shopping...
                                System.out.println("Goodbye " + ((Customer) currentUser).getName() + "! Logging out...");
                                this.currentUser = null; // Log the user out.
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
            System.err.println(ERROR);
            scanner.nextLine();
        }

        return false;
    }

    /**
     * Level 4a menu.
     *
     * @return T/F whether login was a success.
     */
    private boolean customerLogin() {
        boolean promptLogin = true;
        do {
            System.out.println("\n\tPlease Enter Credentials (\"b\" to go back)");
            System.out.print("\tLogin ID: ");
            String loginID = scanner.nextLine();

            if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID)) { // If user-specified login is in the database...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID); // Saves current logged-in user.
                        System.out.println("\nLogin Successful, Welcome Back " + ((Customer) currentUser).getName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.out.println(WRONGPASS);
                        promptPassword = true;
                    }
                } while (promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered user!");
                promptLogin = true;
            }
        } while (promptLogin);

        return false;
    }

    /**
     * Level 4a menu.
     *
     * @return T/F whether login was a success.
     */
    private boolean adminLogin() {
        boolean promptLogin = true;
        do {
            System.out.println("\n\tPlease Enter Credentials (\"b\" to go back)");
            System.out.print("\tLogin ID: ");
            String loginID = scanner.nextLine();

            if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID)) { // If user-specified login is in the database...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID);
                        System.out.println("\nLogin Successful, Welcome Back " + ((Administrator) currentUser).getAdminName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.err.println(WRONGPASS);
                        promptPassword = true;
                    }
                } while (promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered user!");
                promptLogin = true;
            }
        } while (promptLogin);

        return false;
    }

    /**
     * Level 4b menu.
     *
     * @return T/F whether account was created.
     */
    public boolean createCustomerAccount() {
        String loginID, password, name, phone, email, address, cardNumber, cardHolder;

        System.out.println(REQUIRED);
        System.out.print("\tLogin ID*: ");
        loginID = scanner.nextLine();

        if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
            return false;
        }

        while (loginID.isEmpty() || users.containsKey(loginID)) {// While user-defined login ID is empty or exists within collection...
            // Promp user for another login id.
            System.err.print("Please choose another login ID: ");
            loginID = scanner.nextLine();
        }

        do {
            System.out.print("\tPassword*: ");
            password = scanner.nextLine();

            if (!Utilities.passIsSecure(password, 8)) {
                System.err.println(PASSWORDSTRENGTH + "8+ Characters): ");
            }
        } while (!Utilities.passIsSecure(password, 8)); // While user-defined password is not secure

        System.out.print("\tFull Name: ");
        name = scanner.nextLine();

        System.out.print("\tPhone Number: ");
        phone = scanner.nextLine();

        System.out.print("\tEmail: ");
        email = scanner.nextLine();

        System.out.print("\tAddress: ");
        address = scanner.nextLine();

        System.out.print("\tCard Number: ");
        cardNumber = scanner.nextLine();

        System.out.print("\tCard Holder Name: ");
        cardHolder = scanner.nextLine();

        this.users.put(loginID, new Customer(loginID, password, name, phone, email, address, cardNumber, cardHolder));

        System.out.println("Account created. Please log in to start shopping =)");

        return true;
    }

    /**
     * Level 4b menu.
     *
     * @return T/F whether account was created.
     */
    private boolean createAdministratorAccount() {
        String loginID, password, name, email;

        System.out.println(REQUIRED);
        System.out.print("\tLogin ID*: ");
        loginID = scanner.nextLine();

        if (loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
            return false;
        }

        while (loginID.isEmpty() || users.containsKey(loginID)) {// While user-defined login ID is empty or exists within collection...
            // Promp user for another login id.
            System.err.print("Please choose another login ID: ");
            loginID = scanner.nextLine();
        }

        do {
            System.out.print("\tPassword*: ");
            password = scanner.nextLine();

            if (!Utilities.passIsSecure(password, 14)) {
                System.err.println(PASSWORDSTRENGTH + "14+ Characters): ");
            }
            // While user-defined password is not secure
        } while (!Utilities.passIsSecure(password, 14));

        System.out.print("\tFull Name: ");
        name = scanner.nextLine();

        System.out.print("\tEmail: ");
        email = scanner.nextLine();

        this.users.put(loginID, new Administrator(loginID, password, name, email));

        System.out.println("Account created. Please log in to commence administration =)");
        return true;
    }

    public void displayProducts() {
        System.out.print(this.products.toString());
    }

    /**
     * Level 5a menu (Shopping function for customer).
     */
    public void addToCart() {
        ShoppingCart cart = new ShoppingCart(this.currentUser);
        boolean run = true;

        while (run) {
            System.out.print("Please select a product to add to cart (0 to proceed to checkout): ");
            String productSelection = scanner.nextLine();
            Product selectedProduct = null;
            
            if(productSelection.matches("^-?\\d+\\.?\\d*$")){ // If user entered a number (accounts for negatives and decimals too)...
                int selectedNo = Integer.parseInt(productSelection);
                if(selectedNo < 0 || selectedNo > products.getProductList().size()){ // If user-selected number is outside of the available choices...
                    System.err.println(ERROR); // Outputs error message.
                    continue; // Re-prompts user to select a product to add to cart.
                } else if (selectedNo == 0) { // If user selects to stop adding products...
                    run = false; // Stops the loop.
                    continue; // And go back one level.
                } else {
                    selectedProduct = products.getProductList().get(selectedNo - 1); // Get the product from the ArrayList via ArrayList.get().                   
                }
            } else { // Otherwise (e.g user enters the partial/full Product Name)...
                selectedProduct = products.searchProduct(productSelection); // Use ProductList search by keyword (String).
                
                if(selectedProduct == null) {
                    System.err.println(ERROR); // Outputs error message.
                    continue; // Reprompts the user to select another product.
                }
            }

            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0 && selectedProduct != null) { // If user specifies 1 or more as quantity and selectedProduct is not empty...               
                cart.addToCart(selectedProduct, quantity); // Adds the product to the cart alongside user-specified quantity.
            }
        }

        System.out.println(cart.generateInvoice(this.currentUser)); // Outputs invoice to user.
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
                        addProduct();
                        break;
                    case 2:
                        removeProduct();
                        break;
                    case 3:
                        editProduct();
                        break;
                    case 4:
                        System.out.println("Goodbye " + ((Administrator) currentUser).getAdminName() + "! Logging out...");
                        this.currentUser = null; // Log the user out.
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

    /**
     * Level 6a menu.
     */
    public void addProduct() {
        String productName;
        int productID = 0;
        double price = 0D;
        Category category = null;
        int productCategorySelection;
        Integer stock = 0;

        System.out.println("\nProduct Details (\"b\" to go back)");
        do {
            System.out.print("\tProduct Name: ");
            productName = scanner.nextLine();

            if (productName.isEmpty()) {
                System.err.println("Error: Name can't be empty.");
            } else if (productName.equalsIgnoreCase("b")) { // If user wishes to go back...
                return; // Exit method.
            }
        } while (productName.isEmpty());

        while (true) {
            try {
                System.out.print("\tProduct ID: ");
                productID = scanner.nextInt();
                scanner.nextLine();

                for (Product product : this.products.getProductList()) { // Traverse through the list of products.
                    if (productID == product.getProductID()) { // If product ID is already existent...
                        throw new IllegalArgumentException("Existing Product ID Detected!");
                    }
                }
                break;
            } catch (InputMismatchException e) {
                System.err.println("Please enter a valid Product ID");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        }

        while (true) {
            try {
                System.out.print("\tPrice: $");
                price = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.err.println(VALIDNO);
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.println("\tProduct Category: ");
                for (String cat : categories) {
                    System.out.print("\t" + cat); // Print out the categories.
                }
                System.out.print("\n\tSelect 1-6: ");
                productCategorySelection = scanner.nextInt();
                scanner.nextLine();

                category = Category.values()[productCategorySelection - 1];

                if (category == null) {
                    throw new IndexOutOfBoundsException(ERROR);
                } else {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(ERROR);
            } catch (InputMismatchException e) {
                System.err.println(VALIDNO);
                scanner.nextLine();
            }
        }

        while (true) {
            try {
                System.out.print("\tInitial Stock: ");
                stock = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.err.println(VALIDNO);
                scanner.nextLine();
            }
        }

        // Adds the product to the instantiated ProductList object.
        this.products.addSingleProduct(new Product(productName, productID, price, category, stock));
        System.out.println("Product added!");
    }

    /**
     * Level 6b menu.
     */
    public void removeProduct() {
        int pIndRmv = 0;
        boolean success = false;
        Product pToRmv = null;

        do {
            System.out.print(this.products.toString());
            System.out.print(BACK);
            try {
                pIndRmv = (scanner.nextInt() - 1); // Gets the user-selection and modify it to enable indexed access.
                scanner.nextLine();

                if (pIndRmv == -1) { // If user wishes to go back (0 pressed)...
                    return; // Exit this method.
                } else if (pIndRmv < 0 || pIndRmv > this.products.getProductList().size() - 1) { // If user's selection is out of bounds...
                    throw new IndexOutOfBoundsException(ERROR);
                } else { // Otherwise
                    pToRmv = this.products.getProductList().get(pIndRmv); // Gets the specific Product object that is to be removed.
                    this.products.removeProduct(pToRmv.getCategory(), pToRmv); // Removes the product from the ProductList Object.
                    success = true;
                    System.out.println("Product removed!");
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println(ERROR);
                scanner.nextLine();
            }
        } while (!success);
    }

    /**
     * Level 6c menu.
     */
    private void editProduct() {
        ArrayList<Product> pList = this.products.getProductList();

        int pIndEdit = -1;
        boolean editSuccess = false;
        Product pToEdit = null;
        do {
            System.out.print(this.products.toString());
            System.out.print(BACK);
            try {
                pIndEdit = (scanner.nextInt() - 1); // Gets the user-selection and modify it to enable indexed access.
                scanner.nextLine();

                if (pIndEdit == -1) { // If user wishes to go back (0 pressed)...
                    return; // Exit this method.
                } else if (pIndEdit < 0 || pIndEdit > this.products.getProductList().size() - 1) { // If user's selection is out of bounds...
                    throw new IndexOutOfBoundsException(ERROR);
                } else {
                    pToEdit = pList.get(pIndEdit); // Saves the reference of the product to be edited.
                }

                boolean edit2Success = false;
                do {
                    System.out.println("\nWhich would you like to edit? (0 to exit)");
                    System.out.println("\n\t1. Product Name");
                    System.out.println("\t2. Product ID");
                    System.out.println("\t3. Price");
                    System.out.println("\t4. Category");
                    System.out.println("\t5. Stock");
                    System.out.print("> ");
                    int editType = scanner.nextInt();
                    scanner.nextLine();

                    switch (editType) {
                        case 0:
                            edit2Success = true;
                            break;
                        case 1:
                            System.out.print("New Name: ");
                            pToEdit.setProductName(scanner.nextLine()); // Modifies the name.
                            break;
                        case 2:
                            System.out.print("New Product ID: ");
                            pToEdit.setProductID(scanner.nextInt()); // Modifies the Product ID.
                            scanner.nextLine();
                            break;
                        case 3:
                            System.out.print("New Price: $");
                            pToEdit.setPrice(scanner.nextDouble()); // Modifies the price.
                            scanner.nextLine();
                            break;
                        case 4:
                            System.out.println("Categories: ");
                            for (String cat : categories) {
                                System.out.print(cat);
                            }
                            System.out.print("> ");
                            pToEdit.setCategory(Category.values()[scanner.nextInt() - 1]); // Modifies the Category.
                            scanner.nextLine();
                            break;
                        case 5:
                            System.out.print("Stock: ");
                            pToEdit.setStock(scanner.nextInt()); // Modifies the stock.
                            scanner.nextLine();
                            break;
                        default:
                            throw new IndexOutOfBoundsException(ERROR);
                    }
                } while (!edit2Success);
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println(ERROR);
                scanner.nextLine();
            }
        } while (!editSuccess);

        System.out.println("Product edited!");
    }
}
