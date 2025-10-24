package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import rva.models.Film;
import rva.models.Rezervacija;
import rva.models.Sala;
@EnableJpaRepositories
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Integer> {

    List<Rezervacija> findByPlacenoEquals(boolean placeno);

    List<Rezervacija> findByFilm(Film film);

    List<Rezervacija> findBySala(Sala sala);
}
