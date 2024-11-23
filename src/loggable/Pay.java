package loggable;

import account.Account;

public class Pay extends DoubleAccountTransaction {
    Pay(Account from, double amt, Account to) {
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

        String message = String.format("Paid $%s from %s to %s\n", amt, acctInfo, toAcctInfo);

        return message;
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String toName = to.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();
        String toAcctInfo = to.getAccountType() + "-" + to.getAccountNumber();

        String message = String.format("%s paid %s to %s from %s. New balance for %s: %s. New balance for %s: %s.",
                name, amt, toName, acctInfo, acctInfo, from.getAccountBalance(), toAcctInfo, to.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Pay";
    }
}
