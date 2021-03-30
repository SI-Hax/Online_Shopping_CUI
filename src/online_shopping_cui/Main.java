/**
 * <h1>Main Class!</h1>
 * An online shopping system with an integrated
 * Inventory management system for
 * Program Design Construction Assignment 1.
 *
 * @author  Miguel Emmara - 18022146
 * @author  Amos Foong - 18044418
 * @author  Roxy Dao - 1073633
 * @version 1.0
 * @since   15/03/2021
 */
package online_shopping_cui;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This is the main method.
 * @param args Unused.
 * @return Nothing.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = true;
        boolean stop2 = true;
        mainMenu();

        do {
            try {
                System.out.print("\nPlease Choose Your Option: ");
                int uAnswer = scanner.nextInt();
                scanner.nextLine();
                switch (uAnswer) {
                    case 1:
                        System.out.print("\n\tUser Name: ");
                        String loginID = scanner.nextLine();
                        System.out.print("\tPassword: ");
                        String password = scanner.nextLine();
                        // TODO login user , should we have an options to login as either customer or admin or using if else to determine
                        // TODO if it is a customer or admin by using login id?
                        User user = new User(loginID) {
                            @Override
                            public boolean setPassword(String password) {
                                return false;
                            }
                        };
                        break;
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
                                        System.out.print("\nUser Name: ");
                                        loginID = scanner.nextLine();
                                        System.out.print("Full Name: ");
                                        String customerName = scanner.nextLine();
                                        System.out.print("address: ");
                                        String address = scanner.nextLine();
                                        System.out.print("Phone Number: ");
                                        String phoneNumber = scanner.nextLine();
                                        System.out.print("Email: ");
                                        String email = scanner.nextLine();
                                        System.out.print("Card Number: ");
                                        String cardNumber = scanner.nextLine();
                                        System.out.print("Card Expire Date: ");
                                        String cardExpDate = scanner.nextLine();
                                        System.out.print("Card Type: ");
                                        String cardType = scanner.nextLine();
                                        System.out.print("Card Name: ");
                                        String cardName = scanner.nextLine();
                                        // TODO create customer
                                        stop2 = false;
                                        break;
                                    case 2:
                                        System.out.print("\nName Of The Company");
                                        String companyName = scanner.nextLine();
                                        System.out.print("User Name: ");
                                        loginID = scanner.nextLine();
                                        System.out.print("Full Name: ");
                                        customerName = scanner.nextLine();
                                        System.out.print("address: ");
                                        address = scanner.nextLine();
                                        System.out.print("Phone Number: ");
                                        phoneNumber = scanner.nextLine();
                                        System.out.print("Email: ");
                                        email = scanner.nextLine();
                                        System.out.println("Card Number: ");
                                        cardNumber = scanner.nextLine();
                                        System.out.print("Card Expire Date: ");
                                        cardExpDate = scanner.nextLine();
                                        cardType = scanner.nextLine();
                                        System.out.print("Card Name: ");
                                        cardName = scanner.nextLine();
                                        // TODO create administrator
                                        stop2 = false;
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
                        } while (stop2);

                        break;
                    case 3:
                        System.out.println("\nGood Bye");
                        stop = false;
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
        } while (stop);
    }

    private static void mainMenu() {
        System.out.println("Welcome To Shop & Run Online Shopping");
        System.out.println("\n\t1. Login");
        System.out.println("\t2. Create Account");
        // TODO view product without login or register?
        System.out.println("\t3. Exit");
    }
}