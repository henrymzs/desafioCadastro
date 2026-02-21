package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FormService {
    private final static Path pathForm = Paths.get("src", "data", "formulario.txt");
    private static final String DEFAULT_QUESTIONS = "1 - Qual o nome e sobrenome do pet?\n" +
            "2 - Qual o tipo do pet (Cachorro/Gato)?\n" +
            "3 - Qual o sexo do animal?\n" +
            "4 - Qual endereço e bairro que ele foi encontrado?\n" +
            "5 - Qual a idade aproximada do pet?\n" +
            "6 - Qual o peso aproximado do pet?\n" +
            "7 - Qual a raça do pet?";

    public void ensureFormExists() {
        if (!Files.exists(pathForm)) {
            try {
                Files.createDirectories(pathForm.getParent());
                Files.writeString(pathForm, DEFAULT_QUESTIONS);
                System.out.println("Arquivo de formulário não encontrado. Criando padrão...");
            } catch (IOException e) {
                throw new RuntimeException("Erro ao inicializar arquivo de formulário: " + e.getMessage());
            }
        }
    }

    public List<String> getAllQuestions() {
        try {
            return Files.readAllLines(pathForm);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler perguntas: " + e.getMessage());
        }
    }
}
