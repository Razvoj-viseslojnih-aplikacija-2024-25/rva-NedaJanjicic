package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rva.models.Film;
import rva.services.FilmService;

@CrossOrigin
@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmService filmService;

    // GET ALL
    @GetMapping
    public List<Film> getAll() {
        return filmService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable int id) {
        Optional<Film> film = filmService.findById(id);
        return film.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET BY NAZIV
    @GetMapping("/naziv/{naziv}")
    public List<Film> getByNaziv(@PathVariable String naziv) {
        return filmService.getFilmsByNaziv(naziv);
    }

    // GET BY ŽANR
    @GetMapping("/zanr/{zanr}")
    public List<Film> getByZanr(@PathVariable String zanr) {
        return filmService.getFilmsByZanr(zanr);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Film> create(@RequestBody Film film) {
        Film newFilm = filmService.create(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(newFilm);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Film> update(@RequestBody Film film, @PathVariable int id) {
        Optional<Film> updated = filmService.update(film, id);
        return updated.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!filmService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        filmService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
