package model.enums;

public enum Sex {
    MALE, FEMALE;

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