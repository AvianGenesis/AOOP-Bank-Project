package loggable;

import account.Account;
import main.Customer;

public abstract class AccountAction implements Loggable {
    Account from;
    double amt;
    Account to;

    String[] statement = new String[6];

    public abstract String getReport();

    public String[] getStatement() {
        return statement;
    };

    protected String getAcctInfo() {
        return new String(from.getAccountType() + "-" + from.getAccountNumber());
    }

    public Customer getOwner() {
        return from.getAccountOwner();
    }

    protected void printFail() {
        System.err.println(getType() + " failed!");
    }
}
