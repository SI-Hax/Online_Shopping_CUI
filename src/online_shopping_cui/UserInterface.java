package online_shopping_cui;

import java.util.*;

/**
 * User Interface Class
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.03
 * @since 18/04/2021
 **/
public class UserInterface {
    protected static Scanner scanner;
    protected HashMap<String, User> users;
    protected ProductList products;
    protected String[] categories = {"\t1. PC Parts\n", "\t2. Laptop\n", "\t3. Camera\n", "\t4. Printer\n", "\t5. Smartphone\n", "\t6. Misc"};
    protected User currentUser;
    protected boolean mainMenuLoop, accountCreationSuccess, loginSuccess;

    public UserInterface()
    {
        this.scanner = new Scanner(System.in);
        this.users = UserFileIO.importUserData();
        this.products = ProductFileIO.importProductData();
        this.mainMenuLoop = true;
        this.accountCreationSuccess = false;
        this.loginSuccess = false;
    }

    public void mainMenu()
    {
        System.out.println("\n\t1. Login");
        System.out.println("\t2. Create Account");
        System.out.println("\t3. Exit");
    }

    public void menuSelections()
    {
        do {
            try {
                this.mainMenu();
                System.out.print("\nPlease Choose Your Option: ");
                int uAnswer = scanner.nextInt();
                scanner.nextLine();
                switch (uAnswer) {
                    // Choose To Login As Administrator Or Customer
                    case 1: // User selects login in level 1 menu...
                        do {
                            loginSuccess = loginSelection();
                            //break;
                        } while (!loginSuccess);
                        mainMenuLoop = false;
                        break;
                    // Choose To Register As Administrator Or Customer
                    case 2:
                        do {
                            try {
                                System.out.println("\n\t1. Create Account For Customer");
                                System.out.println("\t2. Create Account For Administrator");
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
                                    default:
                                        throw new IndexOutOfBoundsException();
                                }
                            } catch (IndexOutOfBoundsException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 3");
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.err.println(e.getMessage());
                                scanner.nextLine();
                            }
                        } while (!accountCreationSuccess);
                        break;
                    case 3:
                        System.out.println("\nGood Bye");
                        mainMenuLoop = false;
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
            } catch (InputMismatchException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
            }
        } while (mainMenuLoop);
    }

