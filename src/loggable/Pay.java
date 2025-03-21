package loggable;

import account.Account;

public class Pay extends DoubleAccountAction {
    public Pay(Account from, double amt, Account to) {
        this.from = from;
        this.amt = amt;
        this.to = to;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = PAY;
        statement[3] = String.valueOf(getToOwner().getidNumber());
        statement[4] = String.valueOf(to.getAccountNumber());
        statement[5] = String.valueOf(amt);
    }

    /**
     * Verifies validity of and executes pay
     * 
     * @return boolean
     */
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

    /**
     * @return String
     */
    public String getReport() {
        String message = String.format("Paid $%,.2f from %s to %s\n", amt, getAcctInfo(), getToAcctInfo());

        return message;
    }

    /**
     * @return String
     */
    public String getLog() {
        String name = getOwner().getName();
        String toName = getToOwner().getName();

        String message = String.format(
                "%s paid $%,.2f to %s from %s. New balance for %s: $%,.2f. New balance for %s: $%,.2f.\n",
                name, amt, toName, getAcctInfo(), getAcctInfo(), from.getAccountBalance(), getToAcctInfo(),
                to.getAccountBalance());

        return message;
    }

    /**
     * @return String
     */
    public String getSuccess() {
        String message = String.format("Successfully paid $%,.2f from %s to %s\n", amt, getAcctInfo(), getToAcctInfo());

        return message;
    }

    /**
     * @return String
     */
    public String getType() {
        return PAY;
    }
}
