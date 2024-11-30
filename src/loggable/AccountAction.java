package loggable;

import account.Account;
import main.Customer;

public abstract class AccountAction implements Loggable, ActionTypes {
    Account from;
    double amt;

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
        System.out.println(getType() + " failed!");
        System.out.println();
    }
}
