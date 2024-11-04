import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RunBank {

    /**
     * Main method, banking interface
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        List<Customer> customers = new ArrayList<Customer>();
        List<Account> accounts = new ArrayList<Account>();
        ReadWriter rw = new ReadWriter();
        customers = rw.loadCustomers(accounts);
        CustomersManager custManager = new CustomersManager(customers);
        AccountsManager accManager = new AccountsManager(accounts);
        TransactionsManager tm = new TransactionsManager();

        Scanner scanner = new Scanner(System.in);
        String input;
        int btn;
        Customer activeCustomer = null;
        Account activeAccount = null;
        Account receivingAccount = null;
        Navigator nav = new Navigator();

        class Mode {
            public final int EXIT = 0;
            public final int CREDENTIALS = 1;
            public final int CHOOSE_ACCOUNT = 2;
            public final int CHOOSE_ACTION = 3; // choosing to inquire about a balance does not require an additional
                                                // mode
            public final int DEPOSIT = 4;
            public final int WITHDRAW = 5;
            public final int TRANSFER = 6;
            public final int PAY = 7;
            public final int MAIN = 8;
            public final int ADMIN = 9;
            public final int NEW_CUSTOMER = 10;
        }
        Mode modes = new Mode();
        int uiMode = modes.MAIN;

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        while (uiMode != modes.EXIT) {
            if (uiMode == modes.MAIN) { // Main menu
                input = nav.displayMainMenu();
                btn = tryParseInt(input);

                if (btn != -1) {
                    switch (btn) {
                        case (1):
                            uiMode = modes.CREDENTIALS;
                            break;
                        case (2):
                            uiMode = modes.ADMIN;
                            break;
                        case (3):
                            uiMode = modes.NEW_CUSTOMER;
                            break;
                        default:
                            nav.displayGenericInputError(input);
                            break;
                    }
                } else if (input.trim().toUpperCase().equals("EXIT")) {
                    uiMode = modes.EXIT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == modes.CREDENTIALS) { // Customer credentials prompt, ADD NAME INPUT FUNCTIONALITY
                input = nav.displayCustomerLogin();
                int uId = tryParseInt(input);
                if (uId != -1) {
                    if ((activeCustomer = custManager.searchById(uId)) != null) {
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Unrecognized ID, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.MAIN;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == modes.CHOOSE_ACCOUNT) { // Customer chooses account
                input = nav.displayAccounts(activeCustomer);
                btn = tryParseInt(input);
                if (btn >= 1 && btn <= activeCustomer.getAccounts().length) {
                    activeAccount = accManager.searchByNum(activeCustomer.getAccounts()[btn - 1].getAccountNumber());
                    uiMode = modes.CHOOSE_ACTION;
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.CREDENTIALS;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == modes.CHOOSE_ACTION) {
                input = nav.displayAccountActions(activeAccount);
                btn = tryParseInt(input);
                if (btn >= 1 && btn <= 5) {
                    switch (btn) {
                        case 1:
                            tm.checkBalance(activeCustomer, activeAccount);
                            nav.displayBalanceRequest(activeAccount);
                            System.out.println();
                            System.out.println("Please press ENTER to continue.");
                            scanner.nextLine();
                            break;
                        case 2:
                            uiMode = modes.DEPOSIT;
                            break;
                        case 3:
                            uiMode = modes.WITHDRAW;
                            break;
                        case 4:
                            uiMode = modes.TRANSFER;
                            break;
                        case 5:
                            uiMode = modes.PAY;
                            break;
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.CHOOSE_ACCOUNT;
                } else {
                    System.out.println("Unknown command, please try again.");
                }

                System.out.println();
            } else if (uiMode == modes.DEPOSIT) { // Deposit mode
                input = nav.displayDepositRequest();
                double amt = tryParseAmt(input);
                if (amt != -1) {
                    if (tm.deposit(activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been deposited to %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                        uiMode = modes.CHOOSE_ACTION;
                    } else {
                        System.out.println("Invalid amount, please try again.");
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == modes.WITHDRAW) { // Withdraw mode
                input = nav.displayWithdrawRequest();
                double amt = tryParseAmt(input);
                if (amt != -1) {
                    if (tm.withdraw(activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been withdrawn from %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                        uiMode = modes.CHOOSE_ACTION;
                    } else {
                        System.out.println("invalid amount, please try again.");
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = modes.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == modes.TRANSFER) { // Transfer mode
                input = nav.displayTransferAmtRequest();
                double amt = tryParseAmt(input);
                if (amt > 0) { // if input is a number greater than 0
                    System.out.println();
                    input = nav.displayTransferTargetRequest();
                    int receiver = tryParseInt(input);
                    if ((receivingAccount = accManager.searchByNum(receiver)) != null) { // if account exists
                        if (logTransfer(activeAccount.getAccountOwner(), activeAccount, receivingAccount, amt)) {
                            System.out.printf("An amount of $%.2f has been transferred from %s --- %s to %s --- %s.%n",
                                    amt,
                                    activeAccount.getAccountType(), activeAccount.getAccountNumber(),
                                    receivingAccount.getAccountType(), receivingAccount.getAccountNumber());
                            System.out.println();

                            System.out.println("Please press ENTER to continue.");
                            scanner.nextLine();
                            uiMode = modes.CHOOSE_ACCOUNT;
                        } else {
                            System.out.println("Invalid values given, transfering incomplete. Please try again.");
                        }
                    } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Invalid account number, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                    uiMode = modes.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
                /* PAY MODE */
            } else if (uiMode == modes.PAY) { // FIX
                input = nav.displayPayAmtRequest();
                double amt = tryParseAmt(input);
                if (amt > 0) { // if input is a number greater than 0
                    System.out.println();
                    input = nav.displayPayTargetRequest();
                    int receiver = tryParseInt(input);
                    if ((receivingAccount = accManager.searchByNum(receiver)) != null) { // if account exists
                        if (logPayment(activeAccount.getAccountOwner(), receivingAccount.getAccountOwner(),
                                activeAccount, receivingAccount, amt)) {
                            System.out.printf(
                                    "An amount of $%,.2f has been paid from %s %s's %s --- %s to %s %s's %s --- %s.%n",
                                    amt, activeAccount.getAccountOwner().getFirstName(),
                                    activeAccount.getAccountOwner().getLastName(),
                                    activeAccount.getAccountType(), activeAccount.getAccountNumber(),
                                    receivingAccount.getAccountOwner().getFirstName(),
                                    receivingAccount.getAccountOwner().getLastName(),
                                    receivingAccount.getAccountType(), receivingAccount.getAccountNumber());
                            System.out.println();

                            System.out.println("Please press ENTER to continue.");
                            scanner.nextLine();
                            uiMode = modes.CHOOSE_ACCOUNT;
                        } else {
                            System.out.println("Invalid values given, payment incomplete. Please try again.");
                        }
                    } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Incorrect account number, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                    uiMode = modes.CHOOSE_ACCOUNT;
                } else {
                    System.out.println("Unknown command, please try again.");
                }

                System.out.println();
            } else if (uiMode == modes.ADMIN) {
                uiMode = modes.EXIT;
            } else if (uiMode == modes.NEW_CUSTOMER) {
                uiMode = modes.EXIT;
            }
        }
        System.out.println(
                "Thank you for banking with us today! Please press ENTER to commit changes and close the application.");
        scanner.nextLine();
        scanner.close();
        rw.writeChanges(custManager.getCustomers());
    }

    private static final String LOG_FILE = "resources/Log.txt";
    private static final DecimalFormat df = new DecimalFormat("$#,##0.00");

    /**
     * Writes action to log file
     * 
     * @param message String to be written to the log file
     */
    public static void logAction(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }

    /**
     * Executes and logs transfers
     * 
     * @param customer    Customer performing the transfer
     * @param fromAccount Sending Account
     * @param toAccount   Receiving Account
     * @param amount      Amount being transfered
     * @return true if the transfer was successful, false otherwise
     */
    public static boolean logTransfer(Customer customer, Account fromAccount, Account toAccount, double amount) {
        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            String message = String.format(
                    "%s %s transferred %s from %s-%s to %s-%s. %s %s’s Balance for %s-%s: %s. %s %s’s Balance for %s-%s: %s",
                    customer.getFirstName(), customer.getLastName(), df.format(amount), fromAccount.getAccountType(),
                    String.valueOf(fromAccount.getAccountNumber()),
                    toAccount.getAccountType(), String.valueOf(toAccount.getAccountNumber()),
                    customer.getFirstName(), customer.getLastName(), fromAccount.getAccountType(),
                    String.valueOf(fromAccount.getAccountNumber()), df.format(fromAccount.getAccountBalance()),
                    customer.getFirstName(), customer.getLastName(), toAccount.getAccountType(),
                    String.valueOf(toAccount.getAccountNumber()), df.format(toAccount.getAccountBalance()));
            logAction(message);
            return true;
        }
        return false;
    }

    /**
     * Executes and logs payments
     * 
     * @param payer        Customer paying
     * @param payee        Customer being paid
     * @param payerAccount Sending Account
     * @param payeeAccount Receiving Account
     * @param amount       Amount being paid
     * @return true if the payment was successful, false otherwise
     */
    public static boolean logPayment(Customer payer, Customer payee, Account payerAccount, Account payeeAccount,
            double amount) {
        if (payerAccount.withdraw(amount)) {
            payeeAccount.deposit(amount);
            String message = String.format("%s %s paid %s %s %s from %s-%s. %s %s’s New Balance for %s-%s: %s",
                    payer.getFirstName(), payer.getLastName(), payee.getFirstName(), payee.getLastName(),
                    df.format(amount),
                    payerAccount.getAccountType(), String.valueOf(payerAccount.getAccountNumber()),
                    payer.getFirstName(), payer.getLastName(), payerAccount.getAccountType(),
                    String.valueOf(payerAccount.getAccountNumber()), df.format(payerAccount.getAccountBalance()));
            logAction(message);

            String payeeMessage = String.format("%s %s received %s from %s %s. %s %s’s New Balance for %s-%s: %s",
                    payee.getFirstName(), payee.getLastName(), df.format(amount), payer.getFirstName(),
                    payer.getLastName(),
                    payee.getFirstName(), payee.getLastName(), payeeAccount.getAccountType(),
                    String.valueOf(payeeAccount.getAccountNumber()), df.format(payeeAccount.getAccountBalance()));
            logAction(payeeMessage);
            return true;
        }
        return false;
    }

    /**
     * Attempts to parse a String for an int
     * 
     * @param str String to be parsed
     * @return int
     */
    static int tryParseInt(String str) {
        int ret = -1;
        try {
            ret = Integer.parseInt(str);
        } catch (Throwable e) {
        }
        return ret;
    }

    /**
     * Attempts to parse a string for a double and, if successful, return a double
     * rounded to the 100ths place
     * 
     * @param str String to be parsed
     * @return double
     */
    static double tryParseAmt(String str) {
        double ret = -1;
        try {
            ret = Double.parseDouble(str);
        } catch (Throwable e) {
        }
        return (Math.round(ret * 100.00) / 100.00);
    }
}