package loggable;

import account.Account;
import main.Customer;

public abstract class DoubleAccountAction extends AccountAction {
    Account to;

    /**
     * @return Pre-formatted String of Account type and number of receiving Account
     *         for printing purposes
     */
    protected String getToAcctInfo() {
        return new String(to.getAccountType() + "-" + to.getAccountNumber());
    }

    /**
     * @return Customer who is receiving the action
     */
    protected Customer getToOwner() {
        return to.getAccountOwner();
    }
}
