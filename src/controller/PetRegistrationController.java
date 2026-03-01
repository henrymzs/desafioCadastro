package controller;

import java.util.Map;

import model.Address;
import model.Pet;
import model.enums.Sex;
import model.enums.TypePet;
import repository.PetRepository;

public class PetRegistrationController {
    private final PetRepository repository;

    public PetRegistrationController(PetRepository repository) {
        this.repository = repository;
    }

    public void register(Map<String, String> data) {
        try {
            Address address = createAddress(data);

            TypePet typePet = TypePet.fromUserInput(data.get("type"));
            Sex sex = Sex.fromUserInput(data.get("sex"));

            Double age = parseDouble(data.get("age"), "Idade");
            Double weight = parseDouble(data.get("weight"), "Peso");

            Pet pet = new Pet(
                    data.get("fullName"),
                    typePet,
                    sex,
                    data.get("race"),
                    weight,
                    age,
                    address);
            repository.save(pet);
            displaySuccess(pet);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado ao registrar: " + e.getMessage());
            e.printStackTrace(); 
        }
    }

    private Double parseDouble(String value, String fieldName) {
        try {
            if (value == null || value.isBlank()) return 0.0;
            return Double.parseDouble(value.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " deve ser um número válido.");
        }
    }
   
    private Address createAddress(Map<String, String> data) {
        return new Address(
                data.get("road"),
                data.get("number"),
                data.get("city"));
    }

    private void displaySuccess(Pet pet) {
        System.out.println("\n==============================");
        System.out.println("PET CADASTRADO COM SUCESSO!");
        System.out.println("==============================");
        System.out.println(pet);
        System.out.println("==============================\n");
    }
}
