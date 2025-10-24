package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Film;
import rva.repository.FilmRepository;
import rva.services.FilmService;

@Component
public class FilmServiceImpl implements FilmService {

    @Autowired
    private FilmRepository repo;

    @Override
    public List<Film> getAll() {
        return repo.findAll();
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public Optional<Film> findById(int id) {
        return repo.findById(id);
    }

    @Override
    public Film create(Film t) {
        return repo.save(t);
    }

    @Override
    public Optional<Film> update(Film t, int id) {
        if(existsById(id)) {
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
    public List<Film> getFilmsByNaziv(String naziv) {
        return repo.findByNazivContainingIgnoreCase(naziv);
    }
    @Override
    public List<Film> getFilmsByZanr(String zanr) {
        return repo.findByZanrContainingIgnoreCase(zanr);
    }
}
