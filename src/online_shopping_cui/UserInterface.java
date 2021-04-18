package online_shopping_cui;

import java.util.*;

public class UserInterface {
    protected static Scanner scanner;
    protected HashMap<String, User> users;
    protected ProductList products;
    protected ShoppingCart cart;
    protected User currentUser;
    protected boolean mainMenuLoop, accountCreationSuccess, loginSuccess;
    
    public UserInterface()
    {
        this.scanner = new Scanner(System.in);
        this.users = UserFileIO.importUserData();
        this.products = ProductFileIO.importProductData();
        this.cart = new ShoppingCart(this.currentUser);
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
                mainMenu();
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
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.err.println("Please Enter The Correct Options, 1 - 2");
                                System.err.flush();
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
                System.err.flush();
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
                scanner.nextLine();
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
                    return true;
                case 2:
                    adminLogin(); // Login for admins.
                    return true;
                case 3:
                    // TODO: Something...
                    return true;
                default:
                    throw new IndexOutOfBoundsException();
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Please Enter The Correct Options, 1 - 3");
            System.err.flush();
        } catch (InputMismatchException | IllegalArgumentException e) {
            System.err.println("Please Enter The Correct Options, 1 - 3");
            System.err.flush();
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
                        this.currentUser = this.users.get(loginID);
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
                        adminPanel();
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

    public boolean createCustomerAccount()
    {
        System.out.print("\nLogin ID: ");
        String loginID = scanner.nextLine();

        // While user-defined login ID is empty or exists within collection...
        while (loginID.isEmpty() || users.containsKey(loginID)) {
            // Promp user for another login id.
            System.out.print("Please choose another login ID:");
            loginID = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // While user-defined password is not secure
        while(!test.Utilities.passIsSecure(password)) {
            System.out.print("Password is not strong enough, try another: ");
            password = scanner.nextLine();
        }

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        // While user-defined name is empty
        while (name.isEmpty()) {
            System.out.print("Full Name cannot be empty: ");
            name = scanner.nextLine();
        }

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        // While user-defined phone is empty
        while (phone.isEmpty()) {
            System.out.print("Phone Number cannot be empty: ");
            phone = scanner.nextLine();
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // While user-defined email is invalid
        while (!test.Utilities.emailIsValid(email)) {
            System.out.print("Please enter a valid email address: ");
            email = scanner.nextLine();
        }

        System.out.print("Address: ");
        String address = scanner.nextLine();

        // While user-defined address is empty
        while (address.isEmpty()) {
            System.out.print("Address cannot be empty: ");
            address = scanner.nextLine();
        }

        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();

        // While user-defined cardNumber invalid
        while (!Utilities.cardIsValid(cardNumber) || cardNumber.isEmpty()) {
            System.out.print("Please enter a valid card number: ");
            cardNumber = scanner.nextLine();
        }

        System.out.print("Card Holder Name: ");
        String cardHolder = scanner.nextLine();

        // While user-defined cardHolder is empty
        while (cardHolder.isEmpty()) {
            System.out.print("Card Holder Name cannot be empty: ");
            cardHolder = scanner.nextLine();
        }

        this.users.put(loginID, new Customer(loginID, password, name, phone, email, address, cardNumber, cardHolder));

        return false;
    }

    private boolean createAdministratorAccount() 
    {
        System.out.print("\nLogin ID: ");
        String loginID = scanner.nextLine();

        // While user-defined login ID is empty or exists within collection...
        while (loginID.isEmpty() || users.containsKey(loginID)) {
            // Promp user for another login id.
            System.out.print("Please choose another login ID:");
            loginID = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        // While user-defined password is not secure
        while(!Utilities.passIsSecure(password)) {
            System.out.print("Password is not strong enough, try another: ");
            password = scanner.nextLine();
        }

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        // While user-defined name is empty
        while (name.isEmpty()) {
            System.out.print("Full Name cannot be empty: ");
            name = scanner.nextLine();
        }

        System.out.print("Email: ");
        String email = scanner.nextLine();

        // While user-defined email is invalid
        while (!Utilities.emailIsValid(email)) {
            System.out.print("Please enter a valid email address: ");
            email = scanner.nextLine();
        }

        this.users.put(loginID, new Administrator(loginID, password, name, email));

        return false;
    }
    
    public void displayProducts()
    {
        int counter = 1;
        System.out.println();
        
        // For each loop to traverse through each key (Category) of the LinkedHashMap.
        for(Map.Entry<Category, ArrayList<Product>> category : products.getSingleProductList().entrySet()) {
            // For each loop to traverse through each value (ArrayList of Products for a specific Category) of the LinkedHashMap.
            for(Product product: category.getValue()) {
                System.out.println("\t" + (counter++) + ". " + "Product Name: " + product.getProductName() + " Stock: " + product.getStock() + 
                       " Price: $" + product.getPrice());
            }
        }
    }
    
    public void addToCart()
    {
     boolean run = true;
     
        while(run){
            System.out.print("\nPlease select a product to add to cart (0 to stop): ");
            int productSelection = scanner.nextInt();

            if(productSelection == 0){
                run = false;
                continue;
            }
            
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();

            int counter = 1;

            for(Map.Entry<Category, ArrayList<Product>> category : products.getSingleProductList().entrySet()) {
                for(Product product: category.getValue()) {
                    if(counter == productSelection) { // If the counter reaches user-specified index...
                        this.cart.addToCart(product, quantity); // Add the product to cart.
                        break;
                    }
                    counter++; // Increment counter until user-selected product is found.
                }
            }        
        }
        
        System.out.println(this.cart.cartList());
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
                        System.out.println("Log out");
                        done = true;
                }

            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
                scanner.nextLine();
            }
        } while(!done);
    }

    private void addProduct()
    {
        String productName;
        int productID = 0;
        double price = 0;
        Category category = Category.MISCELLANEOUS;
        int productCategorySelection;
        Integer stock = null;
        boolean success = false;

        do {
            System.out.print("Product Name: ");
            productName = scanner.nextLine();

            if (productName.isEmpty()) {
                System.out.println("Error: Name can't be empty.");
            }

        } while(productName.isEmpty());

        do {
            try {
                System.out.print("Product ID: ");
                productID = scanner.nextInt();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter an Integer for Product ID");
                System.err.flush();
                scanner.nextLine();
            }
        } while(!success);

        success = false;
        do {
            try {
                System.out.print("Price: ");
                price = scanner.nextDouble();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter an integer for price");
                System.err.flush();
                scanner.nextLine();
            }
        } while(!success);

        success = false;
        System.out.println("Product Category? ");
        do {
            try {
                System.out.println("\n\t1. PC Parts");
                System.out.println("\t2. Laptops");
                System.out.println("\t3. Cameras");
                System.out.println("\t4. Printers");
                System.out.println("\t5. SmartPhones");
                System.out.print("Answer: ");
                productCategorySelection = scanner.nextInt();

                switch (productCategorySelection) {
                    case 1:
                        category = Category.PCPARTS;
                        success = true;
                        break;
                    case 2:
                        category = Category.LAPTOP;
                        success = true;
                        break;
                    case 3:
                        category = Category.CAMERA;
                        success = true;
                        break;
                    case 4:
                        category = Category.PRINTER;
                        success = true;
                        break;
                    case 5:
                        category = Category.SMARTPHONE;
                        success = true;
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }
            } catch (IndexOutOfBoundsException e) {
                System.err.println("Please Enter The Correct Options, 1 - 5");
                System.err.flush(); }
            catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter an integer for price");
                System.err.flush();
                scanner.nextLine();
            }
        } while(!success);

        success = false;
        do {
            try {
                System.out.print("Initial Stock: ");
                stock = scanner.nextInt();
                scanner.nextLine();
                success = true;
            } catch (InputMismatchException | IllegalArgumentException e) {
                System.out.println("Please Enter an Integer for the initial stock");
                System.err.flush();
                scanner.nextLine();
            }
        } while(!success);

        new Product(productName, productID, price, category, stock);

        if (ProductFileIO.exportProductData(products)) {
            System.out.println("Product added to the database!");
        } else {
            System.out.println("Cannot add product to the database!");
        }
    }

    private void removeProduct()
    {
        int counter = 1;
        int productToRemove;
        boolean success = false;
        System.out.println("Available product to remove");

        for(Map.Entry<Category, ArrayList<Product>> category : products.getSingleProductList().entrySet()) {
            for(Product product: category.getValue()) {
                System.out.println();
                System.out.println("\t" + (counter++) + ". " + "Product Name: " + product.getProductName() + " Stock: " + product.getStock() +
                        " Price: " + product.getPrice());

                do {
                    try {
                        System.out.println("Select product to remove: ");
                        productToRemove = scanner.nextInt();
                        scanner.nextLine();
                        success = true;

                        if (productToRemove <= counter) {
                            // TODO remove product based on user choice
                            products.removeProduct(product.getCategory(),productToRemove);
                        } else {
                            System.out.println("Enter between 1 - " + counter);
                            success = false;
                        }
                    } catch (InputMismatchException | IllegalArgumentException e) {
                        System.out.println("Please Enter an Integer");
                        System.err.flush();
                        scanner.nextLine();
                    }
                } while(!success);
            }
        }
    }

    private void editProduct()
    {
        // TODO
    }
}
