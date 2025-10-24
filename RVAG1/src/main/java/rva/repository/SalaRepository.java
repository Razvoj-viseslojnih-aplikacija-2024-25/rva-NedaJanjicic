package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import rva.models.Bioskop;
import rva.models.Sala;
@EnableJpaRepositories
public interface SalaRepository extends JpaRepository<Sala, Integer> {

    List<Sala> findByKapacitetGreaterThanEqual(int kapacitet);
    
    List<Sala> findByBioskop(Bioskop bioskop);
}
