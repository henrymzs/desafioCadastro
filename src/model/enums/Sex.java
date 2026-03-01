package model.enums;

public enum Sex {
    FEMALE(1), MALE(2);

    private final int value;

    Sex(int value) {
        this.value = value;
    }

    public static Sex fromValue(int value) {
        for (Sex sex : Sex.values()) {
            if (sex.value == value) {
                return sex;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static Sex fromString(String value) {
        if (value == null)
            return null;
        try {
            return Sex.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return fromUserInput(value);
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    public static Sex fromUserInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("O sexo do pet é obrigatório! Use 1 para Fêmea ou 2 para Macho.");
        }

        String cleanInput = input.trim().toLowerCase();

        return switch (cleanInput) {
            case "1", "f", "fêmea", "femea", "feminino" -> FEMALE;
            case "2", "m", "macho", "masculino" -> MALE;
            default -> throw new IllegalArgumentException("Sexo inválido! Use 1 para Fêmea ou 2 para Macho.");
        };
    }
}