package loggable;

public class ReadTransactions implements Loggable {

    @Override
    public boolean action() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }

    @Override
    public String getLog() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLog'");
    }

    @Override
    public String getType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getType'");
    }

    @Override
    public String getSuccess() {
        String message = String.format("Successfully read in transactions file");

        return message;
    }
    
}
