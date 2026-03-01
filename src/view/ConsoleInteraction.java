package view;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import controller.PetRegistrationController;
import controller.PetSearchController;
import model.Address;
import model.Pet;
import model.enums.FilterType;
import model.enums.Sex;
import model.enums.TypePet;

public class ConsoleInteraction {
    private final PetRegistrationController registrationController;
    private final PetSearchController searchController;
    private final Scanner scanner;

    public ConsoleInteraction(PetRegistrationController registrationController, PetSearchController searchController) {
        this.registrationController = registrationController;
        this.searchController = searchController;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        while (running) {
            displayMenu();
            String input = scanner.nextLine();
            try {
                int userChoice = Integer.parseInt(input);
                if (userChoice == 0) {
                    running = false;
                    System.out.println("Encerrando o sistema...");
                } else {
                    handleMainMenuOption(userChoice);
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite apenas números.");
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private void displayMenu() {
        System.out.println("----MENU PRINCIPAL----");
        System.out.println("1 - Cadastrar Novo Pet");
        System.out.println("2 - Listar todos os pets cadastrados");
        System.out.println("3 - Listar animas com base em criterio");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void handleMainMenuOption(int userChoice) {
        switch (userChoice) {
            case 1 -> {
                Map<String, String> petData = collectPetData();
                registrationController.register(petData);
            }
            case 2 -> {
                System.out.println("\n=================================");
                System.out.println("Pets Registrados:");
                System.out.println("=================================");
                try {
                    searchController.listAllPets();
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro ao listar os animais de estimação: " + e.getMessage());
                }
            }
            case 3 -> {
                try {
                    handleListPetMenu();
                } catch (Exception e) {
                    System.out.println("Ocorreu um erro ao filtrar animais de estimação: " + e.getMessage());
                }
            }
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
    }

    public void handleListPetMenu() {
        TypePet typePet = null;

        while (true) {
            System.out.println("\nEscolha o tipo de pet para busca (1 - Gato / 2 - Cachorro / 0 - Voltar): ");
            String input = scanner.nextLine();

            if (input.equals("0"))
                return;
            if (input.equals("1") || input.equals("2")) {
                typePet = (input.equals("1")) ? TypePet.CAT : TypePet.DOG;
                break;
            }
            System.out.println("Opção inválida!");
        }

        Map<FilterType, String> filters = new HashMap<>();
        Set<Integer> usedCriteria = new HashSet<>();
        int maxFilters = 2;
        int filtersAdded = 0;

        while (filtersAdded < maxFilters) {
            System.out.println("\n--- Filtros Disponíveis ---");
            System.out.println("1. Nome | 2. Sexo | 3. Idade | 4. Peso | 5. Raça | 6. Cidade | 0. Sair");
            System.out.print("Escolha um critério: ");

            int criterion = readIntSafe();

            if (criterion == 0)
                break;
            if (criterion < 1 || criterion > 6) {
                System.out.println("Opção inválida.");
                continue;
            }
            if (usedCriteria.contains(criterion)) {
                System.out.println("Você já usou este filtro.");
                continue;
            }

            switch (criterion) {
                case 1 -> filters.put(FilterType.NAME, readStringSafe("Digite o nome: "));
                case 2 -> filters.put(FilterType.SEX, readStringSafe("Sexo (Macho/Fêmea): "));
                case 3 -> filters.put(FilterType.AGE, String.valueOf(readDoubleSafe("Digite a idade: ")));
                case 4 -> filters.put(FilterType.WEIGHT, String.valueOf(readDoubleSafe("Digite o peso: ")));
                case 5 -> filters.put(FilterType.RACE, readStringSafe("Digite a raça: "));
            }

            usedCriteria.add(criterion);
            filtersAdded++;

            if (filtersAdded < maxFilters) {
                System.out.print("Deseja adicionar outro filtro? (1-Sim / 2-Não): ");
                if (readIntSafe() != 1)
                    break;
            }
        }

        if (!filters.isEmpty()) {
            searchController.filterByCriterion(typePet, filters);
            System.out.println("\nPressione ENTER para voltar ao menu principal...");
            scanner.nextLine();
        } else {
            System.out.println("Busca cancelada ou sem filtros.");
        }
    }

    private int readIntSafe() {
        try {
            String line = scanner.nextLine();
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double readDoubleSafe(String message) {
        while (true) {
            System.out.println(message);
            try {
                return Double.parseDouble(scanner.nextLine().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Erro: Digite um número válido.");
            }
        }
    }

    private String readStringSafe(String message) {
        System.out.println(message);
        return scanner.nextLine().trim();
    }

    private String readInput(String prompt, java.util.function.Consumer<String> validator) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                input = Pet.NOT_PROVIDED;
            }

            try {
                if (validator != null) {
                    validator.accept(input);
                }
                return input;
            } catch (IllegalArgumentException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private Map<String, String> collectPetData() {
        Map<String, String> data = new HashMap<>();

        Pet validatorPet = new Pet();
        Address validatorAddr = new Address();

        System.out.println("\n--- 📝 CADASTRO DE PET ---");

        data.put("fullName", readInput("1 - Nome completo: ", validatorPet::setFullName));

        data.put("type", readInput("2 - Tipo (Cachorro/Gato): ", value -> TypePet.fromUserInput(value)));
        data.put("sex", readInput("3 - Sexo (1-F / 2-M): ", value -> Sex.fromUserInput(value)));

        data.put("race", readInput("4 - Raça: ", validatorPet::setRace));

        data.put("weight", readInput("5 - Peso aproximado: ", value -> {
            if (!value.equals(Pet.NOT_PROVIDED))
                validatorPet.setWeight(Double.parseDouble(value.replace(",", ".")));
        }));

        data.put("age", readInput("6 - Idade aproximada: ", value -> {
            if (!value.equals(Pet.NOT_PROVIDED))
                validatorPet.setAge(Double.parseDouble(value.replace(",", ".")));
        }));

        System.out.println("\n--- LOCALIZAÇÃO ---");
        data.put("road", readInput("Rua/Bairro: ", validatorAddr::setRoad));
        data.put("number", readInput("Número: ", validatorAddr::setNumber));
        data.put("city", readInput("Cidade: ", validatorAddr::setCity));

        return data;
    }
}
