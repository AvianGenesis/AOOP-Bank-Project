package main;

import account.Account;
import loggable.AccountAction;
import loggable.Deposit;
import loggable.Inquire;
import loggable.Pay;
import loggable.Transfer;
import loggable.Withdraw;


/**
 * The ActionFactory class is responsible for creating instances of various 
 * account-related actions such as deposit, withdrawal, transfer, or inquiry, 
 * based on the provided parameters.
 */
public class ActionFactory {
    /**
     * Creates an account action for inquiring account details.
     * 
     * @param account The account on which the inquiry action is performed.
     * @return An Inquire action object for the given account.
     */
    public AccountAction chooseAction(Account account){
        return new Inquire(account);
    }

    /**
     * Creates an account action for deposit or withdrawal based on the action type.
     * 
     * @param target The account on which the action is performed.
     * @param amount The amount of money involved in the action.
     * @param actionType The type of action, either "Deposit" or "Withdraw".
     * @return A Deposit or Withdraw action object based on the actionType, or null if the actionType is not recognized.
     */
    public AccountAction chooseAction(Account target, double amount, String actionType){
        if(actionType.equals("Deposit")){
            return new Deposit(target, amount);
        }else if(actionType.equals("Withdraw")){
            return new Withdraw(target, amount);
        }else{
            return null;
        }
    }

    /**
     * Creates an account action for transferring or paying between two accounts.
     * 
     * @param from The source account from which the amount is transferred or paid.
     * @param to The target account to which the amount is transferred or paid.
     * @param amount The amount of money to be transferred or paid.
     * @param actionType The type of action, either "Transfer" or "Pay".
     * @return A Transfer or Pay action object based on the actionType, or null if the actionType is not recognized.
     */
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
