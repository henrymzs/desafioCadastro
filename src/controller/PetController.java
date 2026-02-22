package controller;

import java.util.List;

import dto.Pet;
import service.FileReaderService;
import service.PetService;
import view.PetFormView;

public class PetController {
    private final FileReaderService fileReader = new FileReaderService();
    private final PetFormView view = new PetFormView();
    private final PetService petService = new PetService();

    public void start() {
        fileReader.ensureFormExists();

        List<String> questions = fileReader.getAllQuestions();
        List<String> answers = view.askAll(questions);

        try {
            Pet pet = petService.createPetFromAnswers(answers);
            petService.save(pet);
            showSucessMessage(pet); 
        } catch (IllegalArgumentException e) {
            System.err.println("Erro ao cadastrar:" + e.getMessage());
        }
    }

    private void showSucessMessage(Pet pet) {
        System.out.println("Pet cadastrado");
        System.out.println(pet);
    }
}
