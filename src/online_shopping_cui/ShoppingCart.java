package online_shopping_cui;

import java.util.ArrayList;

/**
 * @author Amos Foong <18044418>
 */
public class ShoppingCart {
    private ArrayList<Product> products;
    private ArrayList<Integer> quantity;
    private double grandTotal;
    private User user;

    public ShoppingCart(User user) {
        this.products = new ArrayList<Product>();
        this.quantity = new ArrayList<Integer>();
        this.grandTotal = 0.0;
        this.user = user;
    }
    
    public double getGrandTotal() {
        return this.grandTotal;
    }
    
    public void addItemToCart(Product product, int amount) {
        this.products.add(product);
        this.quantity.add(amount);
        product.setStock(product.getStock() - amount); // Decrements product stock.
        this.grandTotal += (product.getPrice() * amount);
    }

    public void removeItemFromCart(int index) {
        this.grandTotal -= (this.products.get(index).getPrice() * this.quantity.get(index));
        this.products.get(index).setStock(this.products.get(index).getStock() + this.quantity.get(index)); // Increments product's stock.
        this.products.remove(index);
        this.quantity.remove(index);
    }
}
