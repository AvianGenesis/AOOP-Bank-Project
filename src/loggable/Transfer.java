package loggable;

import account.Account;

public class Transfer extends DoubleAccountAction {
    public Transfer(Account from, double amt, Account to) {
        this.from = from;
        this.amt = amt;
        this.to = to;

        statement[0] = String.valueOf(getOwner().getidNumber());
        statement[1] = String.valueOf(from.getAccountNumber());
        statement[2] = TRANSFER;
        statement[3] = String.valueOf(getToOwner().getidNumber());
        statement[4] = String.valueOf(to.getAccountNumber());
        statement[5] = String.valueOf(amt);
    }

    /**
     * Verifies validity of and executes transfer
     * 
     * @return boolean
     */
    public boolean action() {
        if (from != to) {
            if (from.canWithdraw(amt) && to.canDeposit(amt)) {
                from.withdraw(amt);
                to.deposit(amt);
                return true;
            } else {
                printFail();
                return false;
            }
        }
        System.out.println("Cannot transfer to same account!");
        return false;
    }

    /**
     * @return String
     */
    public String getReport() {
        String message = String.format("Transferred $%,.2f from %s to %s\n", amt, getAcctInfo(), getToAcctInfo());

        return message;
    }

    /**
     * @return String
     */
    public String getLog() {
        String name = getOwner().getName();

        String message = String.format(
                "%s transferred $%,.2f from %s to %s. New balance for %s: $%,.2f. New balance for %s: $%,.2f.\n",
                name, amt, getAcctInfo(), getToAcctInfo(), getAcctInfo(), from.getAccountBalance(), getToAcctInfo(),
                to.getAccountBalance());

        return message;
    }

    /**
     * @return String
     */
    public String getSuccess() {
        String message = String.format("Successfully transferred $%,.2f from %s to %s\n", amt, getAcctInfo(),
                getToAcctInfo());

        return message;
    }

    /**
     * @return String
     */
    public String getType() {
        return TRANSFER;
    }
}
