package model;

import java.util.Map;

import model.enums.Sex;
import model.enums.TypePet;

public class Pet {
    private String fullName;
    private TypePet typePet;
    private Sex sex;
    private String race;
    private Double weight;
    private Double age;
    private Address address;

    public static final String NOT_PROVIDED = "NÃO INFORMADO";

    @Override
    public String toString() {
        return "Nome: " + fullName + "\n" +
                "Tipo: " + typePet + "\n" +
                "Sexo: " + (sex == null ? NOT_PROVIDED : sex) + "\n" +
                "Raça: " + race + "\n" +
                "Peso: " + getWeightFormatted() + "\n" +
                "Peso: " + getWeightFormatted() + "\n" +
                "Endereço: " + address;
    }

    public Pet() { } 

    public Pet(Map<String, String> data, Address address) {
        setFullName(data.get("fullName"));
        setRace(data.get("race"));

        this.typePet = parseType(data.get("type"));
        this.sex = parseSex(data.get("sex"));
        this.weight = parseWeight(data.get("weight"));
        this.age = parseAge(data.get("age"));
        this.address = address;
    }

    private TypePet parseType(String value) {
        if (value == null || value.equalsIgnoreCase(NOT_PROVIDED) || value.isBlank()) {
            throw new IllegalArgumentException("O tipo de pet (Cachorro/Gato) é obrigatório");
        }
        return TypePet.fromUserInput(value);
    }

    private Sex parseSex(String value) {
        if (value == null || value.equalsIgnoreCase(NOT_PROVIDED) || value.isBlank()) {
            return null;
        }
        return Sex.fromUserInput(value);
    }

    private Double parseAge(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase(NOT_PROVIDED)) {
            return null;
        }
        try {
            double v = Double.parseDouble(value.replace(",", "."));
            setAge(v);
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor da idade deve ser um número.");
        }
    }

    private Double parseWeight(String value) {
        if (value == null || value.isBlank() || value.equalsIgnoreCase(NOT_PROVIDED)) {
            return null;
        }
        try {
            double v = Double.parseDouble(value.replace(",", "."));
            setWeight(v);
            return v;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O peso deve ser um número válido.");
        }
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty() || fullName.equalsIgnoreCase(NOT_PROVIDED)) {
            this.fullName = NOT_PROVIDED;
            return;
        }

        String trimmed = fullName.trim();

        if (!trimmed.matches("^[A-Za-zÀ-ÖØ-öø-ÿ' -]+$")) {
            throw new IllegalArgumentException("Nome contém caracteres inválidos.");
        }

        if (trimmed.split("\\s+").length < 2) {
            throw new IllegalArgumentException("É necessário informar nome e sobrenome.");
        }

        this.fullName = trimmed;
    }

    public void setRace(String race) {
        if (race == null || race.trim().isEmpty() || race.equalsIgnoreCase(NOT_PROVIDED)) {
            this.race = NOT_PROVIDED;
            return;
        }

        if (!race.matches("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$")) {
            throw new IllegalArgumentException("Raça não pode conter números ou caracteres especiais.");
        }
        this.race = race.trim();
    }

    public void setWeight(Double weigth) {
        if (weigth != null && (weigth > 60 || weigth < 0.5)) {
            throw new IllegalArgumentException("Peso inválido (0.5kg - 60kg).");
        }
        this.weight = weigth;
    }

    public void setAge(Double age) {
        if (age != null && (age > 20 || age < 0)) {
            throw new IllegalArgumentException("Idade deve ser entre 0 e 20 anos.");
        }
        this.age = age;
    }

    public String getWeightFormatted() {
        return (weight == null) ? NOT_PROVIDED : weight + "kg";
    }

    public String getAgeFormatted() {
        return (age == null) ? NOT_PROVIDED : age + " anos";
    }

    public String getFullName() {
        return fullName;
    }

    public TypePet getTypePet() {
        return typePet;
    }

    public void setTypePet(TypePet typePet) {
        this.typePet = typePet;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getRace() {
        return race;
    }

    public double getWeight() {
        return weight;
    }

    public double getAge() {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public static String getNotProvided() {
        return NOT_PROVIDED;
    }

}
