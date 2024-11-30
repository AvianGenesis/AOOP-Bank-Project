package loggable;

public interface Loggable extends ActionTypes { // all transactions, report generation, new user creation, etc

    /**
     * Executes the action of the associated class
     * 
     * @return Boolean of whether action was successful or not
     */
    public abstract boolean action();

    /**
     * 
     * @return Pre-formatted String for log-writing of action
     */
    public abstract String getLog();

    /**
     * 
     * @return String value of final static type
     */
    public abstract String getType();

    /**
     * 
     * @return Pre-formatted String of success message
     */
    public abstract String getSuccess();
}
