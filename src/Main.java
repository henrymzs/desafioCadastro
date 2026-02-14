import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner read = new Scanner(System.in);
    final String FORM_FILE_NAME = "formulario.txt";
    File fileName = new File(FORM_FILE_NAME);
    boolean continued = true;

    try (BufferedReader bufferedReader = new BufferedReader((new FileReader(fileName)))) {
      String currentLine;
      System.out.println("\nConteúdo do arquivo:\n");
      while ((currentLine = bufferedReader.readLine()) != null) {
        System.out.println(currentLine);
      }

      while (continued) {
        System.out.print("Escolha uma opção: ");
        String input = read.nextLine();
        if (!input.matches("\\d+")) {
          System.out.println("Digite apenas números positivos");
          continue;
        }
        int option = Integer.parseInt(input);
        if (option <= 0) {
          System.out.println("Número inválido");
          continue;
        }
        switch (option) {
          case 1:
            System.out.println("Escolheu primeira opção");
            break;
          case 2:
            System.out.println("Escolheu segunda opção");
            break;
          case 3:
            System.out.println("Escolheu terceira opção");
            break;
          case 4:
            System.out.println("Escolheu quarta opção");
            break;
          case 5:
            System.out.println("Escolheu quinta opção");
            break;
          case 6:
            System.out.println("Escolheu sexta opção");
            break;
          case 7:
            System.out.println("Escolheu setima opção");
            break;
          case 8:
            System.out.println("Saindo...");
            continued = false;
            break;
          default:
            System.out.println("Opção inválida");
        }
      }
    } catch (IOException exception) {
      System.err.println("Erro na abertura do arquivo: "
          + exception.getMessage());
    }
    System.out.println();
    read.close();
  }
}