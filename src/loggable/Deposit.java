package loggable;

import account.Account;

public class Deposit extends AccountAction {

    public Deposit(Account from, double amt) {
        this.from = from;
        this.amt = amt;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = DEPOSIT;
        statement[3] = "";
        statement[4] = "";
        statement[5] = String.valueOf(amt);
    }

    public boolean action() {
        if (from.canDeposit(amt)) {
            from.deposit(amt);
            return true;
        } else {
            printFail();
            return false;
        }
    }

    public String getReport() {
        String message = String.format("Deposited $%,.2f into %s\n", amt, getAcctInfo());

        return message;
    }

    public String getLog() {
        String name = getOwner().getName();

        String message = String.format("%s deposited $%,.2f into %s. %s’s New Balance for %s: $%,.2f\n",
                name, amt, getAcctInfo(), name, getAcctInfo(), from.getAccountBalance());

        return message;
    }

    public String getSuccess() {
        String message = String.format("Successfully deposited $%,.2f into %s\n", amt, getAcctInfo());

        return message;
    }

    public String getType() {
        return DEPOSIT;
    }
}
