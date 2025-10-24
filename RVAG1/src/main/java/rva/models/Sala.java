package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Sala implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SALA_ID_GEN", sequenceName = "SALA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SALA_ID_GEN")
    private int id;

    private int kapacitet;
    private int brojRedova;

    @ManyToOne
    @JoinColumn(name = "bioskop")
    private Bioskop bioskop;

    @OneToMany(mappedBy = "sala")
    @JsonIgnore
    private List<Rezervacija> rezervacije;

    public Sala() {}

    public Sala(int id, int kapacitet, int brojRedova) {
        this.id = id;
        this.kapacitet = kapacitet;
        this.brojRedova = brojRedova;
    }

    // getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getKapacitet() { return kapacitet; }
    public void setKapacitet(int kapacitet) { this.kapacitet = kapacitet; }
    public int getBrojRedova() { return brojRedova; }
    public void setBrojRedova(int brojRedova) { this.brojRedova = brojRedova; }
    public Bioskop getBioskop() { return bioskop; }
    public void setBioskop(Bioskop bioskop) { this.bioskop = bioskop; }
    public List<Rezervacija> getRezervacije() { return rezervacije; }
    public void setRezervacije(List<Rezervacija> rezervacije) { this.rezervacije = rezervacije; }
}
