package service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import model.Pet;
import repository.PetRepository;

public class PetServiceSearch {
    private final PetRepository repository;

    public PetServiceSearch(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> listAll() {
        Map<Path, Pet> petMap = repository.findAll();

        List<Pet> list = new ArrayList<>(petMap.values());
        list.sort(Comparator.comparing(Pet::getFullName));
        return list;
    }

    
}
