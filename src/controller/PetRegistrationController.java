package controller;

import java.util.Map;

import dto.Address;
import dto.Pet;
import repository.PetRepository;

public class PetRegistrationController {
    private final PetRepository repository;

    public PetRegistrationController(PetRepository repository) {
        this.repository = repository;
    }

    public void register(Map<String, String> data) {
        try {
            Address address = createAddress(data);
            Pet pet = new Pet(data, address);

            repository.save(pet);

            displaySuccess(pet);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private Address createAddress(Map<String, String> data) {
        return new Address(
            data.get("road"),
            data.get("number"),
            data.get("city")
        );
    }

    private void displaySuccess(Pet pet) {
        System.out.println("\n==============================");
        System.out.println("PET CADASTRADO COM SUCESSO!");
        System.out.println("==============================");
        System.out.println(pet);
        System.out.println("==============================\n");
    }
}
