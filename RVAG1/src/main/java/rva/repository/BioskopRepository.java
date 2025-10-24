package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import rva.models.Bioskop;
@EnableJpaRepositories
public interface BioskopRepository extends JpaRepository<Bioskop, Integer> {

    List<Bioskop> findByNazivContainingIgnoreCase(String naziv);
}
