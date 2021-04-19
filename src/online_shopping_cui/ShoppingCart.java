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
    
    public void addToCart(Product product, int amount) {
        this.products.add(product);
        this.quantity.add(amount);
        product.setStock(product.getStock() - amount); // Decrements product stock.
        this.grandTotal += (product.getPrice() * amount);
    }

    public void removeFromCart(int index) {
        this.grandTotal -= (this.products.get(index).getPrice() * this.quantity.get(index));
        this.products.get(index).setStock(this.products.get(index).getStock() + this.quantity.get(index)); // Increments product's stock.
        this.products.remove(index);
        this.quantity.remove(index);
    }
    
    public void clearCart() {
        this.products.clear();
        this.quantity.clear();
        this.grandTotal = 0D;
    }
    
    public String cartList(){
        String cartList = "";
        
        for(int i = 0; i < this.products.size(); i++) {
            cartList += "Product: " + this.products.get(i).getProductName() + " Qty: " + this.quantity.get(i) + " Price: " + this.products.get(i).getPrice() +"\n"; 
        }
        
        return cartList;
    }
    
    public String generateInvoice(User currentUser){
        String invoice = "";
        
        invoice += "\n-----------------Invoice----------------\n";
        invoice += "Bill To: " + ((Customer)currentUser).getName() + "\n";
        invoice += "Billing Address: " + ((Customer)currentUser).getAddress() + "\n";
        invoice += "----------------------------------------\n";
        invoice += String.format("%20s%10s%7s\n", "Product", "Quantity", "Price");
        
        for(int i = 0; i < this.products.size(); i++) {
             invoice += String.format("%20s %8d  $%7.2f\n", this.products.get(i).getProductName(), this.quantity.get(i), (this.products.get(i).getPrice() * this.quantity.get(i)));
        }
        invoice += "----------------------------------------\n";
        invoice += String.format("%32s%7.2f\n", "Grand Total: $", this.getGrandTotal());
        invoice += "----------------------------------------";
        return invoice; 
    }       
}
