package controller;

import java.util.List;

import dto.Pet;
import service.FormService;
import service.PetService;
import view.PetFormView;

public class PetController {
    private final FormService formService = new FormService();
    private final PetFormView view = new PetFormView();
    private final PetService petService = new PetService();

    public void start() {
        formService.ensureFormExists();

        List<String> questions = formService.getAllQuestions();
        List<String> answers = view.askAll(questions);

        try {
            Pet pet = petService.createPetFromAnswers(answers);
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
