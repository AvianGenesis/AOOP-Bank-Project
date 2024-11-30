package loggable;

import account.Account;

public class ActionFactory implements ActionTypes {
    public AccountAction chooseAction(Account account) {
        return new Inquire(account);
    }

    public AccountAction chooseAction(Account target, double amount, String actionType) {
        if (actionType.equals(DEPOSIT)) {
            return new Deposit(target, amount);
        } else if (actionType.equals(WITHDRAW)) {
            return new Withdraw(target, amount);
        } else {
            return null;
        }
    }

    public AccountAction chooseAction(Account from, Account to, double amount, String actionType) {
        if (actionType.equals(TRANSFER)) {
            return new Transfer(from, amount, to);
        } else if (actionType.equals(PAY)) {
            return new Pay(from, amount, to);
        } else {
            return null;
        }
    }

}
