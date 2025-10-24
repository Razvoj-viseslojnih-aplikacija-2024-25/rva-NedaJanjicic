package rva.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Film implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "FILM_ID_GEN", sequenceName = "FILM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILM_ID_GEN")
    private int id;

    private String naziv;
    private String recenzija;
    private double trajanje;
    private String zanr;

    @OneToMany(mappedBy = "film")
    @JsonIgnore
    private List<Rezervacija> rezervacije;

    public Film() {}

    public Film(int id, String naziv, String recenzija, double trajanje, String zanr) {
        this.id = id;
        this.naziv = naziv;
        this.recenzija = recenzija;
        this.trajanje = trajanje;
        this.zanr = zanr;
    }

    // getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public String getRecenzija() { return recenzija; }
    public void setRecenzija(String recenzija) { this.recenzija = recenzija; }
    public double getTrajanje() { return trajanje; }
    public void setTrajanje(double trajanje) { this.trajanje = trajanje; }
    public String getZanr() { return zanr; }
    public void setZanr(String zanr) { this.zanr = zanr; }
    public List<Rezervacija> getRezervacije() { return rezervacije; }
    public void setRezervacije(List<Rezervacija> rezervacije) { this.rezervacije = rezervacije; }
}
