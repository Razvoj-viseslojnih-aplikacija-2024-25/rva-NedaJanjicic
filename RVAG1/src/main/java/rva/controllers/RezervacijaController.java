package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rva.models.Rezervacija;
import rva.models.Film;
import rva.models.Sala;
import rva.services.RezervacijaService;

@CrossOrigin
@RestController
@RequestMapping("/rezervacija")
public class RezervacijaController {

    @Autowired
    private RezervacijaService rezervacijaService;

    // GET ALL
    @GetMapping
    public List<Rezervacija> getAll() {
        return rezervacijaService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Rezervacija> getById(@PathVariable int id) {
        Optional<Rezervacija> rezervacija = rezervacijaService.findById(id);
        return rezervacija.map(ResponseEntity::ok)
                          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET BY PLACENO
    @GetMapping("/placeno/{placeno}")
    public List<Rezervacija> getByPlaceno(@PathVariable boolean placeno) {
        return rezervacijaService.getRezervacijasByPlaceno(placeno);
    }

    // GET BY FILM
    @GetMapping("/film/{filmId}")
    public List<Rezervacija> getByFilm(@PathVariable int filmId) {
        Film film = new Film();
        film.setId(filmId);
        return rezervacijaService.getRezervacijasByFilm(film);
    }

    // GET BY SALA
    @GetMapping("/sala/{salaId}")
    public List<Rezervacija> getBySala(@PathVariable int salaId) {
        Sala sala = new Sala();
        sala.setId(salaId);
        return rezervacijaService.getRezervacijasBySala(sala);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Rezervacija> create(@RequestBody Rezervacija rezervacija) {
        Rezervacija newRezervacija = rezervacijaService.create(rezervacija);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRezervacija);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Rezervacija> update(@RequestBody Rezervacija rezervacija, @PathVariable int id) {
        Optional<Rezervacija> updated = rezervacijaService.update(rezervacija, id);
        return updated.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!rezervacijaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rezervacijaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
