package com.online.shopping_cui.driver;

/**
 * <h1>Main Class!</h1>
 * An online shopping system with an integrated
 * Inventory management system for
 * Program Design Construction Assignment 1.
 *
 * @author Miguel Emmara - 18022146
 * @author Amos Foong - 18044418
 * @author Roxy Dao - 1073633
 * @version 1.0
 * @since 15/03/2021
 */
public class Main {
    /**
     * This is where the program commences.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.err.println(""); // Synchronises the System.err with System.out.
        System.out.println("Welcome To Shop & Run Online Shopping");           
        UserInterface userInterface = new UserInterface();
        userInterface.menuSelections();
    }
}