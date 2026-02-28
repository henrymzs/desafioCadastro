package view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import controller.PetRegistrationController;
import model.Address;
import model.Pet;
import model.enums.Sex;
import model.enums.TypePet;

public class ConsoleInteraction {
    private final PetRegistrationController petRegistrationController;
    private final Scanner scanner;

    public ConsoleInteraction(PetRegistrationController registrationController) {
        this.petRegistrationController = registrationController;
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
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void handleMainMenuOption(int userChoice) {
        switch (userChoice) {
            case 1 -> {
                Map<String, String> petData = collectPetData();
                petRegistrationController.register(petData);
            }
            case 0 -> System.out.println("Saindo...");
            default -> System.out.println("Opção inválida.");
        }
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
