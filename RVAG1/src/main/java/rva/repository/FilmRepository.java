package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import rva.models.Film;
@EnableJpaRepositories
public interface FilmRepository extends JpaRepository<Film, Integer> {

    List<Film> findByNazivContainingIgnoreCase(String naziv);

    List<Film> findByZanrContainingIgnoreCase(String zanr);
}