    public boolean loginSelection()
    {
        int userSelection = -1;

        System.out.println("\n\t1. Login As Customer");
        System.out.println("\t2. Login As Administrator");
        System.out.println("\t3. Continue as Guest");
        System.out.print("\nPlease Choose Your Option: ");

        try
        {
            userSelection = scanner.nextInt();
            scanner.nextLine();

            switch (userSelection) {
                case 1:
                    customerLogin(); // Login for customer.
                    if(currentUser != null) { // If currentUser is not null...
                        displayProducts(); // Displays available products.
                        addToCart();  // Allows Customer-typed user to add items to cart and checkout.
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 2:
                    adminLogin(); // Login for admins.
                    if(currentUser != null) { // If currentUser is not null...
                        adminPanel(); // Enter Admin Privileges 
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                case 3:
                    this.currentUser = (new Customer("Guest", "Guest123!", "Guest", "", "", "", "", ""));
                    if(currentUser != null) { // If currentUser is not null...
                        displayProducts(); // Displays available products.
                        addToCart();  // Allows Customer-typed user to add items to cart and checkout.
                    } else {
                        return false; // Goes to login as page..
                    }
                    return true;
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Please Enter The Correct Options, 1 - 3");
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println("Please Enter The Correct Options, 1 - 3");
            scanner.nextLine();
        }

        return false;
    }

    private boolean customerLogin()
    {
        boolean promptLogin = true;
        do
        {
            System.out.print("\n\tLogin ID: ");
            String loginID = scanner.nextLine();

            if(loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID)) { // If user-specified login is in the database...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID); // Saves current logged-in user.
                        System.out.println("\nLogin Successful, Welcome Back " + ((Customer)currentUser).getName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.out.println("Passwords do not match!");
                        promptPassword = true;
                    }
                } while(promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered user!");
                promptLogin = true;
            }
        } while(promptLogin);

        return false;
    }

    private boolean adminLogin()
    {
        boolean promptLogin = true;
        do
        {
            System.out.print("\n\tLogin ID: ");
            String loginID = scanner.nextLine();

            if(loginID.equalsIgnoreCase("b")) { // If user wishes to go back...
                return false; // Exits method and returns false to caller to go back up a level for menu.
            } else if (this.users.containsKey(loginID)) { // If user-specified login is in the database...
                boolean promptPassword = true;

                do {
                    // Prompt user for their password.
                    System.out.print("\tPassword: ");
                    String password = scanner.nextLine();

                    if (this.users.get(loginID).getPassword().equals(password)) { // If user-specified password match
                        this.currentUser = this.users.get(loginID);
                        System.out.println("\nLogin Successful, Welcome Back " + ((Administrator)currentUser).getAdminName()); // Welcomes user.
                        promptLogin = false;
                        promptPassword = false;
                        return true; // Exits method and returns true to caller.
                    } else if (password.equalsIgnoreCase("b")) { // If user wishes to go back up a level...
                        return false; // Exits method and returns false to caller to go back up a level for menu.
                    } else { // Otherwise....
                        System.err.println("Passwords do not match!");
                        promptPassword = true;
                    }
                } while(promptPassword);
            } else { // If user-specified login ID is non-existent...
                System.out.println("\nSorry, " + loginID + " is not a registered user!");
                promptLogin = true;
            }
        } while(promptLogin);

        return false;
    }

    public boolean createCustomerAccount()
    {
        String loginID, password, name, phone, email, address, cardNumber, cardHolder;

        System.out.println("\n*Required Fields (Press enter to skip non-essential fields)");
        System.out.print("Login ID*: ");
        loginID = scanner.nextLine();

        // While user-defined login ID is empty or exists within collection...
        while (loginID.isEmpty() || users.containsKey(loginID)) {
            // Promp user for another login id.
            System.out.print("Please choose another login ID: ");
            loginID = scanner.nextLine();
        }

        do {
            System.out.print("Password* : ");
            password = scanner.nextLine();

            if (!Utilities.passIsSecure(password, 8)) {
                System.err.println("Password is not strong enough (Requirements: 8+ Characters with 1 Upper, 1 Lower, 1 Number, 1 Symbol), try another: ");
            }
            // While user-defined password is not secure
        } while(!Utilities.passIsSecure(password, 8));

        System.out.print("Full Name: ");
        name = scanner.nextLine();

        System.out.print("Phone Number: ");
        phone = scanner.nextLine();
        
        System.out.print("Email: ");
        email = scanner.nextLine();
        
        System.out.print("Address: ");
        address = scanner.nextLine();
        
        System.out.print("Card Number: ");
        cardNumber = scanner.nextLine();
        
        System.out.print("Card Holder Name: ");
        cardHolder = scanner.nextLine();
  
        this.users.put(loginID, new Customer(loginID, password, name, phone, email, address, cardNumber, cardHolder));

        return true;
    }

    private boolean createAdministratorAccount()
    {
        String loginID, password, name, email;

        System.out.println("\n*Required Fields (Press enter to skip non-essential fields)");
        System.out.print("Login ID*: ");
        loginID = scanner.nextLine();

        // While user-defined login ID is empty or exists within collection...
        while (loginID.isEmpty() || users.containsKey(loginID)) {
            // Promp user for another login id.
            System.out.print("Please choose another login ID: ");
            loginID = scanner.nextLine();
        }

        do {
            System.out.print("Password*: ");
            password = scanner.nextLine();

            if (!Utilities.passIsSecure(password, 14)) {
                System.err.println("Password is not strong enough (Requirements: 14+ Character, 1 Upper, 1 Lower, 1 Number, 1 Symbol), try another: ");
            }
            // While user-defined password is not secure
        } while(!Utilities.passIsSecure(password, 14));

        System.out.print("Full Name: ");
        name = scanner.nextLine();
        
        System.out.print("Email: ");
        email = scanner.nextLine();

        this.users.put(loginID, new Administrator(loginID, password, name, email));

        return true;
    }

    public void displayProducts()
    {
        System.out.print(this.products.toString());
    }

    public void addToCart()
    {
        ShoppingCart cart = new ShoppingCart(this.currentUser);
        boolean run = true;

        System.out.println("");
        while(run){
            System.out.print("Please select a product to add to cart (0 to proceed to checkout): ");
            int productSelection = scanner.nextInt();

            if(productSelection == 0){ // If user selects to stop adding products...
                run = false; //
                continue;
            }

            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();
            
            if(quantity > 0) { // If user specifies 1 or more as quantity...
                // Finds the product via indexing and adds it to the cart alongside user-specified quantity.
                cart.addToCart(products.getProductList().get(productSelection - 1), quantity);
            }
        }

        System.out.println(cart.cartList());
        System.out.println(cart.generateInvoice(this.currentUser));
    }

    public void adminPanel()
    {
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

                switch (userSelection) {
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
                        System.out.println(((Administrator)currentUser).getAdminName() + " Logged out");
                        this.currentUser = null;
                        done = true;
                }

            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Enter The Correct Options, 1 - 4");
            } catch (InputMismatchException e) {
                System.err.println("Please Enter The Correct Options, 1 - 4");
                scanner.nextLine();
            }
        } while(!done);
    }

