import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
        HashMap<Integer, Account> accounts = new HashMap<>();
        final String file = "resources/BankUsers.csv";
        customers = loadCustomers(file, accounts);

        Scanner scanner = new Scanner(System.in);
        String input;
        int btn;
        Customer activeCustomer = null;
        Account activeAccount = null;
        Account receivingAccount = null;
        Navigator nav = new Navigator();

        class Mode {
            public int EXIT = 0;
            public int CREDENTIALS = 1;
            public int CHOOSE_ACCOUNT = 2;
            public int CHOOSE_ACTION = 3; // choosing to inquire about a balance does not require an additional mode
            public int DEPOSIT = 4;
            public int WITHDRAW = 5;
            public int TRANSFER = 6;
            public int PAY = 7;
            public int MAIN = 8;
            public int ADMIN = 9;
            public int NEW_CUSTOMER = 10;
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
                    if ((activeCustomer = findCustomer(uId, customers)) != null) {
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
                input = nav.displayAccounts(activeCustomer, accounts);
                btn = tryParseInt(input);
                if (btn >= 1 && btn <= activeCustomer.getAccounts().length) {
                    activeAccount = accounts.get(activeCustomer.getAccounts()[btn - 1]);
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
                            logBalanceInquiry(activeAccount.getAccountOwner(), activeAccount);
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
                    if (logDeposit(activeAccount.getAccountOwner(), activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been deposited to %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("invalid amount, please try again.");
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
                    if (logWithdrawal(activeAccount.getAccountOwner(), activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been withdrawn from %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                        uiMode = modes.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("invalid amount, please try again.");
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
                    if (receiver != -1 && accounts.containsKey(receiver)) { // if input is a number and account exists
                        if (logTransfer(activeAccount.getAccountOwner(), activeAccount, accounts.get(receiver), amt)) {
                            System.out.printf("An amount of $%.2f has been transferred from %s --- %s to %s --- %s.%n",
                                    amt,
                                    activeAccount.getAccountType(), activeAccount.getAccountNumber(),
                                    accounts.get(receiver).getAccountType(), accounts.get(receiver).getAccountNumber());
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

                    if (receiver != -1 && accounts.containsKey(receiver)) { // if input is a number and account exists
                        if (logPayment(activeAccount.getAccountOwner(), accounts.get(receiver).getAccountOwner(),
                                activeAccount, accounts.get(receiver), amt)) {
                            System.out.printf(
                                    "An amount of $%.2f has been paid from %s's %s --- %s to %s's %s --- %s.%n",
                                    amt, activeAccount.getAccountOwner().getFirstName(),
                                    activeAccount.getAccountType(), activeAccount.getAccountNumber(),
                                    accounts.get(receiver).getAccountOwner().getFirstName(),
                                    accounts.get(receiver).getAccountType(), accounts.get(receiver).getAccountNumber());
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
        writeChanges(customers, accounts, file);
    }

    /**
     * Reads and parses BankUsers.csv
     * 
     * @param file     The name of the input file
     * @param accounts Hash map of accounts to be written to
     * @return List<Customer>
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static List<Customer> loadCustomers(String file, HashMap<Integer, Account> accounts)
            throws FileNotFoundException, IOException {
        List<Customer> ret = new ArrayList<Customer>();
        String[] values;
        int id;
        String firstName;
        String lastName;
        String dob;
        String address;
        String city;
        String state;
        int zip;
        String phone;
        int chkNum;
        double chkBal;
        int savNum;
        double savBal;
        int creNum;
        int creMax;
        double creBal;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String headers = br.readLine();
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                id = Integer.parseInt(values[0]);
                firstName = values[1];
                lastName = values[2];
                dob = values[3];
                address = values[4];
                city = values[5];
                state = values[6];
                zip = Integer.parseInt(values[7]);
                phone = values[8];
                chkNum = Integer.parseInt(values[9]);
                chkBal = Double.parseDouble(values[10]);
                savNum = Integer.parseInt(values[11]);
                savBal = Double.parseDouble(values[12]);
                creNum = Integer.parseInt(values[13]);
                creMax = Integer.parseInt(values[14]);
                creBal = Double.parseDouble(values[15]);

                Customer newCust = new Customer(firstName, lastName, dob, address, city, state, zip, phone, id,
                        new int[] { chkNum, savNum, creNum });
                ret.add(newCust);
                accounts.put(chkNum, new Checking(newCust, chkNum, chkBal));
                accounts.put(savNum, new Saving(newCust, savNum, savBal));
                accounts.put(creNum, new Credit(newCust, creNum, creBal, creMax));
            }
        }
        return ret;
    }

    /**
     * Finds Customer based on given account Id
     * 
     * @param acctId    Id of account whose owner will be searched
     * @param customers List of customers to be searched through
     * @return Customer
     */
    public static Customer findCustomer(int acctId, List<Customer> customers) {
        for (Customer cust : customers) {
            if (acctId == cust.getidNumber()) {
                return cust;
            }
        }
        return null;
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
     * Logs balance inquiries
     * 
     * @param customer Customer performing the inquiry
     * @param account  The account the customer is performing the inquiry on
     */
    public static void logBalanceInquiry(Customer customer, Account account) {
        String message = String.format("%s %s made a balance inquiry on %s-%s. %s %s’s Balance for %s-%s: %s",
                customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                String.valueOf(account.getAccountNumber()),
                customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                String.valueOf(account.getAccountNumber()), df.format(account.getAccountBalance()));
        logAction(message);
    }

    /**
     * Executes and logs deposits
     * 
     * @param customer Customer performing the deposit
     * @param account  Account being desposited into
     * @param amount   Amount being deposited
     * @return true if the deposit was successful, false otherwise
     */
    public static boolean logDeposit(Customer customer, Account account, double amount) {
        if (account.deposit(amount)) {
            String message = String.format("%s %s deposited %s into %s-%s. %s %s’s New Balance for %s-%s: %s",
                    customer.getFirstName(), customer.getLastName(), df.format(amount), account.getAccountType(),
                    String.valueOf(account.getAccountNumber()),
                    customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                    String.valueOf(account.getAccountNumber()), df.format(account.getAccountBalance()));
            logAction(message);
            return true;
        }
        return false;
    }

    /**
     * Executes and logs withdrawals
     * 
     * @param customer Customer performing the withdrawal
     * @param account  Account being withdrawn from
     * @param amount   Amount being withdrawn
     * @return true if the withdrawal was successful, false otherwise
     */
    public static boolean logWithdrawal(Customer customer, Account account, double amount) {
        if (account.withdraw(amount)) {
            String message = String.format("%s %s withdrew %s in cash from %s-%s. %s %s’s Balance for %s-%s: %s",
                    customer.getFirstName(), customer.getLastName(), df.format(amount), account.getAccountType(),
                    String.valueOf(account.getAccountNumber()),
                    customer.getFirstName(), customer.getLastName(), account.getAccountType(),
                    String.valueOf(account.getAccountNumber()), df.format(account.getAccountBalance()));
            logAction(message);
            return true;
        }
        return false;
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
     * Writes all changes acrued during runtime to BankUsers.csv
     * 
     * @param customers List of customers
     * @param accounts  Hash map of all accounts with changes
     * @param outFile   File for the changes to be written to
     */
    static void writeChanges(List<Customer> customers, HashMap<Integer, Account> accounts, String outFile) {
        String changes = "Identification Number,First Name,Last Name,Date of Birth,Address,City,State,Zip,Phone Number,Checking Account Number,Checking Starting Balance,Savings Account Number,Savings Starting Balance,Credit Account Number,Credit Max,Credit Starting Balance\n";
        for (Customer cust : customers) {
            changes += new String(
                    cust.getidNumber() + "," +
                            cust.getFirstName() + "," +
                            cust.getLastName() + "," +
                            cust.getDob() + "," +
                            cust.getAddress() + "," +
                            cust.getCity() + "," +
                            cust.getState() + "," +
                            cust.getZip() + "," +
                            cust.getPhoneNumber() + "," +
                            cust.getAccounts()[0] + "," +
                            accounts.get(cust.getAccounts()[0]).getAccountBalance() + "," +
                            cust.getAccounts()[1] + "," +
                            accounts.get(cust.getAccounts()[1]).getAccountBalance() + "," +
                            cust.getAccounts()[2] + "," +
                            ((Credit) accounts.get(cust.getAccounts()[2])).getMax() + "," +
                            accounts.get(cust.getAccounts()[2]).getAccountBalance() + "\n");
        }
        try (FileWriter writer = new FileWriter(outFile, false)) {
            writer.write(changes);
        } catch (IOException e) {
            System.err.println("Error writing to Bank Users file: " + e.getMessage());
        }
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