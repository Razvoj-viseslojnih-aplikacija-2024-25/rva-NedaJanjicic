package rva.models;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Rezervacija implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "REZERVACIJA_ID_GEN", sequenceName = "REZERVACIJA_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REZERVACIJA_ID_GEN")
    private int id;

    private int brojOsoba;
    private double cenaKarti;
    private Date datum;
    private boolean placeno;

    @ManyToOne
    @JoinColumn(name = "sala")
    private Sala sala;

    @ManyToOne
    @JoinColumn(name = "film")
    private Film film;

    public Rezervacija() {}

    public Rezervacija(int id, int brojOsoba, double cenaKarti, Date datum, boolean placeno) {
        this.id = id;
        this.brojOsoba = brojOsoba;
        this.cenaKarti = cenaKarti;
        this.datum = datum;
        this.placeno = placeno;
    }

    // getters i setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getBrojOsoba() { return brojOsoba; }
    public void setBrojOsoba(int brojOsoba) { this.brojOsoba = brojOsoba; }
    public double getCenaKarti() { return cenaKarti; }
    public void setCenaKarti(double cenaKarti) { this.cenaKarti = cenaKarti; }
    public Date getDatum() { return datum; }
    public void setDatum(Date datum) { this.datum = datum; }
    public boolean isPlaceno() { return placeno; }
    public void setPlaceno(boolean placeno) { this.placeno = placeno; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
}
