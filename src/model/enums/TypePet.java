package model.enums;

public enum TypePet {
    CAT(1), DOG(2);

    private final int value;

    TypePet(int value) {
        this.value = value;
    }

    public static TypePet fromValue(int value) {
        for (TypePet type : TypePet.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid value: " + value);
    }

    public static TypePet fromString(String value) {
        if (value == null)
            return null;
        try {
            return TypePet.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return fromUserInput(value);
        }
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase();
    }

    public static TypePet fromUserInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do pet é obrigatório! Use 1 para Cachorro ou 2 para Gato.");
        }

        String cleanInput = input.trim().toLowerCase();

        return switch (cleanInput) {
            case "1", "c", "cachorro", "dog" -> DOG;
            case "2", "g", "gato", "cat" -> CAT;
            default -> throw new IllegalArgumentException("Tipo inválido! Use 1 para Cachorro ou 2 para Gato.");
        };
    }
}