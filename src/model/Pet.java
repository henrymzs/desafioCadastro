public class Pet {
    private String fullName;
    private TypePet typePet;
    private Sex sex;
    private String race;
    private Double weight;
    private Double age;
    private Address address;

    public static final String NOT_PROVIDED = "NÃO INFORMADO";

    public Pet(String fullName, TypePet typePet, Sex sex, String race, Double weight, Double age, Address address) {

        setFullName(fullName);
        setRace(race);
        setWeight(weight);
        setAge(age);

        this.typePet = typePet;
        this.sex = sex;
        this.address = address;
    }

    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome completo é obrigatório.");
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
        if (race == null || race.trim().isEmpty()) {
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
        if (age != null && age > 20) {
            throw new IllegalArgumentException("Idade máxima permitida é 20 anos.");
        }
        this.age = age;
    }

    public String getWeightFormatted() {
        return (weight == null) ? NOT_PROVIDED : weight.toString();
    }

    public String getAgeFormatted() {
        return (age == null) ? NOT_PROVIDED : age.toString();
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
