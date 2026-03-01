package repository;

import java.io.IOException;
import java.nio.file.Path;
import java.util.TreeMap;

import model.Pet;

public interface PetRepository {
    void save(Pet pet) throws IOException;
    TreeMap<Path, Pet> findAll();
}
