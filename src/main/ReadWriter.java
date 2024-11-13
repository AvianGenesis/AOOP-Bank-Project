package main;

import java.text.DecimalFormat;

interface ReadWriter {

    static final String INPUT_CSV = "resources/BankUsers.csv";
    static final String OUTPUT_CSV = "resources/BankUsersOutput.csv";
    static final String OUTPUT_LOG = "resources/Log.txt";
    static final DecimalFormat DF = new DecimalFormat("$#,##0.00");

    static final String ID = "Identification Number";
    static final String FIRST_NAME = "First Name";
    static final String LAST_NAME = "Last Name";
    static final String DOB = "Date of Birth";
    static final String ADDRESS = "Address";
    static final String CITY = "City";
    static final String STATE = "State";
    static final String ZIP = "Zip";
    static final String PHONE_NUMBER = "Phone Number";
    static final String CHECKING_NUMBER = "Checking Account Number";
    static final String CHECKING_BALANCE = "Checking Starting Balance";
    static final String SAVINGS_NUMBER = "Savings Account Number";
    static final String SAVINGS_BALANCE = "Savings Starting Balance";
    static final String CREDIT_NUMBER = "Credit Account Number";
    static final String CREDIT_MAX = "Credit Max";
    static final String CREDIT_BALANCE = "Credit Starting Balance";

    /**
     * Log a balance inquiry
     * 
     * @param customer
     * @param account
     */
    public abstract void logBalanceInquiry(Customer customer, Account account);

    /**
     * Log a deposit
     * 
     * @param account
     * @param amount
     */
    public abstract void logDeposit(Account account, double amount);

    /**
     * Log a withdrawal
     * 
     * @param account
     * @param amount
     */
    public abstract void logWithdrawal(Account account, double amount);

    /**
     * Log a transfer
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     */
    public abstract void logTransfer(Account fromAccount, Account toAccount, double amount);

    /**
     * Log a payment
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     */
    public abstract void logPayment(Account fromAccount, Account toAccount, double amount);

    /**
     * Function used to output compiled message into log file
     * 
     * @param action
     */
    public abstract void logAction(String action);

}