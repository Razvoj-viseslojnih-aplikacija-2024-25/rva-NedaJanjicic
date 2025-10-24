package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Bioskop;
import rva.models.Sala;
import rva.repository.SalaRepository;
import rva.services.SalaService;

@Component
public class SalaServiceImpl implements SalaService {

    @Autowired
    private SalaRepository repo;

    @Override
    public List<Sala> getAll() {
        return repo.findAll();
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public Optional<Sala> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Sala create(Sala t) {
        return repo.save(t);
    }

    @Override
    public Optional<Sala> update(Sala t, int id) {
        if (existsById(id)) {
            t.setId(id);
            return Optional.of(repo.save(t));
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        repo.deleteById(id);
    }

    @Override
    public List<Sala> getSalasByKapacitetGreaterOrEquals(int kapacitet) {
        return repo.findByKapacitetGreaterThanEqual(kapacitet);
    }

    @Override
    public List<Sala> getSalasByBioskop(Bioskop bioskop) {
        return repo.findByBioskop(bioskop);
    }
}
