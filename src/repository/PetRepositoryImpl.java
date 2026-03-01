package repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;

import model.Pet;
import service.FileReaderService;
import service.FileWriterService;

public class PetRepositoryImpl implements PetRepository {
    private static PetRepositoryImpl instance;
    private TreeMap<Path, Pet> pets;
    private final FileReaderService fileReaderService;
    private final FileWriterService fileWriterService;

    private PetRepositoryImpl(FileReaderService fileReaderService, FileWriterService fileWriterService) {
        this.fileWriterService = fileWriterService;
        this.fileReaderService = fileReaderService;
        this.pets = null;
    }

    public static PetRepositoryImpl getInstance(FileReaderService fileReaderService,
            FileWriterService fileWriterService) {
        if (instance == null) {
            instance = new PetRepositoryImpl(fileReaderService, fileWriterService);
        }
        return instance;
    }

    @Override
    public void save(Pet pet) throws IOException {
        fileWriterService.savePetToFile(pet);
        if (pets == null) {
            findAll();
        }
        pets.put(pet.getFilePath(), pet);
    }

    public TreeMap<Path, Pet> findAll() {
        if (pets == null) {
            try {
                pets = fileReaderService.readAllPets();
            } catch (Exception e) {
                System.err.println("Falha ao carregar base de dados: " + e.getMessage());
                pets = new TreeMap<>();
            }
        }
        return pets;
    }

    public void refreshCache() {
        this.pets.clear();
    }
}
