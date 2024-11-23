package loggable;

import account.Account;

public class Transfer extends DoubleAccountTransaction {
    Transfer(Account from, double amt, Account to) {
        this.from = from;
        this.amt = amt;
        this.to = to;
    }

    public boolean action() {
        if (from.canWithdraw(amt) && to.canDeposit(amt)) {
            from.withdraw(amt);
            to.deposit(amt);
            return true;
        } else {
            return false;
        }
    }

    public String getReport() {
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();
        String toAcctInfo = to.getAccountType() + "-" + to.getAccountNumber();

        String message = String.format("Transfered $%s from %s to %s\n", amt, acctInfo, toAcctInfo);

        return message;
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();
        String toAcctInfo = to.getAccountType() + "-" + to.getAccountNumber();

        String message = String.format("%s transfered %s from %s to %s. New balance for %s: %s. New balance for %s: %s.",
                name, amt, acctInfo, toAcctInfo, acctInfo, from.getAccountBalance(), toAcctInfo, to.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Transfer";
    }
}
