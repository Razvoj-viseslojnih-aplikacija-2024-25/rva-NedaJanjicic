package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Bioskop;

@Service
public interface BioskopService extends CrudService<Bioskop> {

    List<Bioskop> getBioskopsByNaziv(String naziv);
}
