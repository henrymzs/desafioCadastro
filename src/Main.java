import java.io.IOException;

import controller.PetRegistrationController;
import controller.PetSearchController;
import repository.PetRepositoryImpl;
import service.FileReaderService;
import service.FileWriterService;
import service.PetServiceSearch;
import view.ConsoleInteraction;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {
    try {
      FileReaderService fileReader = new FileReaderService();
      FileWriterService fileWriter = new FileWriterService();

      PetRepositoryImpl repository = PetRepositoryImpl.getInstance(fileReader, fileWriter);

      PetServiceSearch petServiceSearch = new PetServiceSearch(repository);

      PetRegistrationController registrationController = new PetRegistrationController(repository);

      PetSearchController searchController = new PetSearchController(repository, petServiceSearch);

      ConsoleInteraction interaction = new ConsoleInteraction(registrationController, searchController);

      fileReader.getAllQuestions();

      interaction.start();

    } catch (Exception e) {
      System.err.println("Erro fatal ao iniciar a aplicação: " + e.getMessage());
      e.printStackTrace();
    }
  }
}