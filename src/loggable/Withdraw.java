package loggable;

import account.Account;

public class Withdraw extends AccountAction {

    public Withdraw(Account from, double amt) {
        this.from = from;
        this.amt = amt;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = WITHDRAW;
        statement[3] = "";
        statement[4] = "";
        statement[5] = String.valueOf(amt);
    }

    public boolean action() {
        if (from.canWithdraw(amt)) {
            from.withdraw(amt);
            return true;
        } else {
            printFail();
            return false;
        }
    }

    public String getReport(){
        String message = String.format("Withdrew $%,.2f from %s\n", amt, getAcctInfo());

        return message;
    }

    public String getLog() {
        String name = getOwner().getName();

        String message = String.format("%s withdrew $%,.2f from %s. %s’s New Balance for %s: $%,.2f",
                name, amt, getAcctInfo(), name, getAcctInfo(), from.getAccountBalance());

        return message;
    }

    public String getSuccess() {
        String message = String.format("Successfully withdrew $%,.2f from %s\n", amt, getAcctInfo());

        return message;
    }

    public String getType() {
        return WITHDRAW;
    }
}
