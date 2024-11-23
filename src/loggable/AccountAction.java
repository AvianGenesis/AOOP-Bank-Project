package loggable;

import account.Account;

public abstract class AccountAction implements Loggable {
    Account from;

    public abstract String getReport();
}
