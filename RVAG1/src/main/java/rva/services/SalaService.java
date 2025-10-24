package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Bioskop;
import rva.models.Sala;

@Service
public interface SalaService extends CrudService<Sala> {

    List<Sala> getSalasByKapacitetGreaterOrEquals(int kapacitet);
    
    List<Sala> getSalasByBioskop(Bioskop bioskop);
}
