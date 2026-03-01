package model;

import java.nio.file.Path;

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
    private Path filePath;

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

    public Pet() {
    }

    public Pet(String fullName, TypePet typePet, Sex sex, String race, Double weight, Double age, Address address) {
        setFullName(fullName); 
        setRace(race); 
        setWeight(weight); 
        setAge(age); 

        if (typePet == null)
            throw new IllegalArgumentException("O tipo de pet é obrigatório");

        this.typePet = typePet;
        this.sex = sex;
        this.address = address;
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

    public void setWeight(Double weight) {
        // Agora aceita Double diretamente, sem precisar de parse interno
        if (weight != null && (weight > 60 || weight < 0.5)) {
            throw new IllegalArgumentException("Peso inválido (0.5kg - 60kg).");
        }
        this.weight = weight;
    }

    public void setAge(Double age) {
        if (age != null && (age > 20 || age < 0)) {
            throw new IllegalArgumentException("Idade deve ser entre 0 e 20 anos.");
        }
        this.age = age;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
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
