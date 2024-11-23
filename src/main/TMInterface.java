package main;

import account.Account;
import loggable.LoggableManager;

interface TMInterface {
    static final CustomerReadWriter crw = new CustomerReadWriter();
    static final ManagerReadWriter mrw = new ManagerReadWriter();
    static final LoggableManager lm = new LoggableManager();

    /**
     * Perform and log balance inquiry
     * 
     * @param customer
     * @param account
     */
    public abstract void checkBalance(Customer customer, Account account);

    /**
     * Perform and log deposit
     * 
     * @param target
     * @param amount
     * @return
     */
    public abstract boolean deposit(Account target, double amount);

    /**
     * Perform and log withdrawal
     * 
     * @param target
     * @param amount
     * @return
     */
    public abstract boolean withdraw(Account target, double amount);

    /**
     * Perform and log transfer
     * 
     * @param from
     * @param to
     * @param amount
     * @return
     */
    public abstract boolean transfer(Account from, Account to, double amount);

    /**
     * Perform and log payment
     * 
     * @param from
     * @param to
     * @param amount
     * @return
     */
    public abstract boolean pay(Account from, Account to, double amount);

}
