package service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.Address;
import model.Pet;
import model.enums.Sex;
import model.enums.TypePet;

public class FileReaderService {
    private final static Path pathForm = Paths.get("src", "data", "formulario.txt");
    private static final String FOLDER = "petsCadastrados";

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

    private String extractValue(String line) {
        if (!line.contains(" - "))
            return line.trim();
        return line.substring(line.indexOf(" - ") + 3).trim();
    }

    public TreeMap<Path, Pet> readAllPets() throws IOException {
        Path dirPath = Paths.get(FOLDER);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
            return new TreeMap<>();
        }

        TreeMap<Path, Pet> petMap = new TreeMap<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
            for (Path path : stream) {
                List<String> lines = readFileToList(path);
                if (lines.size() >= 7) {
                    try {
                        Pet pet = parsePetFromFile(lines);
                        pet.setFilePath(path);
                        petMap.put(path, pet);
                    } catch (Exception e) {
                        System.err.println(
                                "Erro ao processar o pet no arquivo: " + path.getFileName() + " -> " + e.getMessage());
                    }
                }
            }
        }
        return petMap;
    }

    public List<String> readFileToList(Path path) throws IOException {
        try (Stream<String> lines = Files.lines(path)) {
            return lines.map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException e) {
            throw new IOException("Falha ao ler o arquivo: " + path.getFileName(), e);
        }
    }

    public Pet parsePetFromFile(List<String> lines) throws IOException {
        if (lines.size() < 7) {
            throw new IOException("Formato de arquivo inválido. Esperado pelo menos 7 linhas.");
        }

        String fullName = extractValue(lines.get(0));
        String[] nameParts = fullName.split(" ");
        String firstName = nameParts[0];
        String lastName = (nameParts.length > 1) ? String.join(" ", Arrays.copyOfRange(nameParts, 1, nameParts.length))
                : "";

        TypePet typePet = TypePet.fromString(extractValue(lines.get(1)));
        Sex sex = Sex.fromString(extractValue(lines.get(2)));

        String fullAddress = extractValue(lines.get(3));
        String[] addressParts = fullAddress.split(",");

        String street = addressParts[0].trim();
        String number = (addressParts.length > 1) ? addressParts[1].trim() : "S/N";
        String city = (addressParts.length > 2) ? addressParts[2].trim() : "Não informada";

        Address address = new Address(street, number, city);

        String ageRaw = extractValue(lines.get(4)).replaceAll("[^0-9.]", "").trim();
        Double age = ageRaw.isEmpty() ? 0.0 : Double.valueOf(ageRaw);

        String weightRaw = extractValue(lines.get(5)).replaceAll("[^0-9.]", "").trim();
        Double weight = weightRaw.isEmpty() ? 0.0 : Double.valueOf(weightRaw);

        String race = extractValue(lines.get(6));

        return new Pet(
                firstName + " " + lastName,
                typePet,
                sex,
                race,
                weight,
                age,
                address);
    }
}
