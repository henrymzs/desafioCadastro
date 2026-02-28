package repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import model.Pet;

public class PetRepository {
    private static final String FOLDER = "petCadastrados";

    public void save(Pet pet) {
        try {
            Path dir = Paths.get(FOLDER);
            if (!Files.exists(dir))
                Files.createDirectories(dir);
            Path file = dir.resolve(generateFileName(pet));

            List<String> lines = List.of(
                    "Nome: " + pet.getFullName(),
                    "Tipo: " + pet.getTypePet(),
                    "Sexo: " + pet.getSex(),
                    "Endereço: " + pet.getAddress(),
                    "Idade: " + pet.getAgeFormatted(),
                    "Peso: " + pet.getWeightFormatted(),
                    "Raça: " + pet.getRace());
            Files.write(file, lines);
        } catch (IOException e) {
            throw new RuntimeException("Erro técnico ao salvar no disco: " + e.getMessage());
        }
    }

    private String generateFileName(Pet pet) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        String safeName = pet.getFullName().replaceAll("[^a-zA-Z0-9]", "");
        return ts + "_" + safeName + ".txt";
    }
}
