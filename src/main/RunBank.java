package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RunBank {

    /**
     * Main method, banking interface
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        CTM ctm = new CTM();
        BMTM bmtm = new BMTM();
        List<Account> accounts = new ArrayList<Account>();
        CustomersManager custManager = ctm.loadCustomers(accounts);
        AccountsManager accManager = new AccountsManager(accounts);

        Scanner scanner = new Scanner(System.in);
        boolean isAdmin = false;
        String input;
        int btn;
        Customer activeCustomer = null;
        Customer receivingCustomer = null;
        Account activeAccount = null;
        Account receivingAccount = null;
        Navigator nav = new Navigator();
        InputParser ip = new InputParser();

        enum Mode { // change to enum
            EXIT,
            CREDENTIALS,
            CHOOSE_ACCOUNT,
            CHOOSE_ACTION, // choosing to inquire about a balance does not require an additional
                           // mode
            DEPOSIT,
            WITHDRAW,
            TRANSFER,
            PAY,
            MAIN,
            ADMIN,
            NEW_CUSTOMER,
            TRANSACTIONS,
        }
        Mode uiMode = Mode.MAIN;

        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        while (uiMode != Mode.EXIT) {
            if (uiMode == Mode.MAIN) { // Main menu
                isAdmin = false;
                input = nav.displayMainMenu();
                btn = ip.tryParseInt(input);
                if (input.trim().toUpperCase().equals("EXIT")) {
                    uiMode = Mode.EXIT;
                } else if ((btn = ip.tryParseInt(input)) != Integer.MIN_VALUE) {
                    switch (btn) {
                        case (1):
                            uiMode = Mode.CREDENTIALS;
                            break;
                        case (2):
                            isAdmin = true;
                            uiMode = Mode.ADMIN;
                            break;
                        case (3):
                            uiMode = Mode.NEW_CUSTOMER;
                            break;
                        default:
                            nav.displayGenericInputError(input);
                            break;
                    }
                } else if (input.trim().toUpperCase().equals("EXIT")) {
                    uiMode = Mode.EXIT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.CREDENTIALS) {
                input = nav.displayCustomerLogin();
                int uId = ip.tryParseInt(input);

                if (uId != -1) {
                    if ((activeCustomer = custManager.searchById(uId)) != null) {
                        uiMode = Mode.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Unrecognized ID, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.MAIN;
                } else {
                    // Handle name input (could be full name or single name)
                    String[] nameParts = input.trim().split("\\s+"); // Split input by spaces
                    if (nameParts.length == 2) {
                        // If two names were entered (first and last)
                        String first = nameParts[0];
                        String last = nameParts[1];
                        if ((activeCustomer = custManager.searchByName(first, last)) != null) {
                            uiMode = Mode.CHOOSE_ACCOUNT;
                        } else {
                            System.out.println("Customer not found with that name.");
                        }
                    } else if (nameParts.length == 1) {
                        // If only one name was entered, search by first or last name
                        List<Customer> possibleCustomers = custManager.searchByName(nameParts[0]);
                        if (possibleCustomers.isEmpty()) {
                            System.out.println("No customers found with that name.");
                        } else if (possibleCustomers.size() == 1) {
                            activeCustomer = possibleCustomers.get(0);
                            uiMode = Mode.CHOOSE_ACCOUNT;
                        } else {
                            // Multiple customers found, ask user to select one
                            System.out.println("Multiple customers found:");
                            for (int i = 0; i < possibleCustomers.size(); i++) {
                                System.out.println((i + 1) + ". " + possibleCustomers.get(i).getFirstName() + " "
                                        + possibleCustomers.get(i).getLastName());
                            }
                            // Ask user to pick a customer
                            input = nav.displayCustomerSelection(); // Method to prompt user for selection
                            int selection = ip.tryParseInt(input);
                            if (selection >= 1 && selection <= possibleCustomers.size()) {
                                activeCustomer = possibleCustomers.get(selection - 1); // Set active customer based on
                                                                                       // selection
                                uiMode = Mode.CHOOSE_ACCOUNT;
                            } else {
                                System.out.println("Invalid selection, please try again.");
                            }
                        }
                    } else {
                        nav.displayGenericInputError(input); // Handle any other invalid input
                    }
                }
                System.out.println();
            } else if (uiMode == Mode.CHOOSE_ACCOUNT) { // Customer chooses account
                input = nav.displayAccounts(activeCustomer, isAdmin);
                btn = ip.tryParseInt(input);
                if (btn >= 1 && btn <= activeCustomer.getAccounts().length) {
                    activeAccount = accManager.searchByNum(activeCustomer.getAccounts()[btn - 1].getAccountNumber());
                    uiMode = Mode.CHOOSE_ACTION;
                } else if (btn == 4) {
                    ctm.generateReport(activeCustomer);
                } else if (isAdmin && btn == 5) {
                    bmtm.generateStatement(activeCustomer);
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.CREDENTIALS;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.CHOOSE_ACTION) {
                input = nav.displayAccountActions(activeAccount);
                btn = ip.tryParseInt(input);
                if (btn >= 1 && btn <= 5) {
                    switch (btn) {
                        case 1:
                            if (isAdmin) {
                                bmtm.checkBalance(activeCustomer, activeAccount);
                            } else {
                                ctm.checkBalance(activeCustomer, activeAccount);
                            }

                            nav.displayBalanceRequest(activeAccount);
                            System.out.println();
                            System.out.println("Please press ENTER to continue.");
                            scanner.nextLine();
                            break;
                        case 2:
                            uiMode = Mode.DEPOSIT;
                            break;
                        case 3:
                            uiMode = Mode.WITHDRAW;
                            break;
                        case 4:
                            uiMode = Mode.TRANSFER;
                            break;
                        case 5:
                            uiMode = Mode.PAY;
                            break;
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.DEPOSIT) { // Deposit mode
                input = nav.displayDepositRequest();
                double amt = ip.tryParseAmt(input);
                if (amt != -1) {
                    if (ctm.deposit(activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been deposited to %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                        uiMode = Mode.CHOOSE_ACTION;
                    } else {
                        System.out.println("Invalid amount, please try again.");
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.WITHDRAW) { // Withdraw mode
                input = nav.displayWithdrawRequest();
                double amt = ip.tryParseAmt(input);
                if (amt != -1) {
                    if (ctm.withdraw(activeAccount, amt)) {
                        System.out.printf("An amount of $%.2f has been withdrawn from %s --- %s.%n", amt,
                                activeAccount.getAccountType(), activeAccount.getAccountNumber());
                        System.out.println();
                        System.out.println("Please press ENTER to continue OR type 1 to donate $1.");
                        input = scanner.nextLine();
                        if (input.equals("1")) {
                            ctm.withdraw(activeAccount, 1);
                        }
                        uiMode = Mode.CHOOSE_ACTION;
                    } else {
                        System.out.println("Invalid amount, please try again.");
                        System.out.println("Please press ENTER to continue.");
                        scanner.nextLine();
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.TRANSFER) { // Transfer mode
                input = nav.displayTransferAmtRequest();
                double amt = ip.tryParseAmt(input);

                if (amt != -1) { // if input is a number
                    System.out.println();
                    input = nav.displayTransferTargetRequest();
                    int receiver = ip.tryParseInt(input);

                    if ((receivingAccount = accManager.searchByNum(receiver)) != null) { // if account exists

                        if (ctm.transfer(activeAccount, receivingAccount, amt)) {
                            System.out.printf("An amount of $%.2f has been transferred from %s --- %s to %s --- %s.%n",
                                    amt,
                                    activeAccount.getAccountType(), activeAccount.getAccountNumber(),
                                    receivingAccount.getAccountType(), receivingAccount.getAccountNumber());
                            System.out.println();

                            System.out.println("Please press ENTER to continue.");
                            scanner.nextLine();
                            uiMode = Mode.CHOOSE_ACCOUNT;
                        } else {
                            System.out.println("Invalid values given, transfering incomplete. Please try again.");
                        }
                    } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                        uiMode = Mode.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Invalid account number, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                    uiMode = Mode.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.PAY) { // Pay mode
                input = nav.displayPayAmtRequest();
                double amt = ip.tryParseAmt(input);

                if (amt != -1) { // if input is a number
                    System.out.println();
                    input = nav.displayPayTargetRequest();
                    int receiver = ip.tryParseInt(input);

                    if ((receivingAccount = accManager.searchByNum(receiver)) != null) { // if account exists

                        if (ctm.pay(activeAccount, receivingAccount, amt)) {
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
                            uiMode = Mode.CHOOSE_ACCOUNT;
                        } else {
                            System.out.println("Invalid values given, payment incomplete. Please try again.");
                        }
                    } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                        uiMode = Mode.CHOOSE_ACCOUNT;
                    } else {
                        System.out.println("Incorrect account number, please try again.");
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) { // if BACK
                    uiMode = Mode.CHOOSE_ACCOUNT;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.ADMIN) {
                input = nav.displayAdminOptions();
                btn = ip.tryParseInt(input);
                if (btn >= 1 && btn <= 2) {
                    switch (btn) {
                        case 1:
                            uiMode = Mode.CREDENTIALS;
                            break;
                        case 2:
                            uiMode = Mode.TRANSACTIONS;
                            break;
                    }
                } else if (input.trim().toUpperCase().equals("BACK")) {
                    uiMode = Mode.MAIN;
                } else {
                    nav.displayGenericInputError(input);
                }

                System.out.println();
            } else if (uiMode == Mode.NEW_CUSTOMER) {// Asking the user for all their inputs for the new account
                String newFN = nav.displayFirstNameReq();
                String newLN = nav.displayLastNameReq();
                String newDOB = nav.displayDOBReq();
                String newAddy = nav.displayAddressReq();
                String newCity = nav.displayCityReq();
                String newState = nav.displayStateReq();
                int newZip = nav.displayZipRequest();
                String newPhoneNum = nav.displayPhoneNumReq();

                // Creates the correct ID and account numbers
                int newID = custManager.getLastCustomerId();
                int newChecking = custManager.getLastChecking();
                int newSaving = custManager.getLastSaving();
                int newCredit = custManager.getLastCredit();

                // display
                System.out.println("----------------------");
                System.out.println("Your ID will be:" + newID);
                System.out.println("Your Cheking account number is: " + newChecking);
                System.out.println("Your Saving account number is: " + newSaving);
                System.out.println("Your Credit account number is: " + newCredit);
                System.out.println("-----------------------");
                System.out.println();

                // creation of the Customer object and accounts
                Customer newC = new Customer(newFN, newLN, newDOB, newAddy, newCity, newState, newZip, newPhoneNum,
                        newID);
                Account newChk = new Checking(newC, newChecking, 0);
                Account newSav = new Saving(newC, newSaving, 0);
                Random rand = new Random();
                int randLimit = rand.nextInt(100, 16000);
                Account newCred = new Credit(newC, newCredit, -1, randLimit);

                Account[] newAccounts = new Account[] { newChk, newSav, newCred };
                newC.setAccounts(newAccounts);
                custManager.addNewCustomer(newC);
                System.out.println();

                System.out.println("Make a Deposit into your savings to activate account ");
                activeAccount = newSav;
                activeCustomer = newC;
                uiMode = Mode.DEPOSIT;

                System.out.println();
            } else if (uiMode == Mode.TRANSACTIONS) {
                input = nav.displayTransactionFileRequest();
                List<String[]> transactions = ctm.readTransactions(input);
                for (String[] line : transactions) {
                    switch (line[3]) {
                        case ("inquires"):
                            if ((activeCustomer = custManager.searchByName(line[0], line[1])) != null) {
                                ctm.checkBalance(activeCustomer, activeCustomer.searchAccounts(line[2]));
                            } else {
                                System.out.println("Inquiry incomplete, customer not found.");
                            }
                            break;
                        case ("deposits"):
                            if ((activeCustomer = custManager.searchByName(line[4], line[5])) != null) {
                                activeAccount = activeCustomer.searchAccounts(line[6]);
                                ctm.deposit(activeAccount, ip.tryParseAmt(line[7]));
                            } else {
                                System.out.println("Deposit incomplete, customer not found.");
                            }
                            break;
                        case ("withdraws"):
                            if ((activeCustomer = custManager.searchByName(line[0], line[1])) != null) {
                                activeAccount = activeCustomer.searchAccounts(line[2]);
                                ctm.withdraw(activeAccount, ip.tryParseAmt(line[7]));
                            } else {
                                System.out.println("Withdrawal incomplete, customer not found.");
                            }
                            break;
                        case ("transfers"):
                            if ((activeCustomer = custManager.searchByName(line[0], line[1])) != null &&
                                    (receivingCustomer = custManager.searchByName(line[4], line[5])) != null) {
                                activeAccount = activeCustomer.searchAccounts(line[2]);
                                receivingAccount = receivingCustomer.searchAccounts(line[6]);
                                ctm.transfer(activeAccount, receivingAccount, ip.tryParseAmt(line[7]));
                            } else {
                                System.out.println("Transfer incomplete, customer(s) not found.");
                            }
                            break;
                        case ("pays"):
                            if ((activeCustomer = custManager.searchByName(line[0], line[1])) != null &&
                                    (receivingCustomer = custManager.searchByName(line[4], line[5])) != null) {
                                activeAccount = activeCustomer.searchAccounts(line[2]);
                                receivingAccount = receivingCustomer.searchAccounts(line[6]);
                                ctm.pay(activeAccount, receivingAccount, ip.tryParseAmt(line[7]));
                            } else {
                                System.out.println("Payment incomplete, customer(s) not found.");
                            }
                            break;
                        default:
                            nav.displayGenericInputError(line[3]);
                            break;
                    }
                }
                uiMode = Mode.ADMIN;

                System.out.println();
            }
        }
        System.out.println(
                "Thank you for banking with us today! Please press ENTER to commit changes and close the application.");
        scanner.nextLine();
        scanner.close();
        ctm.writeChanges(custManager);

    }
}