package rva.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import rva.models.Sala;
import rva.models.Bioskop;
import rva.services.SalaService;

@CrossOrigin
@RestController
@RequestMapping("/sala")
public class SalaController {

    @Autowired
    private SalaService salaService;

    // GET ALL
    @GetMapping
    public List<Sala> getAll() {
        return salaService.getAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Sala> getById(@PathVariable int id) {
        Optional<Sala> sala = salaService.findById(id);
        return sala.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // GET BY BIOSKOP
    @GetMapping("/bioskop/{bioskopId}")
    public List<Sala> getByBioskop(@PathVariable int bioskopId) {
        Bioskop bioskop = new Bioskop();
        bioskop.setId(bioskopId);
        return salaService.getSalasByBioskop(bioskop);
    }

    // GET BY MIN KAPACITET
    @GetMapping("/kapacitet/{minKapacitet}")
    public List<Sala> getByKapacitet(@PathVariable int minKapacitet) {
        return salaService.getSalasByKapacitetGreaterOrEquals(minKapacitet);
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Sala> create(@RequestBody Sala sala) {
        Sala novaSala = salaService.create(sala);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaSala);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Sala> update(@RequestBody Sala sala, @PathVariable int id) {
        Optional<Sala> updated = salaService.update(sala, id);
        return updated.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (!salaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        salaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
