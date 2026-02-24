package view;

import java.util.Scanner;

import controller.PetController;

public class ConsoleInteraction {
    private final PetController petController;
    private final Scanner scanner;

    public ConsoleInteraction(PetController petController) {
        this.petController = petController;
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
                petController.start();
            }
            default -> System.out.println("Opção inválida.");
        }
    }
}
