package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Rezervacija;
import rva.models.Sala;
import rva.models.Film;
import rva.repository.RezervacijaRepository;
import rva.services.RezervacijaService;

@Component
public class RezervacijaServiceImpl implements RezervacijaService {

    @Autowired
    private RezervacijaRepository repo;

    @Override
    public List<Rezervacija> getAll() {
        return repo.findAll();
    }

    @Override
    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    @Override
    public Optional<Rezervacija> findById(int id){
        return repo.findById(id);
    }

    @Override
    public Rezervacija create(Rezervacija t) {
        return repo.save(t);
    }

    @Override
    public Optional<Rezervacija> update(Rezervacija t, int id) {
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
    public List<Rezervacija> getRezervacijasByPlaceno(boolean placeno) {
        return repo.findByPlacenoEquals(placeno);
    }

    @Override
    public List<Rezervacija> getRezervacijasByFilm(Film film) {
        return repo.findByFilm(film);
    }

    @Override
    public List<Rezervacija> getRezervacijasBySala(Sala sala) {
        return repo.findBySala(sala);
    }
}
