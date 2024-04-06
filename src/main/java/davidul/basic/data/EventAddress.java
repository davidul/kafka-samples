package davidul.basic.data;

public class EventAddress {
    private String city;
    private String state;
    private String streetAddress;
    private String country;
    private String firstName;
    private String lastName;

    public EventAddress(String city, String state, String streetAddress, String country, String firstName, String lastName) {
        this.city = city;
        this.state = state;
        this.streetAddress = streetAddress;
        this.country = country;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
