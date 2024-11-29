package main;

import account.Account;
import loggable.AccountAction;
import loggable.Deposit;
import loggable.Inquire;
import loggable.Pay;
import loggable.Transfer;
import loggable.Withdraw;

public class ActionFactory {
    public AccountAction chooseAction(Account account){
        return new Inquire(account);
    }

    public AccountAction chooseAction(Account target, double amount, String actionType){
        if(actionType.equals("Deposit")){
            return new Deposit(target, amount);
        }else if(actionType.equals("Withdraw")){
            return new Withdraw(target, amount);
        }else{
            return null;
        }
    }

    public AccountAction chooseAction(Account from, Account to, double amount, String actionType){
        if(actionType.equals("Transfer")){
            return new Transfer(from, amount, to);
        }else if(actionType.equals("Pay")){
            return new Pay(from, amount, to);
        }else{
            return null;
        }
    }

}
