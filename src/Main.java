import java.io.IOException;

import controller.PetRegistrationController;
import repository.PetRepository;
import service.FileReaderService;
import view.ConsoleInteraction;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {

    FileReaderService fileReader = new FileReaderService();

    PetRepository repository = new PetRepository();

    PetRegistrationController registrationController = new PetRegistrationController(repository);

    ConsoleInteraction interaction = new ConsoleInteraction(registrationController);
    fileReader.getAllQuestions();

    interaction.start();
  }
}