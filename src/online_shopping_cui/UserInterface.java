package online_shopping_cui;

import java.util.*;

public class UserInterface {
    protected static Scanner scanner;
    protected HashMap<String, User> users;
    protected ProductList products;
    
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
                                System.err.flush();
                            } catch (InputMismatchException | IllegalArgumentException e) {
                                System.err.println(e.getMessage());
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
            } catch (InputMismatchException e) {
                System.err.println("Please Enter The Correct Options, 1 - 3");
                System.err.flush();
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

    private boolean adminLogin() {
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

    public boolean createCustomerAccount() {
        // TODO data validation in each prompt
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

        System.out.print("Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.print("Card Holder Name: ");
        String cardHolder = scanner.nextLine();

        this.users.put(loginID, new Customer(loginID, password, name, phone, email, address, cardNumber, cardHolder));

        return true;
    }

    private boolean createAdministratorAccount() 
    {
        System.out.print("\nLogin ID: ");
        String loginID = scanner.nextLine();

        while (loginID.isEmpty() || users.containsKey(loginID)) {
            // Promp user for another login id.
            System.out.print("Please choose another login ID:");
            loginID = scanner.nextLine();
        }

        System.out.print("Password: ");
        String password = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        this.users.put(loginID, new Administrator(loginID, password, name, email));
        
        return true;
    }
    
    public void displayProducts(){
        int counter = 1;
        System.out.println("");
        
        // For each loop to traverse through each key (Category) of the LinkedHashMap.
        for(Map.Entry<Category, ArrayList<Product>> category : products.getSingleProductList().entrySet()) {
            // For each loop to traverse through each value (ArrayList of Products for a specific Category) of the LinkedHashMap.
            for(Product product: category.getValue()) {
                System.out.println("\t" + (counter++) + ". " + "Product Name: " + product.getProductName() + " Stock: " + product.getStock() + 
                       " Price: $" + product.getPrice());
            }
        }
    }
    
    public void addToCart() {
        ShoppingCart cart = new ShoppingCart(this.currentUser);
        boolean run = true;
     
        while(run){
            System.out.print("\nPlease select a product to add to cart (0 to stop): ");
            int productSelection = scanner.nextInt();

            if(productSelection == 0){ // If user selects to stop adding products...
                run = false; // 
                continue;
            }
            
            System.out.print("Quantity: ");
            int quantity = scanner.nextInt();

            int counter = 1;
            
            // For each loop to traverse through each key (Category) of the LinkedHashMap.
            for(Map.Entry<Category, ArrayList<Product>> category : products.getSingleProductList().entrySet()) {
                // For each loop to traverse through each value (ArrayList of Products for a specific Category) of the LinkedHashMap.
                for(Product product: category.getValue()) {
                    if(counter == productSelection) { // If the counter reaches user-specified index...
                       cart.addToCart(product, quantity); // Add the product to cart.
                       //break; // Exit loop.
                    }
                    counter++; // Increment counter until user-selected product is found.
                }
            }        
        }
        
        System.out.println(cart.cartList());
        System.out.println(cart.generateInvoice(this.currentUser));
    }
}
