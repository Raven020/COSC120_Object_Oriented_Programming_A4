public class Geek {

    private String name;
    private long phoneNumber;

    /**
     * Constructor for a geek objec
     * 
     * @param geeks name and phone nume
     */
    public Geek(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter for getting a geeks name
     * 
     * @return geeks name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for getting a geeks phone number
     * 
     * @return geeks phone number
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }
}
