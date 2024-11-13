package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class CTM implements TMInterface {

    public CTM(){
        
    }

    public CustomersManager loadCustomers(List<Account> account) throws FileNotFoundException, IOException{
        return crw.loadCustomers(account);
    }

    public void checkBalance(Customer customer, Account account) {
        crw.logBalanceInquiry(customer, account);
    }

    public boolean deposit(Account target, double amount) {
        if(target.deposit(amount)){
            crw.logDeposit(target, amount);
            return true;
        }
        System.out.println("Unable to deposit to " + target.getAccountType() + " -- " + target.getAccountNumber());
        System.out.println("Requested: $" + amount);
        System.out.println();
        return false;
    }

    public boolean withdraw(Account target, double amount) {
        if(target.withdraw(amount)){
            crw.logWithdrawal(target, amount);
            return true;
        }
        System.out.println("Unable to withdraw from " + target.getAccountType() + " -- " + target.getAccountNumber());
        System.out.println("Requested: $" + amount);
        System.out.println("Available: $" + target.getAccountBalance());
        System.out.println();
        return false;
    }

    public boolean transfer(Account from, Account to, double amount) {
        if (from != to) {
            if (from.withdraw(amount)) {
                if (to.deposit(amount)) {
                    crw.logTransfer(from, to, amount);
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

    public boolean pay(Account from, Account to, double amount) {
        if (from.getAccountOwner() != to.getAccountOwner()) {
            if (from.withdraw(amount)) {
                if (to.deposit(amount)) {
                    crw.logPayment(from, to, amount);
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


    public List<String[]> readTransactions(String transFile) throws FileNotFoundException, IOException {
        // log transactions execution
        return crw.readTransactions(transFile);
    }

    public void generateReport(Customer customer) throws IOException {
        crw.generateReport(customer);
    }

    public void generateStatement(Customer customer) throws IOException {
        crw.generateStatement(customer);
    }

    public void newCustomer(Customer customer) {
        // log customer creation
    }

    public void writeChanges(CustomersManager customers){
        crw.writeChanges(customers);
    }
}

