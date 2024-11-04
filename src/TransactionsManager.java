public class TransactionsManager {
    ReadWriter rw = new ReadWriter();

    public TransactionsManager() {

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
        return false;
    }

    public boolean pay(Account from, Account to, double amount) {
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
}
