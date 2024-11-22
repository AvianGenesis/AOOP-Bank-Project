package main;

public abstract class AccountAction implements Action {
    Account from;
    double amt;

    protected abstract boolean isValid();

    protected boolean isPositive(){
        return amt > 0.0;
    }
}