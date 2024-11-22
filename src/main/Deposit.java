package main;

public class Deposit extends AccountAction {

    protected boolean isValid(){
        return isPositive();
    }

    public String getLog() {
        String name = from.getAccountOwner().getName();
        String acctInfo = from.getAccountType() + "-" + from.getAccountNumber();

        String message = String.format("%s deposited %s into %s. %s’s New Balance for %s: %s",
                name, amt, acctInfo, name, acctInfo, from.getAccountBalance());

        return message;
    }

    public String getType() {
        return "Deposit";
    }
}
