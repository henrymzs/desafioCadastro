package model.enums;

public enum TypePet {
    DOG, CAT;

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