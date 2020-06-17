package comp3350.schrodingers.objects;

public class User {

    private int userId = -1;
    private String email = "";  // distinct key
    private String username = "";
    private String password = "";
    private Address address = new Address();
    private Billing billing = new Billing();

    // Constructor
    public User() {}

    // Setters
    public void setUserId(int id){
        userId = id;
    }

    public void setUserName(String name) {
        username = name;
    }

    public void setEmail(String mail) {
        email = mail;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public void setBilling(Billing bill) {
        billing = bill;
    }

    public void setAddress(Address addr) {
        address = addr;
    }

    // Getters
    public int getUserId(){
        return userId;
    }

    public String getUserName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Billing getBilling() {
        return billing;
    }

    public Address getAddress() {
        return address;
    }

    // Conditional
    public boolean noUserName() {
        return username.equals("");
    }

    public boolean noEmail() {
        return email.equals("");
    }

    public static class Address {  // class which stores the address
        private String streetAndNumber;
        private String postalCode;
        private String city;
        private String state;
        private String country;

        public Address() {
            streetAndNumber = "NOADDRESS!";
            postalCode = "";
            state = "";
            country = "";
            city = "";
        }

        public Address(String streetName, String postalCode, String city, String state, String country) {
            this.streetAndNumber = streetName;
            this.postalCode = postalCode;
            this.state = state;
            this.country = country;
            this.city = city;
        }

        // Getters
        public String getAddress() {
            return streetAndNumber;
        }

        public String getPostalCode() {
            return postalCode;
        }

        public String getState() {
            return state;
        }

        public String getCountry() {
            return country;
        }

        public String getCity() {
            return city;
        }

        // Conditional
        public boolean noAddr() {
            return streetAndNumber.equals("NOADDRESS!");
        }

        public boolean noPostal() {
            return postalCode.equals("");
        }

        public boolean noState() {
            return state.equals("");
        }

        public boolean noCountry() {
            return country.equals("");
        }

        public boolean noCity() {
            return city.equals("");
        }

        public String toString(){
            return streetAndNumber+", "+postalCode+", "+city+", "+state+", "+country;
        }
    }

    public static class Billing { // class which stores billing information
        private long cardNumber;
        private String fullName;
        private String expiry;
        private int cvv;

        public Billing() {
            cardNumber = 0L;
            fullName = "";
            expiry = "";
            cvv = 0;
        }

        public Billing(long cardNumber, String fullName, String expiry, int cvv) {
            this.cardNumber = cardNumber;
            this.fullName = fullName;
            this.expiry = expiry;
            this.cvv = cvv;
        }

        // Getters
        public long getCardNumber() {
            return cardNumber;
        }

        public String getFullName() {
            return fullName;
        }

        public String getExpiry() {
            return expiry;
        }

        public int getCvv() {
            return cvv;
        }

        // Conditional
        public boolean noCardNo() {
            return cardNumber == 0L;
        }

        public boolean noCardName() {
            return fullName.equals("");
        }

        public boolean noExpiry() {
            return expiry.equals("");
        }

        public boolean noCvv() {
            return cvv == 0;
        }

        public String toString() {
            return "Visa ending in" + (cardNumber % 1000);
        }
    }
}
