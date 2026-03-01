package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import model.Pet;

public class FileWriterService {
    private static final String FILE_EXTENSION = ".txt";
    private static final String FOLDER = "petsCadastrados";

    public static Path ensureDataDirectory() throws IOException {
        String projectDir = System.getProperty("user.dir");
        Path directory = Paths.get(projectDir, FOLDER);
        if (!Files.exists(directory)) {
            Files.createDirectories(directory);
        }
        return directory;
    }

    public String formatFileContent(Pet pet) {
        return String.format(Locale.ENGLISH, """
                1 - %s
                2 - %s
                3 - %s
                4 - %s
                5 - %.2f
                6 - %.2f
                7 - %s""",
                pet.getFullName(),
                pet.getTypePet(),
                pet.getSex(),
                pet.getAddress(),
                pet.getAge(),
                pet.getWeight(),
                pet.getRace());
    }

    public String formatFileName(Pet pet) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmm"));
        String safeName = sanitize(pet.getFullName());
        return timestamp + "_" + (safeName.isEmpty() ? "PET" : safeName);
    }

    public void savePetToFile(Pet pet) throws IOException {
        if (pet == null) throw new IllegalArgumentException("Pet não pode ser nulo");
        Path directory = ensureDataDirectory();
        if (pet.getFilePath() != null && Files.exists(pet.getFilePath())) {
            Files.delete(pet.getFilePath());
        }
        String fileName = formatFileName(pet);
        String content = formatFileContent(pet);
        Path filePath = directory.resolve(fileName + FILE_EXTENSION);
    
        pet.setFilePath(filePath);
        Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    private String sanitize(String input) {
        if (input == null) return "";
        return input.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
