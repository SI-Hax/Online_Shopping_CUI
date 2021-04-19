package com.online.shopping_cui.model;

import java.util.ArrayList;

/**
 * This class is a representation of a shopping cart, it has:
 * <p>
 * Attributes:</p>
 * <ul>
 * <li>Products : List of products in the cart.</li>
 * <li>Quantity : Amount of a specific product the customer ordered.</li>
 * <li>GrandTotal: Total cost.</li>
 * <li>User: Logged in user who is using the cart.</li>
 * </ul>
 * Methods:
 * <ul>
 * <li>1-Parameter Constructor</li>
 * <li>Getter</li>
 * <li>AddToCart</li>
 * <li>RemoveFromCart</li>
 * <li>ClearCart</li>
 * <li>CartList</li>
 * <li>GenerateInvoice</li>
 * </ul>
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.03
 * @since 01/04/2021
 *
 */
public class ShoppingCart {

    private ArrayList<Product> products;
    private ArrayList<Integer> quantity;
    private double grandTotal;
    private User user;

    /**
     * 1-Parameter constructor for ShoppingCart, prepares Object attributes so
     * it can be used.
     *
     * @param user : Logged in user who will be using the cart.
     *
     */
    public ShoppingCart(User user) {
        this.products = new ArrayList<Product>();
        this.quantity = new ArrayList<Integer>();
        this.grandTotal = 0.0;
        this.user = user;
    }

    /**
     * Returns the total summed value of the cart items.
     *
     * @return Summed value of the cart items.
     *
     */
    public double getGrandTotal() {
        return this.grandTotal;
    }

    /**
     * Adds a product to the cart.
     *
     * @param product : Product to be added.
     * @param amount : Quantity of the specific product.
     *
     */
    public void addToCart(Product product, int amount) {
        this.products.add(product);
        this.quantity.add(amount);
        product.setStock(product.getStock() - amount); // Decrements product stock.
        this.grandTotal += (product.getPrice() * amount);
    }

    /**
     * Removes a specific item from the cart.
     *
     * @param index : Index of item to be removed.
     *
     */
    public void removeFromCart(int index) {
        this.grandTotal -= (this.products.get(index).getPrice() * this.quantity.get(index));
        this.products.get(index).setStock(this.products.get(index).getStock() + this.quantity.get(index)); // Increments product's stock.
        this.products.remove(index);
        this.quantity.remove(index);
    }

    /**
     * Removes everything from cart.
     *
     */
    public void clearCart() {
        this.products.clear();
        this.quantity.clear();
        this.grandTotal = 0D;
    }

    /**
     * String representation of the current items in the cart.
     *
     * @return String representation of the items in the cart.
     *
     */
    public String cartList() {
        String cartList = "";

        for (int i = 0; i < this.products.size(); i++) {
            cartList += "Product: " + this.products.get(i).getProductName() + " Qty: " + this.quantity.get(i) + " Price: " + this.products.get(i).getPrice() + "\n";
        }

        return cartList;
    }

    /**
     * Generates an invoice and returns a graphical representation of the
     * invoice to caller.
     *
     * @param currentUser : User who is logged on currently.
     * @return String representation of the invoice.
     *
     */
    public String generateInvoice(User currentUser) {
        String invoice = "";

        invoice += "\n-----------------Invoice----------------\n";
        invoice += "Bill To: " + ((Customer) currentUser).getName() + "\n";
        invoice += "Billing Address: " + ((Customer) currentUser).getAddress() + "\n";
        invoice += "----------------------------------------\n";
        invoice += String.format("%20s%10s%7s\n", "Product", "Quantity", "Price");

        for (int i = 0; i < this.products.size(); i++) {
            invoice += String.format("%20s %8d  $%7.2f\n", this.products.get(i).getProductName(), this.quantity.get(i), (this.products.get(i).getPrice() * this.quantity.get(i)));
        }
        invoice += "----------------------------------------\n";
        invoice += String.format("%32s%7.2f\n", "Grand Total: $", this.getGrandTotal());
        invoice += "----------------------------------------";
        return invoice;
    }
}
