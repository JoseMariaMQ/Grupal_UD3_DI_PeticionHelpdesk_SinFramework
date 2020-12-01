package model;

public class Services {
    private int id;
    private String user;
    private int phone;
    private String email;
    private String city;
    private int cp;
    private String services;

    public Services(String user, int phone, String email, String city, int cp, String services) {
        this.user = user;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.cp = cp;
        this.services = services;
    }

    public Services(int id, String user, int phone, String email, String city, int cp, String services) {
        this.id = id;
        this.user = user;
        this.phone = phone;
        this.email = email;
        this.city = city;
        this.cp = cp;
        this.services = services;
    }

    public Services() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }
}