    public void addProduct()
    {
        String productName;
        int productID = 0;
        double price = 0D;
        Category category = null;
        int productCategorySelection;
        Integer stock = new Integer(0);

        do {
            System.out.print("\nProduct Name: ");
            productName = scanner.nextLine();

            if (productName.isEmpty()) {
                System.err.println("\nError: Name can't be empty.");
            }

        } while(productName.isEmpty());

        while (true) {
            try {
                System.out.print("Product ID: ");
                productID = scanner.nextInt();
                
                for(Product product: this.products.getProductList()){ // Traverse through the list of products.
                    if(productID == product.getProductID()){ // If product ID is already existent...
                        throw new IllegalArgumentException("Existing Product ID Detected!");
                    }
                }
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.err.println("\nPlease enter a valid Product ID");
                scanner.nextLine();
            } catch (IllegalArgumentException e) {
               System.err.println(e.getMessage());
            }
        }

        while(true) {
            try {
                System.out.print("Price: ");
                price = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.err.println("\nPlease Enter a Valid Price");
                scanner.nextLine();
            }
        }

        System.out.println("Product Category: ");
        while (true) {
            try {
                for(String cat: categories) {
                    System.out.print(cat);
                }
                System.out.println("\nSelect: ");
                productCategorySelection = scanner.nextInt(); 
                
                category = Category.values()[productCategorySelection - 1];
                
                if(category == null) {
                    throw new IndexOutOfBoundsException("Selection Out of Bounds");
                } else {
                    break;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Select From 1 - 6");
            } catch (InputMismatchException e) {
                System.err.println("Please Enter a Valid Number");
                scanner.nextLine();
            }
        }

        while(true) {
            try {
                System.out.print("Initial Stock: ");
                stock = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.err.println("\nPlease Enter a Valid Number");
                scanner.nextLine();
            }
        } 

        // Adds the product to the instantiated ProductList object.
        this.products.addSingleProduct(new Product(productName, productID, price, category, stock));
    }

    public void removeProduct()
    {
        int pIndRmv = 0;
        boolean success = false;
        Product pToRmv = null;
        
        do {
            System.out.println(this.products.toString());
            System.out.print("Select: ");
            try {
                pIndRmv = (scanner.nextInt() - 1); // Gets the user-selection and modify it to enable indexed access. 
                pToRmv = this.products.getProductList().get(pIndRmv); // Gets the specific Product object that is to be removed.
                this.products.removeProduct(pToRmv.getCategory(), pToRmv); // Removes the product from the ProductList Object.
                
                if(pIndRmv < 0 || pIndRmv >  this.products.getProductList().size()) { // If user's selection is out of bounds...
                    throw new IndexOutOfBoundsException("Selection Out of Bounds");
                } else { // Otherwise
                    success = true;
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Please Enter a Valid Number");
                scanner.nextLine();
            }
        } while(!success);
    }

    private void editProduct()
    {
        ArrayList<Product> pList = this.products.getProductList();
        
        int pIndEdit = 0;
        boolean editSuccess = false;
        Product pToEdit = null;
        do {
            System.out.println(this.products.toString());
            System.out.print("Select: ");
            try {
                pIndEdit = (scanner.nextInt() - 1); // Gets the user-selection and modify it to enable indexed access. 

                if(pIndEdit < 0 || pIndEdit >  this.products.getProductList().size()) { // If user's selection is out of bounds...
                    throw new IndexOutOfBoundsException("Selection Out of Bounds");
                } else {
                    pToEdit = pList.get(pIndEdit); // Saves the reference of the product to be edited.
                }
                
                boolean edit2Success = false;
                System.out.println("\nWhich would you like to edit? (0 to exit)");
                do{
                    System.out.println("\n\t1. Product Name");
                    System.out.println("\t2. Product ID");
                    System.out.println("\t3. Price");
                    System.out.println("\t4. Category");
                    System.out.println("\t5. Stock");
                    System.out.print("> ");
                    int editType = scanner.nextInt();
                    
                    switch(editType){
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
                            break;
                        case 3:
                            System.out.print("\nNew Price: $");
                            pToEdit.setPrice(scanner.nextDouble()); // Modifies the price.
                            break;
                        case 4:
                            System.out.println("\nCategories: ");
                            for(String cat: categories) {
                                System.out.print(cat);
                            }
                            System.out.print("> ");
                            pToEdit.setCategory(Category.values()[scanner.nextInt() - 1]); // Modifies the Category.
                            break;
                        case 5:
                            System.out.print("\nStock: ");
                            pToEdit.setStock(new Integer(scanner.nextInt())); // Modifies the stock.
                            break;
                        default:
                            throw new IndexOutOfBoundsException("Selection Out of Bounds");
                    }                   
                } while(!edit2Success);
                
                ProductFileIO.exportProductData(products);
            } catch (IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.err.println("Please Enter a Valid Number");
                scanner.nextLine();
            }
        } while(!editSuccess);
    }
}