package loggable;

import account.Account;
import main.Customer;

public abstract class DoubleAccountTransaction extends AccountTransaction {
    Account to;

    protected String getToAcctInfo(){
        return new String(to.getAccountType() + "-" + to.getAccountNumber());
    }

    protected Customer getToOwner(){
        return to.getAccountOwner();
    }
}
