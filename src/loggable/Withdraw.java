package loggable;

import account.Account;

public class Withdraw extends AccountTransaction {

    Withdraw(Account from, double amt) {
        this.from = from;
        this.amt = amt;
    }

    public boolean action() {
        if (from.canWithdraw(amt)) {
            from.withdraw(amt);
            return true;
        } else {
            return false;
        }
    }

    public String getReport(){
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("Withdrew $%s from %s\n", amt, acctInfo);

        return message;
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("%s withdrew %s from %s. %s’s New Balance for %s: %s",
                name, amt, acctInfo, name, acctInfo, from.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Withdraw";
    }
}
