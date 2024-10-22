import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        List<Customer> customers = new ArrayList<Customer>();
        final String file = "resources/BankUsers.csv";
        Scanner scanner = new Scanner(System.in);
        String input;

        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            String headers = br.readLine();
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                int id = Integer.parseInt(values[0]);
                String firstName = values[1];
                String lastName = values[2];
                String dob = values[3];
                String address = values[4];
                String city = values[5];
                String state = values[6];
                int zip = Integer.parseInt(values[7]);
                String phone = values[8];
                int chkNum = Integer.parseInt(values[9]);
                double chkBal = Double.parseDouble(values[10]);
                int savNum = Integer.parseInt(values[11]);
                double savBal = Double.parseDouble(values[12]);
                int creNum = Integer.parseInt(values[13]);
                int creMax = Integer.parseInt(values[14]);
                double creBal = Double.parseDouble(values[15]);

                Customer newCust = new Customer(firstName, lastName, dob, address, city, state, zip, phone, id, new int[]{chkNum, savNum, creNum});
                customers.add(newCust);
            }
        }

        System.out.println("Provide input: ");
        input = scanner.nextLine();
        System.out.println(input);
    }
}