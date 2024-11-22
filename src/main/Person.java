package main;

/**
 * Represents a person with personal information.
 */
public class Person {
    
    /** The first name of the person */
    private String firstName;
    
    /** The last name of the person */
    private String lastName;
    
    /** The date of birth of the person */
    private String dob;
    
    /** The address of the person */
    private String address;
    
    /** The city of residence */
    private String city;
    
    /** The state of residence */
    private String state;
    
    /** The zip code of the address */
    private int zip;
    
    /** The phone number of the person */
    private String phoneNumber;

    /**
     * Default constructor for the Person class.
     */
    public Person() {
        // Default constructor
    }

    /**
     * Constructor to initialize a Person with personal details.
     * 
     * @param firstName the first name of the person
     * @param lastName the last name of the person
     * @param dob the date of birth of the person
     * @param address the address of the person
     * @param city the city of residence
     * @param state the state of residence
     * @param zip the zip code of the address
     * @param phoneNumber the phone number of the person
     */
    public Person(String firstName, String lastName, String dob, String address, String city, String state, int zip, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the first name of the person.
     * 
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * 
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the person.
     * 
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * 
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName(){
        return firstName + " " + lastName;
    }

    /**
     * Gets the date of birth of the person.
     * 
     * @return the date of birth
     */
    public String getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the person.
     * 
     * @param dob the new date of birth
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * Gets the address of the person.
     * 
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the person.
     * 
     * @param address the new address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the city of residence.
     * 
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of residence.
     * 
     * @param city the new city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the state of residence.
     * 
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of residence.
     * 
     * @param state the new state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Gets the zip code of the address.
     * 
     * @return the zip code
     */
    public int getZip() {
        return zip;
    }

    /**
     * Sets the zip code of the address.
     * 
     * @param zip the new zip code
     */
    public void setZip(int zip) {
        this.zip = zip;
    }

    /**
     * Gets the phone number of the person.
     * 
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     * 
     * @param phoneNumber the new phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Displays the person's information, including name, address, and phone number.
     */
    public void displayPersonInfo() {
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Address: " + address);
        System.out.println("Phone Number: " + phoneNumber);
    }
}
