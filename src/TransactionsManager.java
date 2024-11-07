import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class TransactionsManager {
    ReadWriter rw = new ReadWriter();

    public TransactionsManager() {

    }

    public CustomersManager loadCustomers(List<Account> account) throws FileNotFoundException, IOException{
        return rw.loadCustomers(account);
    }

    public void checkBalance(Customer customer, Account account) {
        rw.logBalanceInquiry(customer, account);
    }

    public boolean deposit(Account target, double amount) {
        if(target.deposit(amount)){
            rw.logDeposit(target, amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(Account target, double amount) {
        if(target.withdraw(amount)){
            rw.logWithdrawal(target, amount);
            return true;
        }
        return false;
    }

    public boolean transfer(Account from, Account to, double amount) {
        if(from.withdraw(amount)){
            if(to.deposit(amount)){
                rw.logTransfer(from, to, amount);
                return true;
            } else {
                from.deposit(amount);
            }
        }
        return false;
    }

    public boolean pay(Account from, Account to, double amount) {
        if(from.withdraw(amount)){
            if(to.deposit(amount)){
                rw.logPayment(from, to, amount);
                return true;
            } else {
                from.deposit(amount);
            }
        }
        return false;
    }

    public boolean readTransactions(String transFile) {
        return false;
    }

    public void generateReport(Customer customer) {
        // log generation
    }

    public void newCustomer(Customer customer) {
        // log customer creation
    }

    public void writeChanges(CustomersManager customers){
        rw.writeChanges(customers);
    }
}
