package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Pet;
import model.enums.FilterType;
import model.enums.TypePet;
import repository.PetRepository;
import service.PetServiceSearch;

public class PetSearchController {
    private final PetRepository repository;
    private final PetServiceSearch petServiceSearch;

    public PetSearchController(PetRepository repository, PetServiceSearch petServiceSearch) {
        this.repository = repository;
        this.petServiceSearch = petServiceSearch;
    }

    public List<Pet> filterByCriterion(TypePet typePet, Map<FilterType, String> filters) {
        List<Pet> allPets = new ArrayList<>(repository.findAll().values());

        List<Pet> filteredPets = allPets.stream()
                .filter(p -> p.getTypePet() == typePet)
                .filter(p -> checkFilter(p, filters))
                .collect(Collectors.toList());

        System.out.println("\n--- RESULTADO DA BUSCA ---");
        if (filteredPets.isEmpty()) {
            System.out.println("[!] Nenhum pet encontrado com os critérios informados.");
        } else {
            for (int i = 0; i < filteredPets.size(); i++) {
                System.out.println((i + 1) + ". " + filteredPets.get(i));
            }
            System.out.println("--------------------------");
        }

        return filteredPets;
    }

    private boolean checkFilter(Pet p, Map<FilterType, String> filters) {
        for (Map.Entry<FilterType, String> entry : filters.entrySet()) {
            String value = entry.getValue().toLowerCase();
            boolean match = switch (entry.getKey()) {
                case NAME -> p.getFullName().toLowerCase().contains(value);
                case SEX -> p.getSex().toString().toLowerCase().startsWith(value.substring(0, 1));
                case RACE -> p.getRace().toLowerCase().contains(value);
                case AGE -> Math.abs(p.getAge() - Double.parseDouble(value)) < 0.1;
                case WEIGHT -> Math.abs(p.getWeight() - Double.parseDouble(value)) < 0.1;
                default -> throw new IllegalArgumentException("Unexpected value: " + entry.getKey());
            };
            if (!match)
                return false;
        }
        return true;
    }

    public void listAllPets() throws InterruptedException {
        List<Pet> pets = petServiceSearch.listAll();
        if (pets.isEmpty()) {
            System.out.println("Nenhum animal de estimação encontrado");
        } else {
            int i = 0;
            for (Pet pet : pets) {
                i++;
                System.out.print(i + ". ");
                System.out.println(pet);
            }
        }
    }
}
