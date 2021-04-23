package com.online.shopping_cui.driver;

import com.online.shopping_cui.enumerations.Category;
import com.online.shopping_cui.model.ProductList;
import com.online.shopping_cui.model.Product;
import com.online.shopping_cui.model.ShoppingCart;
import com.online.shopping_cui.model.User;

import java.util.*;

/**
 * Product Menu Class - This class contains methods and attributes that handle
 * the product menu.
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.03
 * @since 18/04/2021
 *
 */
public class ProductMenu {

    static String ERROR = "Please choose from the given options";
    static String BACK = "Select (0 to go back): ";
    static String VALIDNO = "Please enter a number";

    protected String[] categories = {"\t1. PC Parts\n", "\t2. Laptop\n", "\t3. Camera\n", "\t4. Printer\n", "\t5. Smartphone\n", "\t6. Misc"};

    protected Scanner scanner;
    protected ProductList products;
    
    public ProductMenu(Scanner scanner, ProductList products) {
        this.scanner = scanner;
        this.products = products;
    }

    public void displayProducts() {
        System.out.print(this.products.toString());
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

            if (productName.trim().isEmpty()) {
                System.err.println("Error: Name can't be empty.");
            } else if (productName.equalsIgnoreCase("b")) { // If user wishes to go back...
                return; // Exit method.
            }
        } while (productName.trim().isEmpty());

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
                break; // Move to next prompt.
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
                break; // Move to next prompt.
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
                System.out.print("\n\tSelect: ");
                productCategorySelection = scanner.nextInt();
                scanner.nextLine();

                category = Category.values()[productCategorySelection - 1];

                if (category == null) {
                    throw new IndexOutOfBoundsException(ERROR);
                } else {
                    break; // Move to next prompt.
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
                break; // Move to add product.
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
                } else { // Otherwise...
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
    public void editProduct() {
        ArrayList<Product> pList = this.products.getProductList();

        int pIndEdit = -10;
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
                } else if (pIndEdit < 0 || pIndEdit > this.products.getProductList().size()) { // If user's selection is out of bounds...
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

                    int newID = -1;
                    switch (editType) {
                        case 0:
                            edit2Success = true;
                            break;
                        case 1:
                            System.out.print("New Name: ");
                            pToEdit.setProductName(scanner.nextLine()); // Modifies the name.
                            break;
                        case 2:
                            do{
                                System.out.print("New Product ID: ");
                                newID = scanner.nextInt(); 
                                scanner.nextLine();
                                for (Product product : this.products.getProductList()) { // Traverse through the list of products.
                                    if (newID == product.getProductID()) { // If product ID is already existent...
                                        System.err.println("Existing Product ID Detected!");
                                        newID = -1;
                                        break;
                                    }
                                }
                            } while(newID == -1);
                            pToEdit.setProductID(newID); // Modifies the Product ID.
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

    /**
     * Level 5a menu (Shopping function for customer).
     * 
     * @param currentUser : User that is currently logged in.
     */
    public void addToCart(User currentUser) {
        ShoppingCart cart = new ShoppingCart(currentUser);
        boolean run = true;

        while (run) {
            System.out.print("Please select a product to add to cart (0 to proceed to checkout): ");
            String productSelection = scanner.nextLine();
            Product selectedProduct = null;

            if (productSelection.matches("^-?\\d+\\.?\\d*$")) { // If user entered a number (accounts for negatives and decimals too)...
                int selectedNo = Integer.parseInt(productSelection);
                if (selectedNo < 0 || selectedNo > products.getProductList().size()) { // If user-selected number is outside of the available choices...
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

                if (selectedProduct == null) {
                    System.err.println(ERROR); // Outputs error message.
                    continue; // Reprompts the user to select another product.
                }
            }

            System.out.print("Quantity: ");
            String quantityStr = "";
            int quantity = 0;

            try {
                quantityStr = scanner.nextLine();
                if (quantityStr.matches("^-?\\d+\\.?\\d*$")) { // If user enters a number...
                    quantity = Integer.parseInt(quantityStr); // Convert the String to an Integer.
                } else { // Otherwise...
                    throw new IllegalArgumentException(VALIDNO);
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            if (quantity > 0 && selectedProduct != null) { // If user specifies 1 or more as quantity and selectedProduct is not empty...               
                cart.addToCart(selectedProduct, quantity); // Adds the product to the cart alongside user-specified quantity.
            }
        }

        System.out.println(cart.generateInvoice(currentUser)); // Outputs invoice to user.
    }
}
