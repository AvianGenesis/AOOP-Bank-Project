package loggable;

import account.Account;

public class Deposit extends AccountTransaction {

    Deposit(Account from, double amt){
        this.from = from;
        this.amt = amt;
    }

    public boolean action() {
        if (from.canDeposit(amt)) {
            from.deposit(amt);
            return true;
        } else {
            return false;
        }
    }

    public String getReport(){
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("Deposited $%s into %s\n", amt, acctInfo);

        return message;
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("%s deposited %s into %s. %s’s New Balance for %s: %s\n",
                name, amt, acctInfo, name, acctInfo, from.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Deposit";
    }
}
