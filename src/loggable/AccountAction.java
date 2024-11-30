package loggable;

import account.Account;
import main.Customer;

public abstract class AccountAction implements Loggable {
    Account from;
    double amt;

    String[] statement = new String[6];

    public abstract String getReport();

    /**
     * @return Pre-formatted String array for Customer's Bank Statement generation
     */
    public String[] getStatement() {
        return statement;
    };

    /**
     * @return Pre-formatted String of Account type and number for printing purposes
     */
    protected String getAcctInfo() {
        return new String(from.getAccountType() + "-" + from.getAccountNumber());
    }

    /**
     * @return Customer who is executing the action
     */
    public Customer getOwner() {
        return from.getAccountOwner();
    }

    /**
     * Print default error message for according type
     */
    protected void printFail() {
        System.out.println(getType() + " failed!");
        System.out.println();
    }
}
