package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class BMTM implements TMInterface {

    public BMTM(){

    }

    
    
    /** 
     * @param customer
     * @param account
     */
    public void checkBalance(Customer customer, Account account) {
        mrw.logBalanceInquiry(customer, account);
    }

    
    /** 
     * @param target
     * @param amount
     * @return boolean
     */
    public boolean deposit(Account target, double amount) {
        if(target.deposit(amount)){
            mrw.logDeposit(target, amount);
            return true;
        }
        System.out.println("Unable to deposit to " + target.getAccountType() + " -- " + target.getAccountNumber());
        System.out.println("Requested: $" + amount);
        System.out.println();
        return false;
    }

    
    /** 
     * @param target
     * @param amount
     * @return boolean
     */
    public boolean withdraw(Account target, double amount) {
        if(target.withdraw(amount)){
            mrw.logWithdrawal(target, amount);
            return true;
        }
        System.out.println("Unable to withdraw from " + target.getAccountType() + " -- " + target.getAccountNumber());
        System.out.println("Requested: $" + amount);
        System.out.println("Available: $" + target.getAccountBalance());
        System.out.println();
        return false;
    }

    
    /** 
     * @param from
     * @param to
     * @param amount
     * @return boolean
     */
    public boolean transfer(Account from, Account to, double amount) {
        if (from != to) {
            if (from.withdraw(amount)) {
                if (to.deposit(amount)) {
                    mrw.logTransfer(from, to, amount);
                    return true;
                } else {
                    from.deposit(amount);
                    System.out.println("Unable to deposit to " + to.getAccountType() + " -- " + to.getAccountNumber());
                    System.out.println("Requested: $" + amount);
                    System.out.println();
                    return false;
                }
            }
            System.out.println("Unable to withdraw from " + from.getAccountType() + " -- " + from.getAccountNumber()
                    + " for transfer");
            System.out.println("Requested: $" + amount);
            System.out.println("Available: $" + from.getAccountBalance());
            System.out.println();
            return false;
        }
        System.out.println("Cannot transfer to same account");
        System.out.println();
        return false;
    }

    
    /** 
     * @param from
     * @param to
     * @param amount
     * @return boolean
     */
    public boolean pay(Account from, Account to, double amount) {
        if (from.getAccountOwner() != to.getAccountOwner()) {
            if (from.withdraw(amount)) {
                if (to.deposit(amount)) {
                    mrw.logPayment(from, to, amount);
                    return true;
                } else {
                    from.deposit(amount);
                    System.out.println("Unable to deposit to " + to.getAccountType() + " -- " + to.getAccountNumber());
                    System.out.println("Requested: $" + amount);
                    System.out.println();
                    return false;
                }
            }
            System.out.println("Unable to withdraw from " + from.getAccountType() + " -- " + from.getAccountNumber()
                    + " for pay");
            System.out.println("Requested: $" + amount);
            System.out.println("Available: $" + from.getAccountBalance());
            System.out.println();
            return false;
        }
        System.out.println("Cannot pay same account");
        System.out.println();
        return false;
    }


    
    /** 
     * @param transFile
     * @return List of String[]
     * @throws FileNotFoundException
     * @throws IOException
     */
    public List<String[]> readTransactions(String transFile) throws FileNotFoundException, IOException {
        // log transactions execution
        return mrw.readTransactions(transFile);
    }

    
    /** 
     * @param customer
     * @throws IOException
     */
    public void generateReport(Customer customer) throws IOException {
        crw.generateReport(customer);
    }

    
    /** 
     * @param customer
     * @throws IOException
     */
    public void generateStatement(Customer customer) throws IOException {
        crw.generateStatement(customer);
    }

   
}
