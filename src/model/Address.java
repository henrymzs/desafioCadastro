package model;

import model.constants.Constant;

public class Address {
    private String number;
    private String road;
    private String city;

    @Override
    public String toString() {
        return "Rua: " + road + ", Num: " + number + ", Cidade: " + city;
    }

    public Address(String number, String road, String city) {
        setRoad(road);
        setNumber(number);
        setCity(city);
    }

    public Address() { }

    public void setRoad(String road) {
        if (road == null || road.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da rua/logradouro é obrigatório.");
        }
        this.road = road.trim();
    }

    public void setNumber(String number) {
        if (number == null || number.trim().isEmpty() || number.equalsIgnoreCase(Constant.NOT_INFORMED)) {
            this.number = Constant.NOT_INFORMED;
            return;
        }
        this.number = number.trim();
    }

    public void setCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("A cidade é obrigatória.");
        }
        this.city = city.trim();
    }

    public String getNumber() {
        return number;
    }

    public String getRoad() {
        return road;
    }

    public String getCity() {
        return city;
    }
}
