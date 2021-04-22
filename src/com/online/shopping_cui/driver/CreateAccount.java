/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @version 1.01
 * @since 18/04/2021
 *
 */
public class CreateAccount {

    static String REQUIRED = "\n*Required Fields (\"b\" to go back or press \"enter\" to skip non-essential fields)";
    static String PASSWORDSTRENGTH = "Password is not strong enough (Requirements: 1 Upper, 1 Lower, 1 Number, 1 Symbol, and ";

    protected static Scanner scanner;
    protected HashMap<String, User> users;

    public CreateAccount() {
        this.scanner = new Scanner(System.in);
        this.users = UserFileIO.importUserData();
    }

    /**
     * Level 4b menu.
     *
     * @return T/F whether account was created.
     */
    public boolean createCustomerAccount() {
        String loginID, password;
        boolean saved = false;

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

            if (!Utilities.passIsSecure(password, 8)) { // If user-defined password is not secure enough...
                System.err.println(PASSWORDSTRENGTH + "8+ Characters): "); // Output guide/prompt.
            }
        } while (!Utilities.passIsSecure(password, 8)); // While user-defined password is not secure.

        Customer newCustomer = new Customer(loginID, password); // Create a new instance of Customer.

        System.out.print("\tFull Name: ");
        newCustomer.setName(scanner.nextLine());

        System.out.print("\tPhone Number: ");
        newCustomer.setPhone(scanner.nextLine());

        do {
            System.out.print("\tEmail: ");
            saved = newCustomer.setEmail(scanner.nextLine());

            if (!saved) { // If email is not saved...
                System.err.println("Input is not in email format (e.g. john@gmail.com)");
            }
        } while (!saved); // Keep looping while email is not blank but is in an invalid format.

        System.out.print("\tAddress: ");
        newCustomer.setAddress(scanner.nextLine());

        do {
            System.out.print("\tCard Number: ");
            saved = newCustomer.setCardNumber(scanner.nextLine());

            if (!saved) { // If card number was not saved...
                System.err.println("Please enter a valid card number.");
            }
        } while (!saved); // Keep looping while cardnumber is not blank but is an invalid number.

        System.out.print("\tCard Holder Name: ");
        newCustomer.setCardHolder(scanner.nextLine());

        this.users.put(loginID, newCustomer);

        System.out.println("Account created. Please log in to start shopping =)");

        return true;
    }

    /**
     * Level 4b menu.
     *
     * @return T/F whether account was created.
     */
    protected boolean createAdministratorAccount() {
        String loginID, password, name, email;
        boolean saved = false;

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

            if (!Utilities.passIsSecure(password, 14)) { // If user-defined password is not secure enough...
                System.err.println(PASSWORDSTRENGTH + "14+ Characters): "); // Output guide/prompt.
            }
        } while (!Utilities.passIsSecure(password, 14)); // While user-defined password is not secure

        Administrator newAdmin = new Administrator(loginID, password); // Creates a new instance of an Administrator.

        System.out.print("\tFull Name: ");
        newAdmin.setAdminName(scanner.nextLine());

        do {
            System.out.print("\tEmail: ");
            saved = newAdmin.setAdminEmail(scanner.nextLine());

            if (!saved) { // If email is not saved...
                System.err.println("Input is not in email format (e.g. john@gmail.com)");
            }
        } while (!saved); // Keep looping while email is not blank but is in an invalid format.

        this.users.put(loginID, newAdmin);

        System.out.println("Account created. Please log in to commence administration =)");
        return true;
    }
}
