package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rva.models.Bioskop;
import rva.services.BioskopService;

@CrossOrigin
@RestController
@RequestMapping("/bioskop")
public class BioskopController {

    @Autowired
    private BioskopService bioskopService;

    // GET ALL
    @GetMapping
    public List<Bioskop> getAll() {
        return bioskopService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Bioskop> getById(@PathVariable int id) {
        Optional<Bioskop> bioskop = bioskopService.findById(id);
        return bioskop.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET BY NAZIV
    @GetMapping("/naziv/{naziv}")
    public List<Bioskop> getByNaziv(@PathVariable String naziv) {
        return bioskopService.getBioskopsByNaziv(naziv);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Bioskop> create(@RequestBody Bioskop bioskop) {
        Bioskop newBioskop = bioskopService.create(bioskop);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBioskop);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Bioskop> update(@RequestBody Bioskop bioskop, @PathVariable int id) {
        Optional<Bioskop> updated = bioskopService.update(bioskop, id);
        return updated.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!bioskopService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        bioskopService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
