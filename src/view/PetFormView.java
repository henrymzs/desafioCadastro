package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetFormView {
    private final static Scanner scanner = new Scanner(System.in);

    public List<String> askAll(List<String> questions) {
        List<String> answers = new ArrayList<>();

        for (String question : questions) {
            if (question.trim().isEmpty()) {
                continue;
            }

            if (question.startsWith("4")) {
                System.out.println("\n--- " + question + " ---");

                System.out.print("   > Número: ");
                String num = scanner.nextLine();

                System.out.print("   > Rua: ");
                String rua = scanner.nextLine();

                System.out.print("   > Cidade: ");
                String cidade = scanner.nextLine();

                answers.add(num + ";" + rua + ";" + cidade);
            } else {
                System.out.print(question + " ");
                answers.add(scanner.nextLine());
            }
        }
        return answers;
    }
}
