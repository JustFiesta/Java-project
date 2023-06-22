public class Client {
    //Dane klienta
    private String name, email;
    private int clientID;
    private double phoneNumber;

    //Konstruktor obiektu klient
    public Client(String name, String email, double phoneNumber, int clientID){
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setClientID(clientID);
    }

    //Obsługa klientów
    void addClient(Client newClient) {

    }
    boolean checkForClient(Client client) {

        return false;
    }


    //Gettery i settery
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getPhoneNumber() {
        return phoneNumber;
    }

    public int getClientID() {
        return clientID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(double phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public String toString() {
        return name + " " + email + " " + phoneNumber + " " + clientID;
    }
}
