package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import IO.BankUsersIO;
import IO.CustomerReadWriter;
import account.Account;
import loggable.AccountAction;
import loggable.ActionFactory;
import loggable.GenerateStatement;
import loggable.Loggable;

public class TransactionsManager {
    static final BankUsersIO buio = new BankUsersIO();
    static final CustomerReadWriter rw = new CustomerReadWriter();
    static final ActionFactory af = new ActionFactory();

    /**
     * Initial load-in of BankUsers.csv
     * 
     * @param account
     * @return CustomersManager
     * @throws FileNotFoundException
     * @throws IOException
     */
    public CustomersManager loadCustomers(List<Account> account) throws FileNotFoundException, IOException {
        return buio.loadCustomers(account);
    }

    /**
     * Record a customer's balance inquiry
     * 
     * @param customer
     * @param account
     */
    public void checkBalance(Account account) {
        executeAccountAction(af.chooseAction(account));
    }

    /**
     * @param target
     * @param amount
     * @return boolean
     */
    public boolean deposit(Account target, double amount, String actionType) {
        return executeAccountAction(af.chooseAction(target, amount, actionType));
    }

    /**
     * @param target
     * @param amount
     * @return boolean
     */
    public boolean withdraw(Account target, double amount, String actionType) {
        return executeAccountAction(af.chooseAction(target, amount, actionType));
    }

    /**
     * @param from
     * @param to
     * @param amount
     * @return boolean
     */
    public boolean transfer(Account from, Account to, double amount, String actionType) {
        return executeAccountAction(af.chooseAction(from, to, amount, actionType));
    }

    /**
     * @param from
     * @param to
     * @param amount
     * @return boolean
     */
    public boolean pay(Account from, Account to, double amount, String actionType) {
        return executeAccountAction(af.chooseAction(from, to, amount, actionType));
    }

    /**
     * @param transFile
     * @return List of String[]
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String[]> readTransactions(String transFile) throws FileNotFoundException, IOException {
        // log transactions execution
        return rw.readTransactions(transFile);
    }

    /**
     * @param customer
     * @throws IOException
     */
    public void generateReport(Customer customer) throws IOException {
        rw.generateReport(customer);
    }

    /**
     * @param customer
     * @throws IOException
     */
    public void generateStatement(Customer customer) throws IOException {
        rw.generateStatement(customer);
        executeLoggable(new GenerateStatement(customer));
    }

    /**
     * @param customer
     */
    public void newCustomer(Customer customer) {
        // log customer creation
    }

    private boolean executeAccountAction(AccountAction action) {
        if (action.action()) {
            action.getOwner().appendActions(action);
            System.out.println(action.getSuccess());
            rw.logAction(action.getLog());
            return true;
        }
        return false;
    }

    private void executeLoggable(Loggable action) {
        action.action();
        System.out.println(action.getSuccess());
        rw.logAction(action.getLog());
    }

    /**
     * @param customers
     */
    public void writeChanges(CustomersManager customers) {
        buio.writeChanges(customers);
    }
}
