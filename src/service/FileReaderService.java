package service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.Address;
import dto.Pet;

public class FileReaderService {
    private final static Path pathForm = Paths.get("src", "data", "formulario.txt");
    private final Path petsDir = Paths.get("petsCadastrados");

    public List<String> getAllQuestions() {
        try {
            if (!Files.exists(pathForm)) {
                Files.createDirectories(pathForm.getParent());
                Files.writeString(pathForm, "1-Nome\n2-Tipo\n3-Sexo\n4-Endereço\n5-Idade\n6-Peso\n7-Raça");
            }
            return Files.readAllLines(pathForm);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao acessar perguntas: " + e.getMessage());
        }
    }

    public List<Pet> loadAllPet() {
        List<Pet> pets = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(petsDir, "*.txt")) {
            for (Path entry : stream) {
                pets.add(parsePetFromFile(entry));
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar diretório de pets: " + e.getMessage());
        }
        return pets;
    }

    private Pet parsePetFromFile(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        Map<String, String> data = new HashMap<>();

        data.put("fullName", extractValue(lines.get(0)));
        data.put("type", extractValue(lines.get(1)));
        data.put("sex", extractValue(lines.get(2)));
        data.put("age", extractValue(lines.get(4)));
        data.put("weight", extractValue(lines.get(5)));
        data.put("race", extractValue(lines.get(6)));

        Address address = parseAddress(extractValue(lines.get(3)));

        return new Pet(data, address);
    }

    private String extractValue(String line) {
        if (!line.contains(":")) return line.trim();
        return line.substring(line.indexOf(":") + 1).trim();
    }

    private Address parseAddress(String fullAddress) {
        try {
            String[] parts = fullAddress.split(",|-");
            String road = parts[0].trim();
            String number = (parts.length > 1) ? parts[1].trim() : Pet.NOT_PROVIDED;
            String city = (parts.length > 2) ? parts[2].trim() : Pet.NOT_PROVIDED;
            return new Address(road, number, city);
        } catch (Exception e) {
            return new Address(Pet.NOT_PROVIDED, Pet.NOT_PROVIDED, Pet.NOT_PROVIDED);
        }
    }
}
