package loggable;

import account.Account;

public class Pay extends DoubleAccountTransaction {
    public Pay(Account from, double amt, Account to) {
        this.from = from;
        this.amt = amt;
        this.to = to;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = "Pay";
        statement[3] = String.valueOf(getToOwner().getidNumber());
        statement[4] = String.valueOf(to.getAccountNumber());
        statement[5] = String.valueOf(amt);
    }

    public boolean action() {
        if (getOwner() != getToOwner()) {
            if (from.canWithdraw(amt) && to.canDeposit(amt)) {
                from.withdraw(amt);
                to.deposit(amt);
                return true;
            } else {
                printFail();
                return false;
            }
        }
        System.out.println("Cannot pay yourself!");
        return false;
    }

    public String getReport() {
        String message = String.format("Paid $%,.2f from %s to %s\n", amt, getAcctInfo(), getToAcctInfo());

        return message;
    }

    public String getLog() {
        String name = getOwner().getName();
        String toName = getToOwner().getName();

        String message = String.format(
                "%s paid $%,.2f to %s from %s. New balance for %s: $%,.2f. New balance for %s: $%,.2f.",
                name, amt, toName, getAcctInfo(), getAcctInfo(), from.getAccountBalance(), getToAcctInfo(),
                to.getAccountBalance());

        return message;
    }

    public String getSuccess() {
        String message = String.format("Successfully paid $%,.2f from %s to %s\n", amt, getAcctInfo(), getToAcctInfo());

        return message;
    }

    public String getType() {
        return "Pay";
    }
}
