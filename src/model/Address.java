public class Address {
    private String number;
    private String road;
    private String city;
    
    public Address(String number, String road, String city) {
        this.number = number;
        this.road = road;
        this.city = city;
    }
    
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getRoad() {
        return road;
    }
    public void setRoad(String road) {
        this.road = road;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}


