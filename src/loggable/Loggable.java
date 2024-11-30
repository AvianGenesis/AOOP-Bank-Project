package loggable;

public interface Loggable extends ActionTypes { // all transactions, report generation, new user creation, etc
    public abstract boolean action();

    public abstract String getLog();

    public abstract String getType();

    public abstract String getSuccess();
}
