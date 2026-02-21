package service;

import java.util.List;

import dto.*;

public class PetService {

    public Pet createPetFromAnswers(List<String> answers) {
        try {
            String name = answers.get(0).isBlank() ? Pet.NOT_PROVIDED : answers.get(0);
            String race = answers.get(6).isBlank() ? Pet.NOT_PROVIDED : answers.get(6);

            TypePet type = parseType(answers.get(1));
            Sex sex = parseSex(answers.get(2));

            String[] addressParts = answers.get(3).split(";");
            String number = (addressParts.length > 0 && !addressParts[0].isBlank())
                    ? addressParts[0]
                    : Pet.NOT_PROVIDED;
            String road = (addressParts.length > 1 && !addressParts[1].isBlank())
                    ? addressParts[1]
                    : Pet.NOT_PROVIDED;
            String city = (addressParts.length > 2 && !addressParts[2].isBlank())
                    ? addressParts[2]
                    : Pet.NOT_PROVIDED;
            Address addressObj = new Address(number, road, city);

            Double age = answers.get(4).isBlank() ? 0.0 : Double.parseDouble(answers.get(4).replace(",", "."));
            Double weight = answers.get(5).isBlank() ? 0.0 : Double.parseDouble(answers.get(5).replace(",", "."));

            return new Pet(name, type, sex, race, weight, age, addressObj);

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Peso ou Idade inválidos. Use apenas números e vírgula/ponto.");
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao processar dados: " + e.getMessage());
        }
    }

    private static TypePet parseType(String input) {
        String t = input.toLowerCase();
        if (t.contains("dog") || t.contains("cachorro"))
            return TypePet.DOG;
        if (t.contains("cat") || t.contains("gato"))
            return TypePet.CAT;

        throw new IllegalArgumentException("Tipo inválido! Escolha entre Cachorro ou Gato.");
    }

    private static Sex parseSex(String input) {
        String s = input.toLowerCase();
        if (s.startsWith("m"))
            return Sex.MALE;
        if (s.startsWith("f"))
            return Sex.FEMALE;
        throw new IllegalArgumentException("Sexo inválido (use Macho ou Fêmea)");
    }
}
