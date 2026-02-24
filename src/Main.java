import java.io.IOException;

import controller.PetController;
import service.FileReaderService;
import service.PetService;
import view.ConsoleInteraction;
import view.PetFormView;

public class Main {
  public static void main(String[] args) throws IOException, InterruptedException {

    PetService service = new PetService();
    PetFormView view = new PetFormView();
    FileReaderService fileReader = new FileReaderService();

    PetController controller = new PetController(service, view, fileReader);
    ConsoleInteraction interaction = new ConsoleInteraction(controller);
    
    interaction.start();
  }
}